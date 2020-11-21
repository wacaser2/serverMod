package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;

public class TopplingEnchant extends Enchantment {
	public TopplingEnchant() {
		super(Enchantment.Rarity.RARE, ModEnchantHelper.AXE, ModEnchantHelper.SLOT_HAND);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 12 + 12 * enchantmentLevel;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 64;
	}

	@Override
	public int getMaxLevel() { return 50; }

	public boolean func_230309_h_() {
		return true;
	}
}
