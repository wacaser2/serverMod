package main.java.com.skyline.servermod.common.enchantments;

import main.java.com.skyline.servermod.ServerMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ResourceLocation;

public final class ModEnchantments {
	public static final EnchantmentType ALL_WEAPONS = EnchantmentType.create("all_weapons",
			i -> i instanceof AxeItem || EnchantmentType.BOW.canEnchantItem(i)
					|| EnchantmentType.CROSSBOW.canEnchantItem(i) || EnchantmentType.TRIDENT.canEnchantItem(i)
					|| EnchantmentType.WEAPON.canEnchantItem(i));

	public static final Enchantment BLASTING = new ModEnchant(5, 10, 10, Rarity.RARE, EnchantmentType.DIGGER,
			new EquipmentSlotType[] { EquipmentSlotType.MAINHAND })
					.setRegistryName(new ResourceLocation(ServerMod.MODID, "blasting"));

	public static final Enchantment SMELTING = new ModEnchant(1, 15, 0, Rarity.UNCOMMON, EnchantmentType.DIGGER,
			new EquipmentSlotType[] { EquipmentSlotType.MAINHAND })
					.setRegistryName(new ResourceLocation(ServerMod.MODID, "smelting"));

	public static final Enchantment MOD_LOOTING = new ModEnchant(5, 5, 5, Rarity.RARE, ALL_WEAPONS,
			new EquipmentSlotType[] { EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND })
					.setRegistryName(new ResourceLocation("minecraft", "looting"));

	public static final Enchantment MOD_FORTUNE = new ModEnchant(5, 5, 5, Rarity.RARE, EnchantmentType.DIGGER,
			new EquipmentSlotType[] { EquipmentSlotType.MAINHAND })
					.setRegistryName(new ResourceLocation("minecraft", "fortune"));
}
