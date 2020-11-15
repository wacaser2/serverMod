package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;

public class TempestEnchant extends Enchantment {
	public TempestEnchant() {
		super(Enchantment.Rarity.RARE, ModEnchantHelper.HAMMER, ModEnchantHelper.SLOT_HAND);
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
	public int getMaxLevel() { return 3; }

	@Override
	public boolean isTreasureEnchantment() { return false; }

	public boolean func_230309_h_() {
		return false;
	}
}
