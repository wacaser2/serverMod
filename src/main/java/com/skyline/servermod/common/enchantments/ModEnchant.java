package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ModEnchant extends Enchantment {
	private int maxLvl, baseEnchant, lvlEnchant;
	private boolean treasure;

	public ModEnchant(int maxLvl, int baseEnchant, int lvlEnchant, boolean treasure, Rarity rarityIn, EnchantmentType typeIn,
			EquipmentSlotType... slots) {
		super(rarityIn, typeIn, slots);
		this.maxLvl = maxLvl;
		this.baseEnchant = baseEnchant;
		this.lvlEnchant = lvlEnchant;
		this.treasure = treasure;
	}

	@Override
	public int getMaxLevel() {
		return maxLvl;
	}

	@Override
	public int getMinEnchantability(int enchantLvl) {
		return baseEnchant + enchantLvl * lvlEnchant;
	}

	@Override
	public int getMaxEnchantability(int enchantLvl) {
		return getMinEnchantability(enchantLvl) + 10;
	}

	@Override
	public boolean isTreasureEnchantment() {
		return treasure;
	}
}
