package com.skyline.servermod.common.enchantments;

import com.skyline.servermod.ServerMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class BlastingEnchant extends Enchantment {
	public BlastingEnchant() {
		super(Enchantment.Rarity.RARE, EnchantmentType.DIGGER, ModEnchantHelper.SLOT_HAND);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 1 + 10 * (enchantmentLevel - 1);
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	public static class EventHandler {
		protected static final Vector3i[][] blastingBlocks = new Vector3i[][] {
				{ new Vector3i(0, 0, 1), new Vector3i(0, 0, -1), new Vector3i(0, 1, 0), new Vector3i(0, -1, 0),
						new Vector3i(1, 0, 0), new Vector3i(-1, 0, 0) },
				{ new Vector3i(0, 1, 1), new Vector3i(0, 1, -1), new Vector3i(0, -1, 1), new Vector3i(0, -1, -1),
						new Vector3i(1, 0, 1), new Vector3i(1, 0, -1), new Vector3i(-1, 0, 1), new Vector3i(-1, 0, -1),
						new Vector3i(1, 1, 0), new Vector3i(1, -1, 0), new Vector3i(-1, 1, 0),
						new Vector3i(-1, -1, 0) },
				{ new Vector3i(1, 1, 1), new Vector3i(1, 1, -1), new Vector3i(1, -1, 1), new Vector3i(1, -1, -1),
						new Vector3i(-1, 1, 1), new Vector3i(-1, 1, -1), new Vector3i(-1, -1, 1),
						new Vector3i(-1, -1, -1) },
				{ new Vector3i(0, 0, 2), new Vector3i(0, 1, 2), new Vector3i(0, -1, 2), new Vector3i(1, 0, 2),
						new Vector3i(-1, 0, 2), new Vector3i(0, 0, -2), new Vector3i(0, 1, -2), new Vector3i(0, -1, -2),
						new Vector3i(1, 0, -2), new Vector3i(-1, 0, -2), new Vector3i(0, 2, 0), new Vector3i(0, 2, 1),
						new Vector3i(0, 2, -1), new Vector3i(1, 2, 0), new Vector3i(-1, 2, 0), new Vector3i(0, -2, 0),
						new Vector3i(0, -2, 1), new Vector3i(0, -2, -1), new Vector3i(1, -2, 0),
						new Vector3i(-1, -2, 0), new Vector3i(2, 0, 0), new Vector3i(2, 0, 1), new Vector3i(2, 0, -1),
						new Vector3i(2, 1, 0), new Vector3i(2, -1, 0), new Vector3i(-2, 0, 0), new Vector3i(-2, 0, 1),
						new Vector3i(-2, 0, -1), new Vector3i(-2, 1, 0), new Vector3i(-2, -1, 0) },
				{ new Vector3i(1, 1, 2), new Vector3i(1, -1, 2), new Vector3i(-1, 1, 2), new Vector3i(-1, -1, 2),
						new Vector3i(1, 1, -2), new Vector3i(1, -1, -2), new Vector3i(-1, 1, -2),
						new Vector3i(-1, -1, -2), new Vector3i(1, 2, 1), new Vector3i(1, 2, -1), new Vector3i(-1, 2, 1),
						new Vector3i(-1, 2, -1), new Vector3i(1, -2, 1), new Vector3i(1, -2, -1),
						new Vector3i(-1, -2, 1), new Vector3i(-1, -2, -1), new Vector3i(2, 1, 1),
						new Vector3i(2, 1, -1), new Vector3i(2, -1, 1), new Vector3i(2, -1, -1), new Vector3i(-2, 1, 1),
						new Vector3i(-2, 1, -1), new Vector3i(-2, -1, 1), new Vector3i(-2, -1, -1) } };

		@EventBusSubscriber(modid = ServerMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
		public static class EventHandlers {
			@SubscribeEvent
			public static void onBreakBlock(final BreakEvent event) {
				PlayerEntity player = event.getPlayer();
				if (player != null) {
					World world = (World) event.getWorld();
					BlockState state = event.getState();
					Block block = state.getBlock();
					BlockPos pos = event.getPos();
					ItemStack tool = player.getHeldItemMainhand();
					int blastLvl;
					if (tool.isEnchanted()
							&& (blastLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.BLASTING, tool)) > 0) {
						if (block.canHarvestBlock(state, world, pos, player)) {
							blastLvl = Math.min(blastLvl, blastingBlocks.length);
							if (blastLvl > 0) {
								for (int lvl = 0; lvl < blastLvl; lvl++) {
									for (Vector3i offset : blastingBlocks[lvl]) {
										BlockPos newPos = pos.add(offset);
										Block newBlock = world.getBlockState(newPos).getBlock();
										if (newBlock == block) {
											newBlock.harvestBlock(world, player, newPos, world.getBlockState(newPos),
													world.getTileEntity(newPos), tool);
											world.destroyBlock(newPos, false, player);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
