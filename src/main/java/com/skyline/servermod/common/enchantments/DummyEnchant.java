package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class DummyEnchant extends Enchantment {

	protected DummyEnchant() {
		super(Enchantment.Rarity.VERY_RARE, EnchantmentType.FISHING_ROD, ModEnchantHelper.SLOT_ALL);
	}

	public boolean canApply(ItemStack stack) {
		return false;
	}
}
