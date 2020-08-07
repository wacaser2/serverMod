package main.java.com.skyline.servermod;

import javax.annotation.Nonnull;

import main.java.com.skyline.servermod.common.blocks.ModBlocks;
import main.java.com.skyline.servermod.common.enchantments.ModEnchantments;
import main.java.com.skyline.servermod.common.enchantments.SmeltingEnchantmentModifier;
import main.java.com.skyline.servermod.common.items.ModItems;
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
			blockRegistryEvent.getRegistry().registerAll(ModBlocks.SHALE
					, ModBlocks.COAL_STAIRS, ModBlocks.COAL_SLAB
					, ModBlocks.IRON_STAIRS, ModBlocks.IRON_SLAB
					, ModBlocks.GOLD_STAIRS, ModBlocks.GOLD_SLAB
					, ModBlocks.DIAMOND_STAIRS, ModBlocks.DIAMOND_SLAB
					, ModBlocks.EMERALD_STAIRS, ModBlocks.EMERALD_SLAB
					, ModBlocks.LAPIS_STAIRS, ModBlocks.LAPIS_SLAB
					, ModBlocks.GLOW_STAIRS, ModBlocks.GLOW_SLAB
//					, ModBlocks.GLASS_STAIRS, ModBlocks.GLASS_SLAB
			);
		}

		@SubscribeEvent
		public static void onItemsRegistry(@Nonnull final RegistryEvent.Register<Item> itemRegistryEvent) {
			itemRegistryEvent.getRegistry().registerAll(ModItems.SHALE, ModItems.EMERALD_NOTE, ModItems.REAM
					, ModItems.COAL_STAIRS, ModItems.COAL_SLAB
					, ModItems.IRON_STAIRS, ModItems.IRON_SLAB
					, ModItems.GOLD_STAIRS, ModItems.GOLD_SLAB
					, ModItems.DIAMOND_STAIRS, ModItems.DIAMOND_SLAB
					, ModItems.EMERALD_STAIRS, ModItems.EMERALD_SLAB
					, ModItems.LAPIS_STAIRS, ModItems.LAPIS_SLAB
					, ModItems.GLOW_STAIRS, ModItems.GLOW_SLAB
//					, ModItems.GLASS_STAIRS, ModItems.GLASS_SLAB
			);
		}

		@SubscribeEvent
		public static void registerEnchantments(
				@Nonnull final RegistryEvent.Register<Enchantment> enchantRegistryEvent) {
			enchantRegistryEvent.getRegistry().registerAll(ModEnchantments.BLASTING, ModEnchantments.SMELTING,
					ModEnchantments.MOD_LOOTING, ModEnchantments.MOD_FORTUNE);
		}

		@SubscribeEvent
		public static void registerModifierSerializers(
				@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
			event.getRegistry().register(new SmeltingEnchantmentModifier.Serializer()
					.setRegistryName(new ResourceLocation(MODID, "smelting")));
		}
	}
}
