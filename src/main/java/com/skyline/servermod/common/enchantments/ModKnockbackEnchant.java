package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ModKnockbackEnchant extends Enchantment {
	public ModKnockbackEnchant(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType... slots) {
		super(rarityIn, typeIn, slots);
	}

	public int getMaxLevel() {
		return 50;
	}
}
