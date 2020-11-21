package com.skyline.servermod.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CommandBlockBlock;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.StructureBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class ModHooks {
	private static final DummyBlockReader DUMMY_WORLD = new DummyBlockReader();

	private static class DummyBlockReader implements IBlockReader {
		@Override
		public TileEntity getTileEntity(BlockPos pos) {
			return null;
		}

		@Override
		public BlockState getBlockState(BlockPos pos) {
			return Blocks.AIR.getDefaultState();
		}

		@Override
		public FluidState getFluidState(BlockPos pos) {
			return Fluids.EMPTY.getDefaultState();
		}
	}

	/**
	 * Attempts to harvest a block
	 */
	public static boolean tryHarvestBlock(World world, ServerPlayerEntity entityPlayer, BlockPos pos) {
		BlockState blockstate = world.getBlockState(pos);
		int exp = onBlastingEvent(world, entityPlayer, pos);
		if (exp == -1) {
			return false;
		} else {
			TileEntity tileentity = world.getTileEntity(pos);
			Block block = blockstate.getBlock();
			if ((block instanceof CommandBlockBlock || block instanceof StructureBlock || block instanceof JigsawBlock) && !entityPlayer.canUseCommandBlock()) {
				world.notifyBlockUpdate(pos, blockstate, blockstate, 3);
				return false;
			} else if (entityPlayer.getHeldItemMainhand().onBlockStartBreak(pos, entityPlayer)) {
				return false;
			} else if (entityPlayer.blockActionRestricted(world, pos, entityPlayer.interactionManager.getGameType())) {
				return false;
			} else {
				if (entityPlayer.isCreative()) {
					removeBlock(world, entityPlayer, pos, false);
					return true;
				} else {
					ItemStack itemstack = entityPlayer.getHeldItemMainhand();
					ItemStack itemstack1 = itemstack.copy();
					boolean flag1 = blockstate.canHarvestBlock(world, pos, entityPlayer);
					itemstack.onBlockDestroyed(world, blockstate, pos, entityPlayer);
					if (itemstack.isEmpty() && !itemstack1.isEmpty()) net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(entityPlayer, itemstack1, Hand.MAIN_HAND);
					boolean flag = removeBlock(world, entityPlayer, pos, flag1);

					if (flag && flag1) {
						block.harvestBlock(world, entityPlayer, pos, blockstate, tileentity, itemstack1);
					}

					if (flag && exp > 0) blockstate.getBlock().dropXpOnBlockBreak(world, pos, exp);

					return true;
				}
			}
		}
	}

	private static boolean removeBlock(World world, ServerPlayerEntity entityPlayer, BlockPos pos, boolean canHarvest) {
		BlockState state = world.getBlockState(pos);
		boolean removed = state.removedByPlayer(world, pos, entityPlayer, canHarvest, world.getFluidState(pos));
		if (removed) state.getBlock().onPlayerDestroy(world, pos, state);
		return removed;
	}

	public static class BlastingEvent extends TopplingEvent {
		public BlastingEvent(World world, BlockPos pos, BlockState state, PlayerEntity player) {
			super(world, pos, state, player);
		}
	}

	public static class TopplingEvent extends BreakEvent {
		public TopplingEvent(World world, BlockPos pos, BlockState state, PlayerEntity player) {
			super(world, pos, state, player);
		}
	}

	public static int onBlastingEvent(World world, ServerPlayerEntity entityPlayer, BlockPos pos) {
		// Logic from tryHarvestBlock for pre-canceling the event
		boolean preCancelEvent = false;
		ItemStack itemstack = entityPlayer.getHeldItemMainhand();
		if (!itemstack.isEmpty() && !itemstack.getItem().canPlayerBreakBlockWhileHolding(world.getBlockState(pos), world, pos, entityPlayer)) {
			preCancelEvent = true;
		}

		if (world.getTileEntity(pos) == null) {
			entityPlayer.connection.sendPacket(new SChangeBlockPacket(DUMMY_WORLD, pos));
		}

		// Post the block break event
		BlockState state = world.getBlockState(pos);
		BlastingEvent event = new BlastingEvent(world, pos, state, entityPlayer);
		event.setCanceled(preCancelEvent);
		MinecraftForge.EVENT_BUS.post(event);

		// Handle if the event is canceled
		if (event.isCanceled()) {
			// Let the client know the block still exists
			entityPlayer.connection.sendPacket(new SChangeBlockPacket(world, pos));

			// Update any tile entity data for this block
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity != null) {
				IPacket<?> pkt = tileentity.getUpdatePacket();
				if (pkt != null) {
					entityPlayer.connection.sendPacket(pkt);
				}
			}
		}
		return event.isCanceled() ? -1 : event.getExpToDrop();
	}

}
