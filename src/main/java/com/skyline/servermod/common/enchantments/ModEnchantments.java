package main.java.com.skyline.servermod.common.enchantments;

import main.java.com.skyline.servermod.ServerMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ResourceLocation;

public final class ModEnchantments {
	public static final EnchantmentType MELEE_WEAPONS = EnchantmentType.create("melee_weapons", i -> 
			i instanceof AxeItem  
			|| EnchantmentType.TRIDENT.canEnchantItem(i)
			|| EnchantmentType.WEAPON.canEnchantItem(i));
	
	public static final EnchantmentType RANGED_WEAPONS = EnchantmentType.create("ranged_weapons", i -> 
			EnchantmentType.BOW.canEnchantItem(i)
			|| EnchantmentType.CROSSBOW.canEnchantItem(i) 
			|| EnchantmentType.TRIDENT.canEnchantItem(i));
	
	public static final EnchantmentType ALL_WEAPONS = EnchantmentType.create("all_weapons", i ->
			MELEE_WEAPONS.canEnchantItem(i)
			|| RANGED_WEAPONS.canEnchantItem(i));

	public static final Enchantment BLASTING = new ModEnchant(5, 10, 10, false, Rarity.RARE, EnchantmentType.DIGGER,
			new EquipmentSlotType[] { EquipmentSlotType.MAINHAND })
					.setRegistryName(new ResourceLocation(ServerMod.MODID, "blasting"));

	public static final Enchantment SMELTING = new ModEnchant(1, 15, 0, false, Rarity.UNCOMMON, EnchantmentType.DIGGER,
			new EquipmentSlotType[] { EquipmentSlotType.MAINHAND })
					.setRegistryName(new ResourceLocation(ServerMod.MODID, "smelting"));
	
	public static final Enchantment TIMELESS = new ModEnchant(1, 15, 0, true, Rarity.VERY_RARE, EnchantmentType.BREAKABLE,
			new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET, EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND })
			.setRegistryName(new ResourceLocation(ServerMod.MODID, "timeless"));
	
	public static final Enchantment SOULBOUND = new ModEnchant(1, 15, 0, true, Rarity.VERY_RARE, EnchantmentType.BREAKABLE,
			new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET, EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND })
			.setRegistryName(new ResourceLocation(ServerMod.MODID, "soulbound"));

	public static final Enchantment MOD_LOOTING = new ModLootBonusEnchant(10, 5, 5, Rarity.RARE, ALL_WEAPONS,
			new EquipmentSlotType[] { EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND })
					.setRegistryName(new ResourceLocation("minecraft", "looting"));

	public static final Enchantment MOD_FORTUNE = new ModLootBonusEnchant(10, 5, 5, Rarity.RARE, EnchantmentType.DIGGER,
			new EquipmentSlotType[] { EquipmentSlotType.MAINHAND })
					.setRegistryName(new ResourceLocation("minecraft", "fortune"));

	public static final Enchantment MOD_PROTECTION_ALL = new ModProtectionEnchant(10, 5, 5, Rarity.COMMON, ProtectionEnchantment.Type.ALL,
			new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET })
					.setRegistryName(new ResourceLocation("minecraft", "protection"));

	public static final Enchantment MOD_PROTECTION_FIRE = new ModProtectionEnchant(10, 5, 5, Rarity.UNCOMMON, ProtectionEnchantment.Type.FIRE,
			new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET })
					.setRegistryName(new ResourceLocation("minecraft", "fire_protection"));

	public static final Enchantment MOD_PROTECTION_PROJ = new ModProtectionEnchant(10, 5, 5, Rarity.UNCOMMON, ProtectionEnchantment.Type.PROJECTILE,
			new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET })
					.setRegistryName(new ResourceLocation("minecraft", "projectile_protection"));

	public static final Enchantment MOD_PROTECTION_BLAST = new ModProtectionEnchant(10, 5, 5, Rarity.RARE, ProtectionEnchantment.Type.EXPLOSION,
			new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET })
					.setRegistryName(new ResourceLocation("minecraft", "blast_protection"));

	public static final Enchantment MOD_UNBREAKING = new ModUnbreakingEnchant(10, 5, 5, Rarity.UNCOMMON, 
			new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET, EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND })
					.setRegistryName(new ResourceLocation("minecraft", "unbreaking"));

	public static final Enchantment MOD_EFFICIENCY = new ModEfficiencyEnchant(10, 5, 5, Rarity.COMMON,
			new EquipmentSlotType[] { EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND })
					.setRegistryName(new ResourceLocation("minecraft", "efficiency"));

	public static final Enchantment[] MOD_ENCHANT_LIST = { 
			BLASTING, 
			SMELTING, 
			TIMELESS,
			SOULBOUND,
			MOD_LOOTING, 
			MOD_FORTUNE, 
			MOD_PROTECTION_ALL,
			MOD_PROTECTION_FIRE,
			MOD_PROTECTION_PROJ,
			MOD_PROTECTION_BLAST,
			MOD_UNBREAKING,
			MOD_EFFICIENCY,
			};
}
