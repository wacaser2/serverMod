package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;

public class ThunderEnchant extends Enchantment {
	public ThunderEnchant() {
		super(Enchantment.Rarity.UNCOMMON, ModEnchantHelper.HAMMER, ModEnchantHelper.SLOT_HAND);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 24 * enchantmentLevel;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 64;
	}

	@Override
	public int getMaxLevel() { return 20; }

	@Override
	public boolean isTreasureEnchantment() { return false; }
}
