package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;

public class TimelessEnchant extends Enchantment {
	public TimelessEnchant() {
		super(Enchantment.Rarity.RARE, ModEnchantHelper.ALL, ModEnchantHelper.SLOT_ALL);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 16;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return 80;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public boolean isTreasureEnchantment() {
		return true;
	}
}
