package com.skyline.servermod;

import java.util.function.Consumer;

import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.blocks.ModBlocks.BlockSet;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class Recipes extends RecipeProvider implements IConditionBuilder {
		public Recipes(DataGenerator gen) {
			super(gen);
		}

		protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
			
			for(BlockSet blockSet : ModBlocks.VARIANT_SETS) {
				registerBlockSetRecipes(consumer, blockSet);
			}
//			
//			
//			ResourceLocation ID = new ResourceLocation("data_gen_test", "conditional");
//
//			ConditionalRecipe.builder()
//					.addCondition(and(not(modLoaded("minecraft")), itemExists("minecraft", "dirt"), FALSE()))
//					.addRecipe(ShapedRecipeBuilder.shapedRecipe(Blocks.DIAMOND_BLOCK, 64).patternLine("XXX")
//							.patternLine("XXX").patternLine("XXX").key('X', Blocks.DIRT).setGroup("")
//							.addCriterion("has_dirt", hasItem(Blocks.DIRT)) // DUMMY: Necessary, but not used when a
//																			// custom advancement is provided through
//																			// setAdvancement
//					::build)
//					.setAdvancement(ID, ConditionalAdvancement.builder()
//							.addCondition(and(not(modLoaded("minecraft")), itemExists("minecraft", "dirt"), FALSE()))
//							.addAdvancement(Advancement.Builder.builder()
//									.withParentId(new ResourceLocation("minecraft", "root"))
//									.withDisplay(Blocks.DIAMOND_BLOCK, new StringTextComponent("Dirt2Diamonds"),
//											new StringTextComponent("The BEST crafting recipe in the game!"), null,
//											FrameType.TASK, false, false, false)
//									.withRewards(AdvancementRewards.Builder.recipe(ID))
//									.withCriterion("has_dirt", hasItem(Blocks.DIRT))))
//					.build(consumer, ID);
		}
		
		private void registerBlockSetRecipes(Consumer<IFinishedRecipe> consumer, BlockSet blockSet) {
			boolean hasIngot = false;
			
			if(hasIngot) {
				
			}
			
			//block
			registerRecipe(consumer, blockSet.variants.get(0).get(), blockSet.baseBlock, Pattern.BLOCK, 3);
			registerRecipe(consumer, blockSet.variants.get(1).get(), blockSet.baseBlock, Pattern.BLOCK, 2);
			registerRecipe(consumer, blockSet.variants.get(2).get(), blockSet.baseBlock, Pattern.BLOCK, 2);
			//stair
			registerRecipe(consumer, blockSet.baseBlock, blockSet.variants.get(0).get(), Pattern.STAIR, 4);
			registerRecipe(consumer, blockSet.variants.get(1).get(), blockSet.variants.get(0).get(), Pattern.STAIR, 2);
			registerRecipe(consumer, blockSet.variants.get(2).get(), blockSet.variants.get(0).get(), Pattern.STAIR, 2);
			//slab
			registerRecipe(consumer, blockSet.baseBlock, blockSet.variants.get(1).get(), Pattern.SLAB, 4);
			registerRecipe(consumer, blockSet.variants.get(0).get(), blockSet.variants.get(1).get(), Pattern.SLAB, 3);
			registerRecipe(consumer, blockSet.variants.get(2).get(), blockSet.variants.get(1).get(), Pattern.SLAB, 2);
			//wall
			registerRecipe(consumer, blockSet.baseBlock, blockSet.variants.get(2).get(), Pattern.WALL, 4);
			registerRecipe(consumer, blockSet.variants.get(0).get(), blockSet.variants.get(2).get(), Pattern.WALL, 3);
			registerRecipe(consumer, blockSet.variants.get(1).get(), blockSet.variants.get(2).get(), Pattern.WALL, 2);
			
//			String id = blockSet.name.toLowerCase();
			//block recipe
//			ConditionalRecipe.builder()
//				.addCondition(modLoaded(MODID))
//				.addRecipe(
//					ShapedRecipeBuilder.shapedRecipe(blockSet.baseBlock, 1)
//						.patternLine("XX")
//						.patternLine("XX")
//						.key('X', blockSet.baseItem)
//						.setGroup("")
//						.addCriterion("has_" + blockSet.name, hasItem(blockSet.baseItem))
//						::build
//				)
//				.setAdvancement(new ResourceLocation(MODID, "conditional_" + id), ConditionalAdvancement.builder()
//						.addCondition(modLoaded(MODID)))
//						.addAdvancement(Advancement.Builder.builder()
//								.withParentId(new ResourceLocation("minecraft", "root"))
//								.withDisplay(Blocks.DIAMOND_BLOCK, new StringTextComponent("Dirt2Diamonds"),
//										new StringTextComponent("The BEST crafting recipe in the game!"), null,
//										FrameType.TASK, false, false, false)
//								.withRewards(AdvancementRewards.Builder.recipe(ID))
//								.withCriterion("has_dirt", hasItem(Blocks.DIRT))))
//				.build(consumer, new ResourceLocation("minecraft", id + "_block"));
		}
		
		private void registerRecipe(Consumer<IFinishedRecipe> consumer, Block ingredient, Block result, Pattern type, int value) {
			ResourceLocation id = new ResourceLocation(ServerMod.MODID, ingredient.getRegistryName().getPath()+"_to_"+ result.getRegistryName().getPath());
			
			ShapedRecipeBuilder shapedRecipe = ShapedRecipeBuilder.shapedRecipe(result, value);
			
			switch(type) {
				case BLOCK:
					shapedRecipe = shapedRecipe.patternLine("XX").patternLine("XX");
					break;
				case STAIR:
					shapedRecipe = shapedRecipe.patternLine("X").patternLine("XX");
					break;
				case SLAB:
					shapedRecipe = shapedRecipe.patternLine("XX");
					break;
				case WALL:
					shapedRecipe = shapedRecipe.patternLine("X").patternLine("X");
					break;
				case INGOT:
					shapedRecipe = shapedRecipe.patternLine("X");
					break;
			}
			shapedRecipe = shapedRecipe.key('X', ingredient)
			.setGroup(result.getRegistryName().getPath())
			.addCriterion("has_" + ingredient.getRegistryName().getPath(), hasItem(ingredient.asItem()));
		}
		
		private enum Pattern {
			BLOCK, STAIR, SLAB, WALL, INGOT;
		}
		
		
		
		
		
	}