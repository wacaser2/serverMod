package com.skyline.servermod.common.enchantments;

import com.skyline.servermod.ServerMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class ReaperEnchant extends Enchantment {
	public ReaperEnchant() {
		super(Enchantment.Rarity.VERY_RARE, ModEnchantHelper.HOE, ModEnchantHelper.SLOT_HAND);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 30;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	@Override
	public int getMaxLevel() { return 1; }

	@Override
	public boolean isTreasureEnchantment() { return true; }

	@EventBusSubscriber(modid = ServerMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class EventHandlers {
		@SubscribeEvent
		public static void canReapBlock(final BlockEvent.BreakEvent event) {
			PlayerEntity player = event.getPlayer();
			if (player != null) {
				BlockState state = event.getState();
				Block block = state.getBlock();
				ItemStack tool = player.getHeldItemMainhand();
				if (tool.isEnchanted() && EnchantmentHelper.getEnchantmentLevel(ModEnchants.REAPER.get(), tool) > 0) {
					if (block instanceof CropsBlock && !((CropsBlock) block).isMaxAge(state)) {
						event.setCanceled(true);
					}
				}
			}
		}
	}
}
