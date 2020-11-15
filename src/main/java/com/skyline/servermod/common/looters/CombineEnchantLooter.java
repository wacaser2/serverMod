package com.skyline.servermod.common.looters;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

public class CombineEnchantLooter extends LootModifier {
	public Random rando;

	public CombineEnchantLooter(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Nonnull
	@Override
	public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		ServerWorld world = context.getWorld();
		BlockPos pos = context.get(LootParameters.POSITION);
		return generatedLoot.stream().map(drop -> {
			if (drop.getItem() instanceof BlockItem && ((BlockItem) drop.getItem()).getBlock() instanceof IPlantable) {
				IPlantable seed = (IPlantable) ((BlockItem) drop.getItem()).getBlock();
				if (world.setBlockState(pos, seed.getPlant(world, pos), 3)) {
					return ItemHandlerHelper.copyStackWithSize(drop, drop.getCount() - 1);
				} else {
					return drop;
				}
			} else {
				return drop;
			}
		}).collect(Collectors.toList());
	}

	public static class Serializer extends GlobalLootModifierSerializer<CombineEnchantLooter> {
		@Override
		public CombineEnchantLooter read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
			return new CombineEnchantLooter(conditionsIn);
		}
	}
}
