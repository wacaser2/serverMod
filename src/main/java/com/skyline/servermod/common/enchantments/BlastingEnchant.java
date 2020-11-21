package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;

public class BlastingEnchant extends Enchantment {
	public BlastingEnchant() {
		super(Enchantment.Rarity.VERY_RARE, EnchantmentType.DIGGER, ModEnchantHelper.SLOT_HAND);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 16 + 16 * enchantmentLevel;
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
