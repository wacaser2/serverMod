package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ModUnbreakingEnchant extends UnbreakingEnchantment {
	public ModUnbreakingEnchant(Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, slots);
	}

	@Override
	public int getMaxLevel() {
		return 50;
	}
}
