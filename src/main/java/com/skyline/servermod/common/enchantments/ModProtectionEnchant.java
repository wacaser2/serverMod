package main.java.com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ModProtectionEnchant extends ProtectionEnchantment {
	private int maxLvl, baseEnchant, lvlEnchant;

	public ModProtectionEnchant(int maxLvl, int baseEnchant, int lvlEnchant, 
			Rarity rarityIn, 
			ProtectionEnchantment.Type typeIn,
			EquipmentSlotType... slots) {
		super(rarityIn, typeIn, slots);
		this.maxLvl = maxLvl;
		this.baseEnchant = baseEnchant;
		this.lvlEnchant = lvlEnchant;
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
}
