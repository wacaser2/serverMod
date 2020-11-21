package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;

public class TempestEnchant extends Enchantment {
	public TempestEnchant() {
		super(Enchantment.Rarity.RARE, ModEnchantHelper.HAMMER, ModEnchantHelper.SLOT_HAND);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 64 * enchantmentLevel;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 128;
	}

	@Override
	public int getMaxLevel() { return 10; }

	@Override
	public boolean isTreasureEnchantment() { return false; }

	public boolean func_230309_h_() {
		return false;
	}
}
