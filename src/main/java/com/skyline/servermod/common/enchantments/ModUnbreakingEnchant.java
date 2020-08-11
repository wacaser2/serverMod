package main.java.com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ModUnbreakingEnchant extends UnbreakingEnchantment {
	private int maxLvl, baseEnchant, lvlEnchant;

	public ModUnbreakingEnchant(int maxLvl, int baseEnchant, int lvlEnchant, Rarity rarityIn,
			EquipmentSlotType... slots) {
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
