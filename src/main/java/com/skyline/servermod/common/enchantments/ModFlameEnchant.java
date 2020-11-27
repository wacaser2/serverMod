package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ModFlameEnchant extends Enchantment {
	public ModFlameEnchant(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType... slots) {
		super(rarityIn, typeIn, slots);
	}

	public void onEntityDamaged(LivingEntity user, Entity target, int level) {
		target.setFire(level * 4);
	}
	
	public int getMaxLevel() {
		return 50;
	}
}
