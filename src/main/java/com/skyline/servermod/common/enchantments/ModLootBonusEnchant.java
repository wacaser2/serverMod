package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.LootBonusEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ModLootBonusEnchant extends LootBonusEnchantment {
	public ModLootBonusEnchant(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType... slots) {
		super(rarityIn, typeIn, slots);
	}

	@Override
	public int getMaxLevel() {
		return 50;
	}
}
