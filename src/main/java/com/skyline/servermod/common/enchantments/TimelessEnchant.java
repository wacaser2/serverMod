package com.skyline.servermod.common.enchantments;

import com.skyline.servermod.ServerMod;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class TimelessEnchant extends Enchantment {
	public TimelessEnchant() {
		super(Enchantment.Rarity.RARE, ModEnchantHelper.ALL, ModEnchantHelper.SLOT_ALL);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 16;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return 80;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public boolean isTreasureEnchantment() {
		return true;
	}

	@EventBusSubscriber(modid = ServerMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class EventHandlers {
		@SubscribeEvent
		public static void onItemExpire(final ItemExpireEvent event) {
			if (event.getEntityItem().getItem().isEnchanted() && EnchantmentHelper
					.getEnchantmentLevel(ModEnchants.TIMELESS.get(), event.getEntityItem().getItem()) > 0) {
				event.getEntityItem().setNoDespawn();
				event.setCanceled(true);
			}
		}
	}
}
