package com.skyline.servermod;

import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.blocks.ModBlocks.BlockSet;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class BlockStates extends BlockStateProvider {
	public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, ServerMod.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		addSimpleBlock(ModBlocks.SHALE);
		
		for(BlockSet blockSet : ModBlocks.VARIANT_SETS) {
			registerBlockSet(blockSet);
		}
	}
	
	private void addSimpleBlock(RegistryObject<Block> block) {
		String name = block.get().getRegistryName().getPath();
		simpleBlock(block.get(), models().cubeAll(name, modLoc("block/" + name)));
		
		addBlockItem(block);
	}
	
	private void registerBlockSet(BlockSet blockSet) {
		String baseName = blockSet.baseBlock.getRegistryName().getPath().replace("_block", "");
		ResourceLocation baseLoc = blockSet.baseBlock.getRegistryName();
		ResourceLocation tex = mcLoc("block/" + baseLoc.getPath());
//
		stairsBlock((StairsBlock) blockSet.variants.get(0).get(), baseName, tex);
		slabBlock((SlabBlock) blockSet.variants.get(1).get(), baseLoc, tex);
		wallBlock((WallBlock) blockSet.variants.get(2).get(), baseName, tex);
		models().wallInventory(blockSet.variants.get(2).get().getRegistryName().getPath(), tex);
		
		addBlockItem(blockSet.variants.get(0));
		addBlockItem(blockSet.variants.get(1));
		addBlockItem(blockSet.variants.get(2));
	}
	
	private void addBlockItem(RegistryObject<Block> variant) {
		simpleBlockItem(variant.get(), models().getExistingFile(modLoc("block/" + variant.get().getRegistryName().getPath())));
	}
}