package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;

public class CombineEnchant extends Enchantment {
	public CombineEnchant() {
		super(Enchantment.Rarity.VERY_RARE, ModEnchantHelper.HOE, ModEnchantHelper.SLOT_HAND);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 30;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	@Override
	public int getMaxLevel() { return 1; }

	@Override
	public boolean isTreasureEnchantment() { return false; }
}
