package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;

public class SoulboundEnchant extends Enchantment {
	public SoulboundEnchant() {
		super(Enchantment.Rarity.VERY_RARE, ModEnchantHelper.ALL, ModEnchantHelper.SLOT_ALL);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 64;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return 192;
	}

	@Override
	public int getMaxLevel() { return 1; }

	@Override
	public boolean isTreasureEnchantment() { return false; }

	public boolean func_230309_h_() {
		return false;
	}
}
