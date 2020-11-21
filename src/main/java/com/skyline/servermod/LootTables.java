package com.skyline.servermod;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.blocks.ModBlocks.BlockSet;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.util.ResourceLocation;

public class LootTables extends LootTableProvider {
	private static final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = ImmutableList
			.of(Pair.of(Blocks::new, LootParameterSets.BLOCK));

	public LootTables(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
		return tables;
	}
	
	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
		map.forEach((p_218436_2_, p_218436_3_) -> {
			LootTableManager.func_227508_a_(validationtracker, p_218436_2_, p_218436_3_);
		});
	}

	public static class Blocks extends BlockLootTables {
		@Override
		protected void addTables() {
			registerDropSelfLootTable(ModBlocks.SHALE.get());
			registerDropSelfLootTable(ModBlocks.EYE_BLOCK.get());
			
			for (BlockSet blockSet : ModBlocks.VARIANT_SETS) {
				addBlockSet(blockSet);
			}
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return () -> Stream.concat(
				Stream.of(ModBlocks.SHALE.get(), ModBlocks.EYE_BLOCK.get()), 
				ModBlocks.VARIANT_SETS.stream().flatMap(bs -> bs.variants.stream()).map(v -> v.get())
			).iterator();
		}

		private void addBlockSet(BlockSet blockSet) {
			registerDropSelfLootTable(blockSet.variants.get(0).get());
			registerLootTable(blockSet.variants.get(1).get(), BlockLootTables::droppingSlab);
			registerDropSelfLootTable(blockSet.variants.get(2).get());
		}
	}
}