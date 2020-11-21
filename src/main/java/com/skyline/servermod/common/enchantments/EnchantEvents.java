package com.skyline.servermod.common.enchantments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.skyline.servermod.ServerMod;
import com.skyline.servermod.common.ModHooks;
import com.skyline.servermod.common.ModHooks.BlastingEvent;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class EnchantEvents {
	private static final Vector3i[][] blastingBlocks = new Vector3i[][] {
		{
			new Vector3i(0, 0, 1), new Vector3i(0, 0, -1), new Vector3i(0, 1, 0), new Vector3i(0, -1, 0), new Vector3i(1, 0, 0), new Vector3i(-1, 0, 0)
		}, {
			new Vector3i(0, 1, 1), new Vector3i(0, 1, -1), new Vector3i(0, -1, 1), new Vector3i(0, -1, -1), new Vector3i(1, 0, 1), new Vector3i(1, 0, -1), new Vector3i(-1, 0, 1), new Vector3i(-1, 0, -1), new Vector3i(1, 1, 0), new Vector3i(1, -1, 0), new Vector3i(-1, 1, 0), new Vector3i(-1, -1, 0)
		}, {
			new Vector3i(1, 1, 1), new Vector3i(1, 1, -1), new Vector3i(1, -1, 1), new Vector3i(1, -1, -1), new Vector3i(-1, 1, 1), new Vector3i(-1, 1, -1), new Vector3i(-1, -1, 1), new Vector3i(-1, -1, -1)
		}, {
			new Vector3i(0, 0, 2), new Vector3i(0, 1, 2), new Vector3i(0, -1, 2), new Vector3i(1, 0, 2), new Vector3i(-1, 0, 2), new Vector3i(0, 0, -2), new Vector3i(0, 1, -2), new Vector3i(0, -1, -2), new Vector3i(1, 0, -2), new Vector3i(-1, 0, -2), new Vector3i(0, 2, 0), new Vector3i(0, 2, 1), new Vector3i(0, 2, -1), new Vector3i(1, 2, 0), new Vector3i(-1, 2, 0), new Vector3i(0, -2, 0), new Vector3i(0, -2, 1), new Vector3i(0, -2, -1), new Vector3i(1, -2, 0), new Vector3i(-1, -2, 0), new Vector3i(2, 0, 0), new Vector3i(2, 0, 1), new Vector3i(2, 0, -1), new Vector3i(2, 1, 0), new Vector3i(2, -1, 0), new Vector3i(-2, 0, 0), new Vector3i(-2, 0, 1), new Vector3i(-2, 0, -1), new Vector3i(-2, 1, 0), new Vector3i(-2, -1, 0)
		}, {
			new Vector3i(1, 1, 2), new Vector3i(1, -1, 2), new Vector3i(-1, 1, 2), new Vector3i(-1, -1, 2), new Vector3i(1, 1, -2), new Vector3i(1, -1, -2), new Vector3i(-1, 1, -2), new Vector3i(-1, -1, -2), new Vector3i(1, 2, 1), new Vector3i(1, 2, -1), new Vector3i(-1, 2, 1), new Vector3i(-1, 2, -1), new Vector3i(1, -2, 1), new Vector3i(1, -2, -1), new Vector3i(-1, -2, 1), new Vector3i(-1, -2, -1), new Vector3i(2, 1, 1), new Vector3i(2, 1, -1), new Vector3i(2, -1, 1), new Vector3i(2, -1, -1), new Vector3i(-2, 1, 1), new Vector3i(-2, 1, -1), new Vector3i(-2, -1, 1), new Vector3i(-2, -1, -1)
		}, {
			new Vector3i(2, 0, 2), new Vector3i(2, 0, -2), new Vector3i(-2, 0, 2), new Vector3i(-2, 0, -2), new Vector3i(2, 2, 0), new Vector3i(2, -2, 0), new Vector3i(-2, 2, 0), new Vector3i(-2, -2, 0), new Vector3i(0, 2, 2), new Vector3i(0, 2, -2), new Vector3i(0, -2, 2), new Vector3i(0, -2, -2)
		}, {
			new Vector3i(2, 1, 2), new Vector3i(2, 1, -2), new Vector3i(-2, 1, 2), new Vector3i(-2, 1, -2), new Vector3i(2, 2, 1), new Vector3i(2, -2, 1), new Vector3i(-2, 2, 1), new Vector3i(-2, -2, 1), new Vector3i(1, 2, 2), new Vector3i(1, 2, -2), new Vector3i(1, -2, 2), new Vector3i(1, -2, -2), new Vector3i(2, -1, 2), new Vector3i(2, -1, -2), new Vector3i(-2, -1, 2), new Vector3i(-2, -1, -2), new Vector3i(2, 2, -1), new Vector3i(2, -2, -1), new Vector3i(-2, 2, -1), new Vector3i(-2, -2, -1), new Vector3i(-1, 2, 2), new Vector3i(-1, 2, -2), new Vector3i(-1, -2, 2), new Vector3i(-1, -2, -2)
		}, {
			new Vector3i(2, 2, 2), new Vector3i(2, 2, -2), new Vector3i(-2, 2, 2), new Vector3i(-2, 2, -2), new Vector3i(2, -2, 2), new Vector3i(2, -2, -2), new Vector3i(-2, -2, 2), new Vector3i(-2, -2, -2)
		}
	};

	@EventBusSubscriber(modid = ServerMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class EventHandlers {
		@SubscribeEvent
		public static void onItemExpire(final ItemExpireEvent event) {
			if (event.getEntityItem().getItem().isEnchanted() && EnchantmentHelper.getEnchantmentLevel(ModEnchants.TIMELESS.get(), event.getEntityItem().getItem()) > 0) {
				event.getEntityItem().setNoDespawn();
				event.setCanceled(true);
			}
		}

		@SubscribeEvent
		public static void onLivingDrops(final LivingDropsEvent event) {
			if (event.getEntityLiving() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) event.getEntityLiving();
				Collection<ItemEntity> drops = event.getDrops();
				event.setCanceled(drops.removeIf(i -> i.getItem().isEnchanted() && EnchantmentHelper.getEnchantmentLevel(ModEnchants.SOULBOUND.get(), i.getItem()) > 0 && player.addItemStackToInventory(i.getItem())));
				if (event.isCanceled()) {
					for (ItemEntity drop : drops) {
						ForgeHooks.onPlayerTossEvent(player, drop.getItem(), false);
					}
				}
			}
		}

		@SubscribeEvent
		public static void onPlayerClone(final PlayerEvent.Clone event) {
			event.getPlayer().inventory.copyInventory(event.getOriginal().inventory);
		}

		@SubscribeEvent
		public static void onBreakBlock(final BreakEvent event) {
			if (!(event.getPlayer() instanceof ServerPlayerEntity)) { return; }
			ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
			if (player != null) {
				ItemStack tool = player.getHeldItemMainhand();
				if (tool.isEnchanted()) {
					World world = (World) event.getWorld();
					BlockState state = event.getState();
					Block block = state.getBlock();
					BlockPos pos = event.getPos();

					if (EnchantmentHelper.getEnchantmentLevel(ModEnchants.REAPER.get(), tool) > 0) {
						if (block instanceof CropsBlock && !((CropsBlock) block).isMaxAge(state)) {
							event.setCanceled(true);
							return;
						}
					}

					boolean canHarvestBlock = block.canHarvestBlock(state, world, pos, player);
					if (canHarvestBlock) {
						boolean blastable = !(event instanceof BlastingEvent);

						if (blastable) {
							int blastLvl;
							if ((blastLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchants.BLASTING.get(), tool)) > 0 && canHarvestBlock) {
								blastLvl = Math.min(blastLvl, blastingBlocks.length);
								if (blastLvl > 0) {
									for (int lvl = 0; lvl < blastLvl; lvl++) {
										for (Vector3i offset : blastingBlocks[lvl]) {
											BlockPos newPos = pos.add(offset);
											Block newBlock = world.getBlockState(newPos).getBlock();
											if (newBlock == block) {
												ModHooks.tryHarvestBlock(world, player, newPos, BlastingEvent.BreakType.BLASTING);
											}
										}
									}
								}
							}
						}

						if (blastable || !BlastingEvent.BreakType.TOPPLING.equals(((BlastingEvent) event).type)) {
							int topLvl;
							if ((topLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchants.TOPPLING.get(), tool)) > 0 && canHarvestBlock) {
								topLvl *= 2;
								List<BlockPos> line = new ArrayList<BlockPos>();
								for (int lvl = 0; lvl < topLvl; lvl++) {
									pos = pos.up();
									Block newBlock = world.getBlockState(pos).getBlock();
									if (newBlock == block) {
										line.add(pos);
									} else {
										break;
									}
								}
								line.forEach(point -> ModHooks.tryHarvestBlock(world, player, point, BlastingEvent.BreakType.TOPPLING));
							}
						}
					}
				}
			}
		}
	}
}
