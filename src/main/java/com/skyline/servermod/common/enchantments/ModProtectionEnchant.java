package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;

public class ModProtectionEnchant extends Enchantment {
	public final ModProtectionEnchant.Type protectionType;

	public ModProtectionEnchant(Enchantment.Rarity rarityIn, ModProtectionEnchant.Type protectionTypeIn, EquipmentSlotType... slots) {
		super(rarityIn, protectionTypeIn == ModProtectionEnchant.Type.FALL ? EnchantmentType.ARMOR_FEET : EnchantmentType.ARMOR, slots);
		this.protectionType = protectionTypeIn;
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return this.protectionType.getMinimalEnchantability() + (enchantmentLevel - 1) * this.protectionType.getEnchantIncreasePerLevel();
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + this.protectionType.getEnchantIncreasePerLevel();
	}

	@Override
	public int getMaxLevel() { return 20; }

	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		if (source.canHarmInCreative()) {
			return 0;
		} else if (this.protectionType == ModProtectionEnchant.Type.ALL) {
			return level;
		} else if (this.protectionType == ModProtectionEnchant.Type.FIRE && source.isFireDamage()) {
			return level * 2;
		} else if (this.protectionType == ModProtectionEnchant.Type.FALL && source == DamageSource.FALL) {
			return level * 3;
		} else if (this.protectionType == ModProtectionEnchant.Type.EXPLOSION && source.isExplosion()) {
			return level * 2;
		} else {
			return this.protectionType == ModProtectionEnchant.Type.PROJECTILE && source.isProjectile() ? level * 2 : 0;
		}
	}

	@Override
	public boolean canApplyTogether(Enchantment ench) {
		if (ench instanceof ModProtectionEnchant) {
			ModProtectionEnchant protectionenchantment = (ModProtectionEnchant) ench;
			if (this.protectionType == protectionenchantment.protectionType) {
				return false;
			} else {
				return this.protectionType == ModProtectionEnchant.Type.FALL || protectionenchantment.protectionType == ModProtectionEnchant.Type.FALL;
			}
		} else {
			return super.canApplyTogether(ench);
		}
	}

	public static enum Type {
		ALL("all", 1, 11), FIRE("fire", 10, 8), FALL("fall", 5, 6), EXPLOSION("explosion", 5, 8), PROJECTILE("projectile", 3, 6);

		@SuppressWarnings("unused")
		private final String typeName;
		private final int minEnchantability;
		private final int levelCost;

		private Type(String typeName, int minEnchant, int lvlCost) {
			this.typeName = typeName;
			this.minEnchantability = minEnchant;
			this.levelCost = lvlCost;
		}

		public int getMinimalEnchantability() { return this.minEnchantability; }

		public int getEnchantIncreasePerLevel() { return this.levelCost; }
	}
}
