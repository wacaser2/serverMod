package com.skyline.servermod.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.skyline.servermod.ServerMod;
import com.skyline.servermod.common.items.ModItems;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ServerMod.MODID);
	
	public static List<BlockSet> VARIANT_SETS = new ArrayList<BlockSet>();
	
	public static class BlockSet {
		public Block baseBlock;
		public Item ingot;
		public List<RegistryObject<Block>> variants;
		
		public BlockSet(Block baseBlock, Item ingot, boolean toClear) {
			this.baseBlock = baseBlock;
			this.ingot = ingot;
			this.variants = addVariants(baseBlock, toClear);
		}
		
		public static List<RegistryObject<Block>> addVariants(final Block base, boolean toClear) {
			String baseName = base.getRegistryName().getPath().replace("_block", "");
			
			AbstractBlock.Properties props = toClear? Block.Properties.from(base).notSolid():Block.Properties.from(base);

			List<RegistryObject<Block>> blockSet = new ArrayList<RegistryObject<Block>>();
			blockSet.add(register(baseName + "_stairs", () -> new StairsBlock(() -> base.getDefaultState(), props)));
			blockSet.add(register(baseName + "_slab", () -> new SlabBlock(props)));
			blockSet.add(register(baseName + "_wall", () -> new WallBlock(props)));
			
			return blockSet;
		}
	}
	
	public static RegistryObject<Block> register(final String name, final Supplier<? extends Block> supplier) {
		RegistryObject<Block> block = BLOCKS.register(name, supplier);
		ModItems.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
		return block;
	}
	
	public static BlockSet registerBlockSet(Block baseBlock, Item ingot, boolean toClear) {
		BlockSet bs = new BlockSet(baseBlock, ingot, toClear);
		VARIANT_SETS.add(bs);
		return bs;
	}

	public static final RegistryObject<Block> SHALE = register("shale", () -> new Block(Block.Properties.create(Material.ROCK)));
	public static final RegistryObject<Block> EYE_BLOCK = register("eye_block", () -> new EyeBlock(Block.Properties.create(Material.CLAY)));
	
	public static final BlockSet COAL_SET = registerBlockSet(Blocks.COAL_BLOCK, Items.COAL, false);
	public static final BlockSet IRON_SET = registerBlockSet(Blocks.IRON_BLOCK, Items.IRON_INGOT, false);
	public static final BlockSet GOLD_SET = registerBlockSet(Blocks.GOLD_BLOCK, Items.GOLD_INGOT, false);
	public static final BlockSet DIAMOND_SET = registerBlockSet(Blocks.DIAMOND_BLOCK, Items.DIAMOND, false);
	public static final BlockSet NETHERITE_SET = registerBlockSet(Blocks.field_235397_ng_, Items.field_234759_km_, false);
	public static final BlockSet EMERALD_SET = registerBlockSet(Blocks.EMERALD_BLOCK, Items.EMERALD, false);
	public static final BlockSet LAPIS_SET = registerBlockSet(Blocks.LAPIS_BLOCK, Items.LAPIS_LAZULI, false);
//	public static final BlockSet REDSTONE_SET = registerBlockSet(Blocks.REDSTONE_BLOCK, Items.REDSTONE, false);
//	public static final BlockSet SLIME_SET = registerBlockSet(Blocks.SLIME_BLOCK, Items.SLIME_BALL, true);
	public static final BlockSet GLOW_SET = registerBlockSet(Blocks.GLOWSTONE, Items.GLOWSTONE_DUST, true);
	public static final BlockSet ICE_SET = registerBlockSet(Blocks.ICE, null, true);
	public static final BlockSet PACKED_ICE_SET = registerBlockSet(Blocks.PACKED_ICE, Items.ICE, false);
	public static final BlockSet BLUE_ICE_SET = registerBlockSet(Blocks.BLUE_ICE, Items.PACKED_ICE, true);
	public static final BlockSet OBSIDIAN_SET = registerBlockSet(Blocks.OBSIDIAN, null, false);
	public static final BlockSet GLASS_SET = registerBlockSet(Blocks.GLASS, null, false);
	public static final BlockSet BLACK_STAINED_GLASS_SET = registerBlockSet(Blocks.BLACK_STAINED_GLASS, null, false);
	public static final BlockSet BLUE_STAINED_GLASS_SET = registerBlockSet(Blocks.BLUE_STAINED_GLASS, null, false);
	public static final BlockSet BROWN_STAINED_GLASS_SET = registerBlockSet(Blocks.BROWN_STAINED_GLASS, null, false);
	public static final BlockSet CYAN_STAINED_GLASS_SET = registerBlockSet(Blocks.CYAN_STAINED_GLASS, null, false);
	public static final BlockSet GRAY_STAINED_GLASS_SET = registerBlockSet(Blocks.GRAY_STAINED_GLASS, null, false);
	public static final BlockSet GREEN_STAINED_GLASS_SET = registerBlockSet(Blocks.GREEN_STAINED_GLASS, null, false);
	public static final BlockSet LIGHT_BLUE_STAINED_GLASS_SET = registerBlockSet(Blocks.LIGHT_BLUE_STAINED_GLASS, null, false);
	public static final BlockSet LIGHT_GRAY_STAINED_GLASS_SET = registerBlockSet(Blocks.LIGHT_GRAY_STAINED_GLASS, null, false);
	public static final BlockSet LIME_STAINED_GLASS_SET = registerBlockSet(Blocks.LIME_STAINED_GLASS, null, false);
	public static final BlockSet MAGENTA_STAINED_GLASS_SET = registerBlockSet(Blocks.MAGENTA_STAINED_GLASS, null, false);
	public static final BlockSet ORANGE_STAINED_GLASS_SET = registerBlockSet(Blocks.ORANGE_STAINED_GLASS, null, false);
	public static final BlockSet PINK_STAINED_GLASS_SET = registerBlockSet(Blocks.PINK_STAINED_GLASS, null, false);
	public static final BlockSet PURPLE_STAINED_GLASS_SET = registerBlockSet(Blocks.PURPLE_STAINED_GLASS, null, false);
	public static final BlockSet RED_STAINED_GLASS_SET = registerBlockSet(Blocks.RED_STAINED_GLASS, null, false);
	public static final BlockSet WHITE_STAINED_GLASS_SET = registerBlockSet(Blocks.WHITE_STAINED_GLASS, null, false);
	public static final BlockSet YELLOW_STAINED_GLASS_SET = registerBlockSet(Blocks.YELLOW_STAINED_GLASS, null, false);
}
