package com.skyline.servermod;

import java.util.function.Consumer;

import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.blocks.ModBlocks.BlockSet;
import com.skyline.servermod.common.items.ModItems;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class Recipes extends RecipeProvider implements IConditionBuilder {
		public Recipes(DataGenerator gen) {
			super(gen);
		}

		protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
			registerRecipe(consumer, BiRecipe.build(Items.FLINT, ModBlocks.SHALE.get(), Pattern.INGOT, Pattern.BLOCK));
			registerRecipe(consumer, BiRecipe.build(Items.PAPER, ModItems.REAM.get(), Pattern.INGOT, Pattern.BLOCK));
			registerRecipe(consumer, BiRecipe.build(Blocks.EMERALD_BLOCK, ModItems.EMERALD_NOTE.get(), Pattern.INGOT, Pattern.NOTE));
			
			for(BlockSet blockSet : ModBlocks.VARIANT_SETS) {
				registerBlockSetRecipes(consumer, blockSet);
			}
		}
		
		private void registerBlockSetRecipes(Consumer<IFinishedRecipe> consumer, BlockSet blockSet) {
			boolean hasIngot = false;
			
			if(hasIngot) {
				
			}
			
			//block
			registerRecipe(consumer, BiRecipe.build(blockSet.baseBlock, blockSet.variants.get(0).get(), Pattern.BLOCK, Pattern.STAIR));
			registerRecipe(consumer, BiRecipe.build(blockSet.baseBlock, blockSet.variants.get(1).get(), Pattern.BLOCK, Pattern.SLAB));
			registerRecipe(consumer, BiRecipe.build(blockSet.baseBlock, blockSet.variants.get(2).get(), Pattern.BLOCK, Pattern.WALL));

			//stairs
			registerRecipe(consumer, BiRecipe.build(blockSet.variants.get(0).get(), blockSet.variants.get(1).get(), Pattern.STAIR, Pattern.SLAB));
			registerRecipe(consumer, BiRecipe.build(blockSet.variants.get(0).get(), blockSet.variants.get(2).get(), Pattern.STAIR, Pattern.WALL));
			
			//slab
			registerRecipe(consumer, BiRecipe.build(blockSet.variants.get(1).get(), blockSet.variants.get(2).get(), Pattern.SLAB, Pattern.WALL));
		}
		
		private static class SimpleRecipe {
			public Ingredient ingredient;
			public IItemProvider result;
			public Pattern type;
			public int count;
			public String from;
			public String to;
			public InventoryChangeTrigger.Instance trigger;
			
			private SimpleRecipe(Pattern type, int count) {
				this.type = type;
				this.count = count;
			}
			
			public static SimpleRecipe build(Block in, Block out, Pattern type, int count) {
				return new SimpleRecipe(type, count).in(in).out(out);
			}
			
			public static SimpleRecipe build(Item in, Block out, Pattern type, int count) {
				return new SimpleRecipe(type, count).in(in).out(out);
			}
			
			public static SimpleRecipe build(ITag<Item> in, Block out, Pattern type, int count, String path) {
				return new SimpleRecipe(type, count).in(in, path).out(out);
			}
			
			public static SimpleRecipe build(Block in, Item out, Pattern type, int count) {
				return new SimpleRecipe(type, count).in(in).out(out);
			}
			
			public static SimpleRecipe build(Item in, Item out, Pattern type, int count) {
				return new SimpleRecipe(type, count).in(in).out(out);
			}
			
			public static SimpleRecipe build(ITag<Item> in, Item out, Pattern type, int count, String path) {
				return new SimpleRecipe(type, count).in(in, path).out(out);
			}
			
			private SimpleRecipe in(Block in) {
				ingredient = Ingredient.fromItems(in);
				from = in.getRegistryName().getPath();
				trigger = hasItem(in);
				return this;
			}
			
			private SimpleRecipe in(Item in) {
				ingredient = Ingredient.fromItems(in);
				from = in.getRegistryName().getPath();
				trigger = hasItem(in);
				return this;
			}
			
			private SimpleRecipe in(ITag<Item> in, String path) {
				ingredient = Ingredient.fromTag(in);
				from = path;
				trigger = hasItem(in);
				return this;
			}
			
			private SimpleRecipe out(Block out) {
				result = out;
				to = out.getRegistryName().getPath();
				return this;
			}
			
			private SimpleRecipe out(Item out) {
				result = out;
				to = out.getRegistryName().getPath();
				return this;
			}
		}
		
		private static class BiRecipe {
			public SimpleRecipe left, right;
			
			private BiRecipe(SimpleRecipe left, SimpleRecipe right) {
				this.left = left;
				this.right = right;
			}
			
			public static BiRecipe build(Block left, Block right, Pattern typeLeft, Pattern typeRight) {
				return new BiRecipe(SimpleRecipe.build(left, right, typeRight, typeLeft.value), SimpleRecipe.build(right, left, typeLeft, typeRight.value));
			}
			
			public static BiRecipe build(Item left, Block right, Pattern typeLeft, Pattern typeRight) {
				return new BiRecipe(SimpleRecipe.build(left, right, typeRight, typeLeft.value), SimpleRecipe.build(right, left, typeLeft, typeRight.value));
			}
			
			public static BiRecipe build(Block left, Item right, Pattern typeLeft, Pattern typeRight) {
				return new BiRecipe(SimpleRecipe.build(left, right, typeRight, typeLeft.value), SimpleRecipe.build(right, left, typeLeft, typeRight.value));
			}
			
			public static BiRecipe build(Item left, Item right, Pattern typeLeft, Pattern typeRight) {
				return new BiRecipe(SimpleRecipe.build(left, right, typeRight, typeLeft.value), SimpleRecipe.build(right, left, typeLeft, typeRight.value));
			}
		}
		
		private void registerRecipe(Consumer<IFinishedRecipe> consumer, BiRecipe recipe) {
			registerRecipe(consumer, recipe.left);
			registerRecipe(consumer, recipe.right);
		}
		
		private void registerRecipe(Consumer<IFinishedRecipe> consumer, SimpleRecipe recipe) {
			registerRecipe(consumer, recipe.ingredient, recipe.result, recipe.type, recipe.count, recipe.from, recipe.to, recipe.trigger);
		}
		
		private void registerRecipe(Consumer<IFinishedRecipe> consumer, Ingredient ingredient, IItemProvider result, Pattern type, int count, String from, String to, InventoryChangeTrigger.Instance trigger) {
			ResourceLocation id = new ResourceLocation(ServerMod.MODID, from +"_to_" + to);
			
			Consumer<Consumer<IFinishedRecipe>> recipe = null;
			if (type.equals(Pattern.INGOT)) {
				recipe = ShapelessRecipeBuilder.shapelessRecipe(result, count).addIngredient(ingredient).setGroup(to).addCriterion("has_" + from, trigger)::build;
			} else {
				ShapedRecipeBuilder shapedRecipe = ShapedRecipeBuilder.shapedRecipe(result, count);
				switch (type) {
				case BLOCK:
					shapedRecipe = shapedRecipe.patternLine("XX").patternLine("XX");
					break;
				case STAIR:
					shapedRecipe = shapedRecipe.patternLine(" X").patternLine("XX");
					break;
				case SLAB:
					shapedRecipe = shapedRecipe.patternLine("XX");
					break;
				case WALL:
					shapedRecipe = shapedRecipe.patternLine("X").patternLine("X");
					break;
				case NOTE:
					shapedRecipe = shapedRecipe.patternLine("X X").patternLine("   ").patternLine("X X");
					break;
				default:
					shapedRecipe = shapedRecipe.patternLine("X X").patternLine(" X ").patternLine("X X");
					break;
				}
				recipe = shapedRecipe.key('X', ingredient).setGroup(to).addCriterion("has_" + from, trigger)::build;
			}
			
			ConditionalRecipe.builder()
				.addCondition(modLoaded(ServerMod.MODID))
				.addRecipe(recipe)
				.setAdvancement(new ResourceLocation(ServerMod.MODID, "conditional_" + id.getPath()), 
					ConditionalAdvancement.builder()
						.addCondition(modLoaded(ServerMod.MODID))
						.addAdvancement(
							Advancement.Builder.builder()
								.withParentId(new ResourceLocation("minecraft", "root"))
								.withDisplay(result, new StringTextComponent(id.getPath()),
									new StringTextComponent(id.getPath()), null, FrameType.TASK, false, false, false)
								.withRewards(AdvancementRewards.Builder.recipe(id))
								.withCriterion("has_" + from, trigger)
				)).build(consumer, id);
		}

		private enum Pattern {
			BLOCK(4), 
			STAIR(3), 
			SLAB(2), 
			WALL(2), 
			INGOT(1), 
			NOTE(4);
			
			public int value;
			
			private Pattern(int value) {
				this.value = value;
			}
		}
	}