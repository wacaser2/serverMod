package com.skyline.servermod;

import javax.annotation.Nonnull;

import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.enchantments.ModEnchantments;
import com.skyline.servermod.common.enchantments.SmeltingEnchantmentModifier;
import com.skyline.servermod.common.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod(ServerMod.MODID)
public class ServerMod {
	public static final String MODID = "servermod";

	public ServerMod() {
	}

	@EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onBlocksRegistry(@Nonnull final RegistryEvent.Register<Block> blockRegistryEvent) {
			blockRegistryEvent.getRegistry().registerAll(ModBlocks.MOD_BLOCK_LIST);
		}

		@SubscribeEvent
		public static void onItemsRegistry(@Nonnull final RegistryEvent.Register<Item> itemRegistryEvent) {
			itemRegistryEvent.getRegistry().registerAll(ModItems.MOD_ITEM_LIST);
		}

		@SubscribeEvent
		public static void registerEnchantments(
				@Nonnull final RegistryEvent.Register<Enchantment> enchantRegistryEvent) {
			enchantRegistryEvent.getRegistry().registerAll(ModEnchantments.MOD_ENCHANT_LIST);
		}

		@SubscribeEvent
		public static void registerModifierSerializers(
				@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
			event.getRegistry().register(new SmeltingEnchantmentModifier.Serializer()
					.setRegistryName(new ResourceLocation(MODID, "smelting")));
		}
	}
}
