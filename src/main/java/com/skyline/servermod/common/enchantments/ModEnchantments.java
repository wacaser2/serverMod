package com.skyline.servermod.common.enchantments;

import java.util.ArrayList;
import java.util.List;

import com.skyline.servermod.ServerMod;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public final class ModEnchantments {
	public static List<Enchantment> MOD_ENCHANTMENT_LIST = new ArrayList<Enchantment>();

	public static Enchantment register(String modid, String name, Enchantment enchantment) {
		MOD_ENCHANTMENT_LIST.add(enchantment.setRegistryName(modid, name));
		return enchantment;
	}

	public static final Enchantment BLASTING = register(ServerMod.MODID, "blasting", new BlastingEnchant());

	public static final Enchantment SMELTING = register(ServerMod.MODID, "smelting", new SmeltingEnchant());

	public static final Enchantment TIMELESS = register(ServerMod.MODID, "timeless", new TimelessEnchant());

	public static final Enchantment SOULBOUND = register(ServerMod.MODID, "soulbound", new SoulboundEnchant());

	public static final Enchantment MOD_LOOTING = register("minecraft", "looting",
			new ModLootBonusEnchant(Rarity.RARE, ModEnchantHelper.ALL_WEAPONS, ModEnchantHelper.SLOT_HAND));

	public static final Enchantment MOD_FORTUNE = register("minecraft", "fortune",
			new ModLootBonusEnchant(Rarity.RARE, EnchantmentType.DIGGER, ModEnchantHelper.SLOT_HAND));

	public static final Enchantment MOD_PROTECTION_ALL = register("minecraft", "protection",
			new ModProtectionEnchant(Rarity.COMMON, ModProtectionEnchant.Type.ALL, ModEnchantHelper.SLOT_ARMOR));

	public static final Enchantment MOD_PROTECTION_FIRE = register("minecraft", "fire_protection",
			new ModProtectionEnchant(Rarity.UNCOMMON, ModProtectionEnchant.Type.FIRE, ModEnchantHelper.SLOT_ARMOR));

	public static final Enchantment MOD_PROTECTION_PROJ = register("minecraft", "projectile_protection",
			new ModProtectionEnchant(Rarity.UNCOMMON, ModProtectionEnchant.Type.PROJECTILE,
					ModEnchantHelper.SLOT_ARMOR));

	public static final Enchantment MOD_PROTECTION_BLAST = register("minecraft", "blast_protection",
			new ModProtectionEnchant(Rarity.RARE, ModProtectionEnchant.Type.EXPLOSION, ModEnchantHelper.SLOT_ARMOR));

	public static final Enchantment MOD_PROTECTION_FALL = register("minecraft", "feather_falling",
			new ModProtectionEnchant(Rarity.UNCOMMON, ModProtectionEnchant.Type.FALL, EquipmentSlotType.FEET));

	public static final Enchantment MOD_UNBREAKING = register("minecraft", "unbreaking",
			new ModUnbreakingEnchant(Rarity.UNCOMMON, ModEnchantHelper.SLOT_ALL));

	public static final Enchantment MOD_EFFICIENCY = register("minecraft", "efficiency",
			new ModEfficiencyEnchant(Rarity.COMMON, ModEnchantHelper.SLOT_HAND));
}
