package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.EfficiencyEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ModEfficiencyEnchant extends EfficiencyEnchantment {
	public ModEfficiencyEnchant(Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, slots);
	}

	@Override
	public int getMaxLevel() {
		return 50;
	}
}
