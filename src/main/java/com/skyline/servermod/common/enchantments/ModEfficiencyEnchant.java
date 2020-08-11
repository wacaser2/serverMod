package main.java.com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.EfficiencyEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ModEfficiencyEnchant extends EfficiencyEnchantment {
	private int maxLvl, baseEnchant, lvlEnchant;

	public ModEfficiencyEnchant(int maxLvl, int baseEnchant, int lvlEnchant, Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, slots);
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
