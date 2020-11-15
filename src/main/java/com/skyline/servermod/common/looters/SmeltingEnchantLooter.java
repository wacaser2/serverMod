package com.skyline.servermod.common.looters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

public class SmeltingEnchantLooter extends LootModifier {
	public Random rando;

	public SmeltingEnchantLooter(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Nonnull
	@Override
	public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		for (ItemStack orig : generatedLoot) {
			ItemStack smelted = smelt(orig, context);
			if (orig.getItem() instanceof BlockItem && !(smelted.getItem() instanceof BlockItem)) {
				ItemStack tool = context.get(LootParameters.TOOL);
				int fortuneLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool);
				int fortuneMultiplier = Math.max(1, context.getRandom().nextInt(fortuneLvl + 2));
				ret.add(ItemHandlerHelper.copyStackWithSize(smelted, fortuneMultiplier * smelted.getCount()));
			} else {
				ret.add(smelted);
			}
		}
		return ret;
	}

	private static ItemStack smelt(ItemStack stack, LootContext context) {
		return context.getWorld().getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(stack), context.getWorld()).map(FurnaceRecipe::getRecipeOutput).filter(itemStack -> !itemStack.isEmpty()).map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount())).orElse(stack);
	}

	public static class Serializer extends GlobalLootModifierSerializer<SmeltingEnchantLooter> {
		@Override
		public SmeltingEnchantLooter read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
			return new SmeltingEnchantLooter(conditionsIn);
		}
	}
}
