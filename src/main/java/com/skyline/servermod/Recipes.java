package com.skyline.servermod;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.blocks.ModBlocks.BlockSet;
import com.skyline.servermod.common.items.ModItems;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.Ingredient.IItemList;
import net.minecraft.item.crafting.Ingredient.SingleItemList;
import net.minecraft.item.crafting.Ingredient.TagList;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class Recipes extends RecipeProvider implements IConditionBuilder {
	private Map<Item, ITag<Item>> replacements = new HashMap<>();

	public Recipes(DataGenerator gen) {
		super(gen);
	}

	private void replace(Item item, ITag<Item> tag) {
		replacements.put(item, tag);
	}

	private Ingredient mix(Ingredient item) {
		List<IItemList> items = new ArrayList<>();
		IItemList[] vanillaItems = getField(Ingredient.class, item, 2); // This will probably crash between versions, if
																		// null fix index
		for (IItemList entry : vanillaItems) {
			if (entry instanceof SingleItemList) {
				ItemStack stack = entry.getStacks().stream().findFirst().orElse(ItemStack.EMPTY);
				ITag<Item> replacement = replacements.get(stack.getItem());
				if (replacement != null) {
					items.add(new TagList(replacement));
				} else items.add(entry);
			} else items.add(entry);
		}
		return Ingredient.fromItemListStream(items.stream());
	}

	@SuppressWarnings("unchecked")
	private <T, R> R getField(Class<T> clz, T inst, int index) {
		Field fld = clz.getDeclaredFields()[index];
		fld.setAccessible(true);
		try {
			return (R) fld.get(inst);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		replace(Items.COAL, ItemTags.COALS);

		registerRecipe(consumer, BiRecipe.build(Items.FLINT, ModBlocks.SHALE.get(), Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.ENDER_PEARL, ModBlocks.EYE_BLOCK.get(), Pattern.INGOT, Pattern.BLOCK));

		registerRecipe(consumer, BiRecipe.build(Items.PAPER, ModItems.REAM.get(), Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.CARROT, ModItems.CARROT_BUSHEL.get(), Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.POTATO, ModItems.POTATO_BUSHEL.get(), Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.BEETROOT, ModItems.BEETROOT_BUSHEL.get(), Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.BEETROOT_SEEDS, ModItems.BEETROOT_SEEDS_FEEDBAG.get(), Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.MELON_SEEDS, ModItems.MELON_SEEDS_FEEDBAG.get(), Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.PUMPKIN_SEEDS, ModItems.PUMPKIN_SEEDS_FEEDBAG.get(), Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.WHEAT_SEEDS, ModItems.WHEAT_SEEDS_FEEDBAG.get(), Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.ARROW, ModItems.QUIVER.get(), Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.EGG, ModItems.CARTON.get(), Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.FEATHER, ModItems.PILLOW.get(), Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.EMERALD_BLOCK, ModItems.EMERALD_NOTE.get(), Pattern.INGOT, Pattern.NOTE));
		registerRecipe(consumer, BiRecipe.build(ModItems.EMERALD_NOTE.get(), ModItems.RUBY.get(), Pattern.INGOT, Pattern.NOTE));

		registerRecipe(consumer, BiRecipe.build(Items.LEATHER, Items.SADDLE, Pattern.INGOT, Pattern.BLOCK));

		registerBlockRecipes(consumer);

		for (BlockSet blockSet : ModBlocks.VARIANT_SETS) {
			registerBlockSetRecipes(consumer, blockSet);
		}
	}

	private void registerBlockRecipes(Consumer<IFinishedRecipe> consumer) {
		// ingots-blocks
		registerVanillaRecipe(consumer, BiRecipe.build(Items.COAL, Blocks.COAL_BLOCK, Pattern.INGOT, Pattern.BLOCK));
		registerVanillaRecipe(consumer, BiRecipe.build(Items.DIAMOND, Blocks.DIAMOND_BLOCK, Pattern.INGOT, Pattern.BLOCK));
		registerVanillaRecipe(consumer, BiRecipe.build(Items.EMERALD, Blocks.EMERALD_BLOCK, Pattern.INGOT, Pattern.BLOCK));
		registerVanillaRecipe(consumer, BiRecipe.build(Items.LAPIS_LAZULI, Blocks.LAPIS_BLOCK, Pattern.INGOT, Pattern.BLOCK));
		registerVanillaRecipe(consumer, BiRecipe.build(Items.REDSTONE, Blocks.REDSTONE_BLOCK, Pattern.INGOT, Pattern.BLOCK));
		registerVanillaRecipe(consumer, BiRecipe.build(Items.SLIME_BALL, Blocks.SLIME_BLOCK, Pattern.INGOT, Pattern.BLOCK));

		registerVanillaRecipe(consumer, SimpleRecipe.build(Blocks.ICE, Items.PACKED_ICE, Pattern.BLOCK, 1));
		registerVanillaRecipe(consumer, SimpleRecipe.build(Blocks.PACKED_ICE, Items.BLUE_ICE, Pattern.BLOCK, 1));
		registerVanillaRecipe(consumer, SimpleRecipe.build(Items.IRON_INGOT, Blocks.IRON_BLOCK, Pattern.BLOCK, 1));
		registerVanillaRecipe(consumer, SimpleRecipe.build(Items.GOLD_INGOT, Blocks.GOLD_BLOCK, Pattern.BLOCK, 1));
		registerVanillaRecipe(consumer, SimpleRecipe.build(Items.field_234759_km_, Blocks.field_235397_ng_, Pattern.BLOCK, 1)); // netherite
		registerVanillaRecipe(consumer, SimpleRecipe.build(Items.IRON_INGOT, Items.IRON_NUGGET, Pattern.INGOT, 4));
		registerVanillaRecipe(consumer, SimpleRecipe.build(Items.GOLD_INGOT, Items.GOLD_NUGGET, Pattern.INGOT, 4));

		registerRecipe(consumer, SimpleRecipe.build(Blocks.IRON_BLOCK, Items.IRON_INGOT, Pattern.INGOT, 4), mcLoc("iron_ingot_from_iron_block"));
		registerRecipe(consumer, SimpleRecipe.build(Blocks.GOLD_BLOCK, Items.GOLD_INGOT, Pattern.INGOT, 4), mcLoc("gold_ingot_from_gold_block"));
		registerRecipe(consumer, SimpleRecipe.build(Blocks.field_235397_ng_, Items.field_234759_km_, Pattern.INGOT, 4), mcLoc("netherite_ingot_from_netherite_block")); // netherite
		registerRecipe(consumer, SimpleRecipe.build(Items.IRON_NUGGET, Items.IRON_INGOT, Pattern.BLOCK, 1), mcLoc("iron_ingot_from_nuggets"));
		registerRecipe(consumer, SimpleRecipe.build(Items.GOLD_NUGGET, Items.GOLD_INGOT, Pattern.BLOCK, 1), mcLoc("gold_ingot_from_nuggets"));

		registerRecipe(consumer, SimpleRecipe.build(Blocks.GLOWSTONE, Items.GLOWSTONE_DUST, Pattern.INGOT, 4));
		registerRecipe(consumer, SimpleRecipe.build(Blocks.PACKED_ICE, Items.ICE, Pattern.INGOT, 4));
		registerRecipe(consumer, SimpleRecipe.build(Blocks.BLUE_ICE, Items.PACKED_ICE, Pattern.INGOT, 4));

		// crops
		registerVanillaRecipe(consumer, BiRecipe.build(Items.WHEAT, Blocks.HAY_BLOCK, Pattern.INGOT, Pattern.BLOCK));
		registerVanillaRecipe(consumer, BiRecipe.build(Items.DRIED_KELP, Blocks.DRIED_KELP_BLOCK, Pattern.INGOT, Pattern.BLOCK));

		registerVanillaRecipe(consumer, SimpleRecipe.build(Items.NETHER_WART, Items.NETHER_WART_BLOCK, Pattern.BLOCK, 1));

		registerRecipe(consumer, SimpleRecipe.build(Items.NETHER_WART_BLOCK, Items.NETHER_WART, Pattern.INGOT, 4));

		// wood
		registerVanillaRecipe(consumer, SimpleRecipe.build(ItemTags.PLANKS, Items.STICK, Pattern.WALL, 8, "planks"));
		registerVanillaRecipe(consumer, SimpleRecipe.build(Items.OAK_LOG, Items.OAK_PLANKS, Pattern.INGOT, 4));
		registerVanillaRecipe(consumer, SimpleRecipe.build(Items.OAK_LOG, Items.OAK_WOOD, Pattern.BLOCK, 1));

		registerRecipe(consumer, SimpleRecipe.build(Items.OAK_WOOD, Items.OAK_LOG, Pattern.INGOT, 4));
		registerRecipe(consumer, SimpleRecipe.build(Items.OAK_PLANKS, Items.OAK_LOG, Pattern.BLOCK, 1));
		registerRecipe(consumer, SimpleRecipe.build(Items.STICK, Items.OAK_PLANKS, Pattern.BLOCK, 1));

		// misc
		registerVanillaRecipe(consumer, SimpleRecipe.build(Items.BONE, Items.BONE_MEAL, Pattern.INGOT, 4));
		registerVanillaRecipe(consumer, SimpleRecipe.build(Items.BONE, Items.BONE_BLOCK, Pattern.BLOCK, 1));
		registerVanillaRecipe(consumer, SimpleRecipe.build(Items.GUNPOWDER, Items.TNT, Pattern.BLOCK, 1));
		registerVanillaRecipe(consumer, SimpleRecipe.build(Items.MELON_SLICE, Items.MELON, Pattern.BLOCK, 1));

		registerRecipe(consumer, SimpleRecipe.build(Items.BONE_BLOCK, Items.BONE, Pattern.INGOT, 4), mcLoc("bone_meal_from_bone_block"));

		registerRecipe(consumer, SimpleRecipe.build(Items.BONE_MEAL, Items.BONE, Pattern.BLOCK, 1));
		registerRecipe(consumer, SimpleRecipe.build(Items.LEATHER, Items.RABBIT_HIDE, Pattern.INGOT, 4));
		registerRecipe(consumer, SimpleRecipe.build(Items.TNT, Items.GUNPOWDER, Pattern.INGOT, 4));
		registerRecipe(consumer, SimpleRecipe.build(Items.HONEYCOMB_BLOCK, Items.HONEYCOMB, Pattern.INGOT, 4));
		registerRecipe(consumer, SimpleRecipe.build(Items.QUARTZ_BLOCK, Items.QUARTZ, Pattern.INGOT, 4));
		registerRecipe(consumer, SimpleRecipe.build(Blocks.BRICKS, Items.BRICK, Pattern.INGOT, 4));
		registerRecipe(consumer, SimpleRecipe.build(Items.NETHER_BRICKS, Items.NETHER_BRICK, Pattern.INGOT, 4));
		registerRecipe(consumer, SimpleRecipe.build(ItemTags.WOOL, Items.STRING, Pattern.INGOT, 4, "wools"));
		registerRecipe(consumer, SimpleRecipe.build(Items.MAGMA_BLOCK, Items.MAGMA_CREAM, Pattern.INGOT, 4));
		registerRecipe(consumer, SimpleRecipe.build(Items.MELON, Items.MELON_SLICE, Pattern.INGOT, 4));

		registerRecipe(consumer, BiRecipe.build(Items.BLACK_DYE, Blocks.BLACK_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.BLUE_DYE, Blocks.BLUE_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.BROWN_DYE, Blocks.BROWN_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.CYAN_DYE, Blocks.CYAN_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.GRAY_DYE, Blocks.GRAY_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.GREEN_DYE, Blocks.GREEN_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.LIGHT_BLUE_DYE, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.LIGHT_GRAY_DYE, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.LIME_DYE, Blocks.LIME_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.MAGENTA_DYE, Blocks.MAGENTA_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.ORANGE_DYE, Blocks.ORANGE_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.PINK_DYE, Blocks.PINK_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.PURPLE_DYE, Blocks.PURPLE_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.RED_DYE, Blocks.RED_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.WHITE_DYE, Blocks.WHITE_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));
		registerRecipe(consumer, BiRecipe.build(Items.YELLOW_DYE, Blocks.YELLOW_CONCRETE_POWDER, Pattern.INGOT, Pattern.BLOCK));

		ShapedRecipeBuilder.shapedRecipe(Items.CRAFTING_TABLE, 1).patternLine("XX").patternLine("SS").key('X', ItemTags.PLANKS).key('S', Items.STICK).setGroup("crafting_table").addCriterion("has_" + "planks", hasItem(ItemTags.PLANKS)).addCriterion("has_" + "sticks", hasItem(Items.STICK)).build(consumer, mcLoc("crafting_table"));
		ShapedRecipeBuilder.shapedRecipe(ModItems.HAMMER.get(), 1).patternLine("XSX").patternLine(" S ").patternLine(" S ").key('X', Items.IRON_INGOT).key('S', Items.STICK).setGroup("hammer").addCriterion("has_" + "iron", hasItem(Items.IRON_INGOT)).addCriterion("has_" + "sticks", hasItem(Items.STICK)).build(consumer, modLoc("hammer"));
	}

	private ResourceLocation mcLoc(String path) {
		return new ResourceLocation("minecraft", path);
	}

	private ResourceLocation modLoc(String path) {
		return new ResourceLocation(ServerMod.MODID, path);
	}

	private void registerBlockSetRecipes(Consumer<IFinishedRecipe> consumer, BlockSet blockSet) {
		if (blockSet.ingot != null) { // ingot
			registerRecipe(consumer, BiRecipe.build(blockSet.ingot, blockSet.variants.get(0).get(), Pattern.INGOT, Pattern.STAIR));
			registerRecipe(consumer, BiRecipe.build(blockSet.ingot, blockSet.variants.get(1).get(), Pattern.INGOT, Pattern.SLAB));
			registerRecipe(consumer, BiRecipe.build(blockSet.ingot, blockSet.variants.get(2).get(), Pattern.INGOT, Pattern.WALL));
		}

		// block
		registerRecipe(consumer, BiRecipe.build(blockSet.baseBlock, blockSet.variants.get(0).get(), Pattern.BLOCK, Pattern.STAIR));
		registerRecipe(consumer, BiRecipe.build(blockSet.baseBlock, blockSet.variants.get(1).get(), Pattern.BLOCK, Pattern.SLAB));
		registerRecipe(consumer, BiRecipe.build(blockSet.baseBlock, blockSet.variants.get(2).get(), Pattern.BLOCK, Pattern.WALL));

		// stairs
		registerRecipe(consumer, BiRecipe.build(blockSet.variants.get(0).get(), blockSet.variants.get(1).get(), Pattern.STAIR, Pattern.SLAB));
		registerRecipe(consumer, BiRecipe.build(blockSet.variants.get(0).get(), blockSet.variants.get(2).get(), Pattern.STAIR, Pattern.WALL));

		// slab
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

	private void registerVanillaRecipe(Consumer<IFinishedRecipe> consumer, BiRecipe recipe) {
		registerVanillaRecipe(consumer, recipe.left);
		registerVanillaRecipe(consumer, recipe.right);
	}

	private void registerVanillaRecipe(Consumer<IFinishedRecipe> consumer, SimpleRecipe recipe) {
		registerRecipe(consumer, recipe.ingredient, recipe.result, recipe.type, recipe.count, recipe.from, recipe.to, recipe.trigger, mcLoc(recipe.to));
	}

	private void registerRecipe(Consumer<IFinishedRecipe> consumer, SimpleRecipe recipe, ResourceLocation id) {
		registerRecipe(consumer, recipe.ingredient, recipe.result, recipe.type, recipe.count, recipe.from, recipe.to, recipe.trigger, id);
	}

	private void registerRecipe(Consumer<IFinishedRecipe> consumer, Ingredient ingredient, IItemProvider result, Pattern type, int count, String from, String to, InventoryChangeTrigger.Instance trigger) {
		ResourceLocation id = modLoc(from + "_to_" + to);
		registerRecipe(consumer, ingredient, result, type, count, from, to, trigger, id);
	}

	private void registerRecipe(Consumer<IFinishedRecipe> consumer, Ingredient ingredient, IItemProvider result, Pattern type, int count, String from, String to, InventoryChangeTrigger.Instance trigger, ResourceLocation id) {
		if (type.equals(Pattern.INGOT)) {
			ShapelessRecipeBuilder.shapelessRecipe(result, count).addIngredient(mix(ingredient)).setGroup(to).addCriterion("has_" + from, trigger).build(consumer, id);
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
			shapedRecipe.key('X', mix(ingredient)).setGroup(to).addCriterion("has_" + from, trigger).build(consumer, id);
		}
	}

	private enum Pattern {
		BLOCK(4), STAIR(3), SLAB(2), WALL(2), INGOT(1), NOTE(4);

		public int value;

		private Pattern(int value) {
			this.value = value;
		}
	}
}
