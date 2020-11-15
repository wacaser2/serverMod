package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;

public class ThunderEnchant extends Enchantment {
	public ThunderEnchant() {
		super(Enchantment.Rarity.VERY_RARE, ModEnchantHelper.HAMMER, ModEnchantHelper.SLOT_HAND);
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
	public int getMaxLevel() { return 5; }

	@Override
	public boolean isTreasureEnchantment() { return true; }
}
