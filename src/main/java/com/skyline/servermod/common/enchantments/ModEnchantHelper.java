package com.skyline.servermod.common.enchantments;

import com.skyline.servermod.common.items.HammerItem;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ShieldItem;

public final class ModEnchantHelper {
	public static final EnchantmentType AXE = EnchantmentType.create("AXE", i -> i instanceof AxeItem);
	public static final EnchantmentType HOE = EnchantmentType.create("HOE", i -> i instanceof HoeItem);
	public static final EnchantmentType HAMMER = EnchantmentType.create("HAMMER", i -> i instanceof HammerItem);
	public static final EnchantmentType MELEE_WEAPONS = EnchantmentType.create("melee_weapons", i -> AXE.canEnchantItem(i) || EnchantmentType.TRIDENT.canEnchantItem(i) || EnchantmentType.WEAPON.canEnchantItem(i) || HAMMER.canEnchantItem(i));
	public static final EnchantmentType RANGED_WEAPONS = EnchantmentType.create("ranged_weapons", i -> EnchantmentType.BOW.canEnchantItem(i) || EnchantmentType.CROSSBOW.canEnchantItem(i) || EnchantmentType.TRIDENT.canEnchantItem(i));
	public static final EnchantmentType ALL_WEAPONS = EnchantmentType.create("all_weapons", i -> MELEE_WEAPONS.canEnchantItem(i) || RANGED_WEAPONS.canEnchantItem(i));
	public static final EnchantmentType DEFENSE = EnchantmentType.create("all_defense", i -> EnchantmentType.ARMOR.canEnchantItem(i) || i instanceof ShieldItem);

	public static final EquipmentSlotType[] SLOT_ALL = EquipmentSlotType.values();
	public static final EquipmentSlotType[] SLOT_MAIN_HAND = new EquipmentSlotType[] {
		EquipmentSlotType.MAINHAND
	};
	public static final EquipmentSlotType[] SLOT_HAND = new EquipmentSlotType[] {
		EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND
	};
	public static final EquipmentSlotType[] SLOT_ARMOR = new EquipmentSlotType[] {
		EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET
	};
	public static final EquipmentSlotType[] SLOT_DEFENSE = new EquipmentSlotType[] {
		EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET, EquipmentSlotType.OFFHAND
	};
}
