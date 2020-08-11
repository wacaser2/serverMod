package com.skyline.servermod.common.items;

import com.skyline.servermod.ServerMod;
import com.skyline.servermod.common.blocks.ModBlocks;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class ModItems {
	public static final Item SHALE = new BlockItem(ModBlocks.SHALE,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.SHALE.getRegistryName());

	public static final Item EMERALD_NOTE = new Item(new Item.Properties().group(ItemGroup.MISC))
			.setRegistryName(new ResourceLocation(ServerMod.MODID, "emerald_note"));

	public static final Item REAM = new Item(new Item.Properties().group(ItemGroup.MISC))
			.setRegistryName(new ResourceLocation(ServerMod.MODID, "ream"));

	public static final Item COAL_STAIRS = new BlockItem(ModBlocks.COAL_STAIRS,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.COAL_STAIRS.getRegistryName());

	public static final Item COAL_SLAB = new BlockItem(ModBlocks.COAL_SLAB,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.COAL_SLAB.getRegistryName());

	public static final Item IRON_STAIRS = new BlockItem(ModBlocks.IRON_STAIRS,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.IRON_STAIRS.getRegistryName());

	public static final Item IRON_SLAB = new BlockItem(ModBlocks.IRON_SLAB,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.IRON_SLAB.getRegistryName());

	public static final Item GOLD_STAIRS = new BlockItem(ModBlocks.GOLD_STAIRS,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.GOLD_STAIRS.getRegistryName());

	public static final Item GOLD_SLAB = new BlockItem(ModBlocks.GOLD_SLAB,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.GOLD_SLAB.getRegistryName());

	public static final Item GLASS_STAIRS = new BlockItem(ModBlocks.GLASS_STAIRS,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.GLASS_STAIRS.getRegistryName());

	public static final Item GLASS_SLAB = new BlockItem(ModBlocks.GLASS_SLAB,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.GLASS_SLAB.getRegistryName());

	public static final Item DIAMOND_STAIRS = new BlockItem(ModBlocks.DIAMOND_STAIRS,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.DIAMOND_STAIRS.getRegistryName());

	public static final Item DIAMOND_SLAB = new BlockItem(ModBlocks.DIAMOND_SLAB,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.DIAMOND_SLAB.getRegistryName());

	public static final Item EMERALD_STAIRS = new BlockItem(ModBlocks.EMERALD_STAIRS,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.EMERALD_STAIRS.getRegistryName());

	public static final Item EMERALD_SLAB = new BlockItem(ModBlocks.EMERALD_SLAB,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.EMERALD_SLAB.getRegistryName());

	public static final Item LAPIS_STAIRS = new BlockItem(ModBlocks.LAPIS_STAIRS,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.LAPIS_STAIRS.getRegistryName());

	public static final Item LAPIS_SLAB = new BlockItem(ModBlocks.LAPIS_SLAB,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.LAPIS_SLAB.getRegistryName());

	public static final Item GLOW_STAIRS = new BlockItem(ModBlocks.GLOW_STAIRS,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.GLOW_STAIRS.getRegistryName());

	public static final Item GLOW_SLAB = new BlockItem(ModBlocks.GLOW_SLAB,
			new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
					.setRegistryName(ModBlocks.GLOW_SLAB.getRegistryName());

	public static final Item[] MOD_ITEM_LIST = { SHALE, EMERALD_NOTE, REAM, 
			EMERALD_SLAB, EMERALD_STAIRS, 
			COAL_SLAB, COAL_STAIRS, 
			IRON_SLAB, IRON_STAIRS, 
			GOLD_SLAB, GOLD_STAIRS, 
			DIAMOND_SLAB, DIAMOND_STAIRS, 
			LAPIS_SLAB, LAPIS_STAIRS,
			GLOW_SLAB, GLOW_STAIRS,
			GLASS_SLAB, GLASS_STAIRS,
			};
}
