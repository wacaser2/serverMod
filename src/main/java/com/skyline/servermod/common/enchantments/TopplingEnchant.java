package com.skyline.servermod.common.enchantments;

import com.skyline.servermod.ServerMod;
import com.skyline.servermod.common.ModHooks;
import com.skyline.servermod.common.ModHooks.BlastingEvent;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class TopplingEnchant extends Enchantment {
	public TopplingEnchant() {
		super(Enchantment.Rarity.RARE, ModEnchantHelper.AXE, ModEnchantHelper.SLOT_HAND);
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
	public int getMaxLevel() { return 5; }

	public boolean func_230309_h_() {
		return true;
	}

	@EventBusSubscriber(modid = ServerMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class EventHandlers {
		private static final Vector3i[][] topplingBlocks = new Vector3i[][] {
			{
				new Vector3i(0, 1, 0), new Vector3i(0, 2, 0)
			}, {
				new Vector3i(0, 3, 0), new Vector3i(0, 4, 0)
			}, {
				new Vector3i(0, 5, 0), new Vector3i(0, 6, 0)
			}, {
				new Vector3i(0, 7, 0), new Vector3i(0, 8, 0)
			}, {
				new Vector3i(0, 9, 0), new Vector3i(0, 10, 0)
			}
		};

		@SubscribeEvent
		public static void onBreakBlock(final BreakEvent event) {
			if (event instanceof BlastingEvent || !(event.getPlayer() instanceof ServerPlayerEntity)) { return; }
			ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
			if (player != null) {
				World world = (World) event.getWorld();
				BlockState state = event.getState();
				Block block = state.getBlock();
				BlockPos pos = event.getPos();
				ItemStack tool = player.getHeldItemMainhand();
				int topLvl;
				if (tool.isEnchanted() && (topLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchants.TOPPLING.get(), tool)) > 0 && block.canHarvestBlock(state, world, pos, player)) {
					topLvl = Math.min(topLvl, topplingBlocks.length);
					if (topLvl > 0) {
						for (int lvl = 0; lvl < topLvl; lvl++) {
							for (Vector3i offset : topplingBlocks[lvl]) {
								BlockPos newPos = pos.add(offset);
								Block newBlock = world.getBlockState(newPos).getBlock();
								if (newBlock == block) {
									ModHooks.tryHarvestBlock(world, player, newPos);
								}
							}
						}
					}
				}
			}
		}
	}
}
