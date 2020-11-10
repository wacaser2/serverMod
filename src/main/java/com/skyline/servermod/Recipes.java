package com.skyline.servermod;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class Recipes extends RecipeProvider implements IConditionBuilder {
		public Recipes(DataGenerator gen) {
			super(gen);
		}

		protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
//			
//			for(BlockSet blockSet : ModBlocks.VARIANT_SETS) {
//				registerBlockSetRecipes(consumer, blockSet);
//			}
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
		
//		private void registerBlockSetRecipes(Consumer<IFinishedRecipe> consumer, BlockSet blockSet) {
//			String id = blockSet.name.toLowerCase();
//			//block recipe
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
//		}
	}