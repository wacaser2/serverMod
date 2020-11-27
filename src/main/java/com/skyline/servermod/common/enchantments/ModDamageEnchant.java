package com.skyline.servermod.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModDamageEnchant extends Enchantment {
   private static final int[] MIN_COST = new int[]{1, 5, 5, 1, 24};
   private static final int[] LEVEL_COST = new int[]{11, 8, 8, 8, 24};
   private static final int[] LEVEL_COST_SPAN = new int[]{20, 20, 20, 20, 64};
   public final Type damageType;

   public ModDamageEnchant(Enchantment.Rarity rarityIn, Type damageTypeIn, EquipmentSlotType... slots) {
		super(rarityIn, damageTypeIn == Type.POWER ? ModEnchantHelper.ALL_WEAPONS
				: damageTypeIn == Type.THUNDER ? ModEnchantHelper.HAMMER
						: damageTypeIn == Type.IMPALING ? EnchantmentType.TRIDENT : ModEnchantHelper.MELEE_WEAPONS,
				slots);
		this.damageType = damageTypeIn;
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return MIN_COST[this.damageType.val] + (enchantmentLevel - 1) * LEVEL_COST[this.damageType.val];
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + LEVEL_COST_SPAN[this.damageType.val];
   }

   public int getMaxLevel() {
      return 50;
   }

	public float calcDamageByCreature(int level, CreatureAttribute creatureType) {
		switch (this.damageType) {
		case POWER:
			return 1.0F + (float) Math.max(0, level - 1) * 0.5F;
		case SMITE:
			if (creatureType == CreatureAttribute.UNDEAD) {
				return (float) level * 2.5F;
			}
			break;
		case BANE:
			if (creatureType == CreatureAttribute.ARTHROPOD) {
				return (float) level * 2.5F;
			}
			break;
		case IMPALING:
			if(creatureType == CreatureAttribute.WATER) {
				return (float) level * 2.5F;
			}
			break;
		default:
			break;
		}
		return 0;
	}

   public boolean canApplyTogether(Enchantment ench) {
      return !(ench instanceof ModDamageEnchant);
   }

   public void onEntityDamaged(LivingEntity user, Entity target, int level) {
      if (target instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)target;
         if (this.damageType == Type.BANE && livingentity.getCreatureAttribute() == CreatureAttribute.ARTHROPOD) {
            int i = 20 + user.getRNG().nextInt(10 * level);
            livingentity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, i, 3));
         }
      }

   }
   
   public static enum Type {
	   POWER(0), SMITE(1), BANE(2), IMPALING(3), THUNDER(4);
	   
	   public int val;
	   
	   private Type(int val) {
		   this.val = val;
	   }
   }
}
