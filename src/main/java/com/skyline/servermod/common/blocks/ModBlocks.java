package com.skyline.servermod.common.blocks;

import java.util.ArrayList;
import java.util.List;

import com.skyline.servermod.ServerMod;
import com.skyline.servermod.common.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public final class ModBlocks {
	public static final List<Block> MOD_BLOCK_LIST = new ArrayList<Block>();
	
	public static Block register(final String modid, final String name, final Block block) {
		MOD_BLOCK_LIST.add(block.setRegistryName(modid, name));
		ModItems.register(modid, name, new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
		return block;
	}

	public static final Block SHALE = register(ServerMod.MODID, "shale",
			new Block(Block.Properties.create(Material.ROCK)));

	public static final Block COAL_STAIRS = register(ServerMod.MODID, "coal_stairs",
			new StairsBlock(() -> Blocks.COAL_BLOCK.getDefaultState(), Block.Properties.from(Blocks.COAL_BLOCK)));

	public static final Block COAL_SLAB = register(ServerMod.MODID, "coal_slab",
			new ModSlabBlock(Block.Properties.from(Blocks.COAL_BLOCK)));

	public static final Block IRON_STAIRS = register(ServerMod.MODID, "iron_stairs",
			new StairsBlock(() -> Blocks.IRON_BLOCK.getDefaultState(), Block.Properties.from(Blocks.IRON_BLOCK)));

	public static final Block IRON_SLAB = register(ServerMod.MODID, "iron_slab",
			new ModSlabBlock(Block.Properties.from(Blocks.IRON_BLOCK)));

	public static final Block GOLD_STAIRS = register(ServerMod.MODID, "gold_stairs",
			new StairsBlock(() -> Blocks.GOLD_BLOCK.getDefaultState(), Block.Properties.from(Blocks.GOLD_BLOCK)));

	public static final Block GOLD_SLAB = register(ServerMod.MODID, "gold_slab",
			new ModSlabBlock(Block.Properties.from(Blocks.GOLD_BLOCK)));

//	public static final Block GLASS_STAIRS = register(ServerMod.MODID, "glass_stairs",
//			new GlassStairsBlock(() -> Blocks.GLASS.getDefaultState(), Block.Properties.from(Blocks.GLASS)));
//
//	public static final Block GLASS_SLAB = register(ServerMod.MODID, "glass_slab",
//			new GlassSlabBlock(Block.Properties.from(Blocks.GLASS)));

	public static final Block DIAMOND_STAIRS = register(ServerMod.MODID, "diamond_stairs",
			new StairsBlock(() -> Blocks.DIAMOND_BLOCK.getDefaultState(), Block.Properties.from(Blocks.DIAMOND_BLOCK)));

	public static final Block DIAMOND_SLAB = register(ServerMod.MODID, "diamond_slab",
			new ModSlabBlock(Block.Properties.from(Blocks.DIAMOND_BLOCK)));

	public static final Block EMERALD_STAIRS = register(ServerMod.MODID, "emerald_stairs",
			new StairsBlock(() -> Blocks.EMERALD_BLOCK.getDefaultState(), Block.Properties.from(Blocks.EMERALD_BLOCK)));

	public static final Block EMERALD_SLAB = register(ServerMod.MODID, "emerald_slab",
			new ModSlabBlock(Block.Properties.from(Blocks.EMERALD_BLOCK)));

	public static final Block LAPIS_STAIRS = register(ServerMod.MODID, "lapis_stairs",
			new StairsBlock(() -> Blocks.LAPIS_BLOCK.getDefaultState(), Block.Properties.from(Blocks.LAPIS_BLOCK)));

	public static final Block LAPIS_SLAB = register(ServerMod.MODID, "lapis_slab",
			new ModSlabBlock(Block.Properties.from(Blocks.LAPIS_BLOCK)));

	public static final Block GLOW_STAIRS = register(ServerMod.MODID, "glow_stairs",
			new StairsBlock(() -> Blocks.GLOWSTONE.getDefaultState(), Block.Properties.from(Blocks.GLOWSTONE)));

	public static final Block GLOW_SLAB = register(ServerMod.MODID, "glow_slab",
			new ModSlabBlock(Block.Properties.from(Blocks.GLOWSTONE)));
}
