package com.skyline.servermod.common.enchantments;

import java.util.Collection;

import com.skyline.servermod.ServerMod;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class SoulboundEnchantmentModifier {
	public static class EventHandler {

		@EventBusSubscriber(modid = ServerMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
		public static class EventHandlers {
			@SubscribeEvent
			public static void onLivingDrops(final LivingDropsEvent event) {
				if (event.getEntityLiving() instanceof PlayerEntity) {
					PlayerEntity player = (PlayerEntity) event.getEntityLiving();
					Collection<ItemEntity> drops = event.getDrops();
					event.setCanceled(drops.removeIf(i -> i.getItem().isEnchanted()
							&& EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SOULBOUND, i.getItem()) > 0
							&& player.addItemStackToInventory(i.getItem())));
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
}
