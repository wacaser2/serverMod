package com.skyline.servermod;

import javax.annotation.Nonnull;

import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.commands.FactionListCommand;
import com.skyline.servermod.common.commands.FactionRegisterCommand;
import com.skyline.servermod.common.commands.FactionSwitchCommand;
import com.skyline.servermod.common.enchantments.ModEnchantments;
import com.skyline.servermod.common.enchantments.SmeltingEnchantmentModifier;
import com.skyline.servermod.common.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod(ServerMod.MODID)
public class ServerMod {
	public static final String MODID = "servermod";

	public ServerMod() {
	}

	@EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class ForgeRegistryEvents {
		@SubscribeEvent
		public static void registerCommands(@Nonnull final RegisterCommandsEvent event) {
			FactionRegisterCommand.register(event.getDispatcher());
			FactionListCommand.register(event.getDispatcher());
			FactionSwitchCommand.register(event.getDispatcher());
		}
	}

	@EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void registerBlocks(@Nonnull final RegistryEvent.Register<Block> event) {
			ModBlocks.MOD_BLOCK_LIST.forEach(b -> event.getRegistry().register(b));
		}

		@SubscribeEvent
		public static void registerItems(@Nonnull final RegistryEvent.Register<Item> event) {
			ModItems.MOD_ITEM_LIST.forEach(i -> event.getRegistry().register(i));
		}

		@SubscribeEvent
		public static void registerEnchantments(@Nonnull final RegistryEvent.Register<Enchantment> event) {
			ModEnchantments.MOD_ENCHANTMENT_LIST.forEach(e -> event.getRegistry().register(e));
		}

		@SubscribeEvent
		public static void registerModifierSerializers(
				@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
			event.getRegistry()
					.register(new SmeltingEnchantmentModifier.Serializer().setRegistryName(MODID, "smelting"));
		}
	}
}
