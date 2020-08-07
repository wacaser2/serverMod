package main.java.com.skyline.servermod.common.blocks;

import main.java.com.skyline.servermod.ServerMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class ModBlocks {
	public static final Block SHALE = new Block(Block.Properties.create(Material.ROCK))
			.setRegistryName(new ResourceLocation(ServerMod.MODID, "shale"));

	public static final Block COAL_STAIRS = new StairsBlock(() -> Blocks.COAL_BLOCK.getDefaultState(),
			Block.Properties.from(Blocks.COAL_BLOCK)).setRegistryName(ServerMod.MODID, "coal_stairs");

	public static final Block COAL_SLAB = new ModSlabBlock(Block.Properties.from(Blocks.COAL_BLOCK))
			.setRegistryName(ServerMod.MODID, "coal_slab");

	public static final Block IRON_STAIRS = new StairsBlock(() -> Blocks.IRON_BLOCK.getDefaultState(),
			Block.Properties.from(Blocks.IRON_BLOCK)).setRegistryName(ServerMod.MODID, "iron_stairs");

	public static final Block IRON_SLAB = new ModSlabBlock(Block.Properties.from(Blocks.IRON_BLOCK))
			.setRegistryName(ServerMod.MODID, "iron_slab");

	public static final Block GOLD_STAIRS = new StairsBlock(() -> Blocks.GOLD_BLOCK.getDefaultState(),
			Block.Properties.from(Blocks.GOLD_BLOCK)).setRegistryName(ServerMod.MODID, "gold_stairs");

	public static final Block GOLD_SLAB = new ModSlabBlock(Block.Properties.from(Blocks.GOLD_BLOCK))
			.setRegistryName(ServerMod.MODID, "gold_slab");

	public static final Block GLASS_STAIRS = new GlassStairsBlock(() -> Blocks.GLASS.getDefaultState(),
			Block.Properties.from(Blocks.GLASS)).setRegistryName(ServerMod.MODID, "glass_stairs");

	public static final Block GLASS_SLAB = new GlassSlabBlock(Block.Properties.from(Blocks.GLASS))
			.setRegistryName(ServerMod.MODID, "glass_slab");

	public static final Block DIAMOND_STAIRS = new StairsBlock(() -> Blocks.DIAMOND_BLOCK.getDefaultState(),
			Block.Properties.from(Blocks.DIAMOND_BLOCK)).setRegistryName(ServerMod.MODID, "diamond_stairs");

	public static final Block DIAMOND_SLAB = new ModSlabBlock(Block.Properties.from(Blocks.DIAMOND_BLOCK))
			.setRegistryName(ServerMod.MODID, "diamond_slab");

	public static final Block EMERALD_STAIRS = new StairsBlock(() -> Blocks.EMERALD_BLOCK.getDefaultState(),
			Block.Properties.from(Blocks.EMERALD_BLOCK)).setRegistryName(ServerMod.MODID, "emerald_stairs");

	public static final Block EMERALD_SLAB = new ModSlabBlock(Block.Properties.from(Blocks.EMERALD_BLOCK))
			.setRegistryName(ServerMod.MODID, "emerald_slab");

	public static final Block LAPIS_STAIRS = new StairsBlock(() -> Blocks.LAPIS_BLOCK.getDefaultState(),
			Block.Properties.from(Blocks.LAPIS_BLOCK)).setRegistryName(ServerMod.MODID, "lapis_stairs");

	public static final Block LAPIS_SLAB = new ModSlabBlock(Block.Properties.from(Blocks.LAPIS_BLOCK))
			.setRegistryName(ServerMod.MODID, "lapis_slab");

	public static final Block GLOW_STAIRS = new StairsBlock(() -> Blocks.GLOWSTONE.getDefaultState(),
			Block.Properties.from(Blocks.GLOWSTONE)).setRegistryName(ServerMod.MODID, "glow_stairs");

	public static final Block GLOW_SLAB = new ModSlabBlock(Block.Properties.from(Blocks.GLOWSTONE))
			.setRegistryName(ServerMod.MODID, "glow_slab");
}
