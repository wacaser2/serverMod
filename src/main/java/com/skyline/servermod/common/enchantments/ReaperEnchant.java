package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;

public class ReaperEnchant extends Enchantment {
	public ReaperEnchant() {
		super(Enchantment.Rarity.RARE, ModEnchantHelper.HOE, ModEnchantHelper.SLOT_HAND);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 16 * enchantmentLevel;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 64;
	}

	@Override
	public int getMaxLevel() { return 2; }

	@Override
	public boolean isTreasureEnchantment() { return false; }
}
