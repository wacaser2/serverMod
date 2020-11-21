package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;

public class SmeltingEnchant extends Enchantment {
	public SmeltingEnchant() {
		super(Enchantment.Rarity.RARE, EnchantmentType.DIGGER, ModEnchantHelper.SLOT_HAND);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 24;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return 128;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}
}
