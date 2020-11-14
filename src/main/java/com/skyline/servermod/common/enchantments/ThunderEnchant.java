package com.skyline.servermod.common.enchantments;

import com.skyline.servermod.common.items.HammerItem;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;

public class ThunderEnchant extends Enchantment {
	public ThunderEnchant() {
		super(Enchantment.Rarity.VERY_RARE, EnchantmentType.create("HAMMER", i -> i instanceof HammerItem), ModEnchantHelper.SLOT_ALL);
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
