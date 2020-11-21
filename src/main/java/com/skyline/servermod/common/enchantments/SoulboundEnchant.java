package com.skyline.servermod.common.enchantments;

import java.util.Collection;

import com.skyline.servermod.ServerMod;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class SoulboundEnchant extends Enchantment {
	public SoulboundEnchant() {
		super(Enchantment.Rarity.VERY_RARE, ModEnchantHelper.ALL, ModEnchantHelper.SLOT_ALL);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 64;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return 192;
	}

	@Override
	public int getMaxLevel() { return 1; }

	@Override
	public boolean isTreasureEnchantment() { return false; }

	public boolean func_230309_h_() {
		return false;
	}

	@EventBusSubscriber(modid = ServerMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class EventHandlers {
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
	}
}
