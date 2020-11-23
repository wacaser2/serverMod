package com.skyline.servermod;

import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.blocks.ModBlocks.BlockSet;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;

public class Tags extends BlockTagsProvider {
	public Tags(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected void registerTags() {
		Builder<Block> stairs = func_240522_a_(BlockTags.makeWrapperTag("minecraft:stairs"));
		Builder<Block> slabs = func_240522_a_(BlockTags.makeWrapperTag("minecraft:slabs"));
		Builder<Block> walls = func_240522_a_(BlockTags.makeWrapperTag("minecraft:walls"));
		
		for (BlockSet blockSet : ModBlocks.VARIANT_SETS) {
			stairs = stairs.func_240532_a_(blockSet.variants.get(0).get());
			slabs = slabs.func_240532_a_(blockSet.variants.get(1).get());
			walls = walls.func_240532_a_(blockSet.variants.get(2).get());
		}
	}
}