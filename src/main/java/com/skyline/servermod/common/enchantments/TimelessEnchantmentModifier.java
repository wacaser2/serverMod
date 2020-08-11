package main.java.com.skyline.servermod.common.enchantments;

import main.java.com.skyline.servermod.ServerMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class TimelessEnchantmentModifier {
	public static class EventHandler {

		@EventBusSubscriber(modid = ServerMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
		public static class EventHandlers {
			@SubscribeEvent
			public static void onItemExpire(final ItemExpireEvent event) {
				if (event.getEntityItem().getItem().isEnchanted() && EnchantmentHelper
						.getEnchantmentLevel(ModEnchantments.TIMELESS, event.getEntityItem().getItem()) > 0) {
					event.getEntityItem().setNoDespawn();
					event.setCanceled(true);
				}
			}
		}
	}
}
