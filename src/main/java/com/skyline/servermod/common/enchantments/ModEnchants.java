package com.skyline.servermod.common.enchantments;

import java.util.function.Supplier;

import com.skyline.servermod.ServerMod;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.ImpalingEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModEnchants {
	public static final DeferredRegister<Enchantment> VANILLA_ENCHANTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "minecraft");
	public static final DeferredRegister<Enchantment> ENCHANTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ServerMod.MODID);

	public static RegistryObject<Enchantment> register(final String name, final Supplier<? extends Enchantment> supplier) {
		return ENCHANTS.register(name, supplier);
	}

	public static RegistryObject<Enchantment> registerVanilla(final String name, final Supplier<? extends Enchantment> supplier) {
		return VANILLA_ENCHANTS.register(name, supplier);
	}

	public static final RegistryObject<Enchantment> BLASTING = register("blasting", BlastingEnchant::new);
	public static final RegistryObject<Enchantment> TOPPLING = register("toppling", TopplingEnchant::new);
	public static final RegistryObject<Enchantment> SMELTING = register("smelting", SmeltingEnchant::new);
	public static final RegistryObject<Enchantment> TIMELESS = register("timeless", TimelessEnchant::new);
	public static final RegistryObject<Enchantment> SOULBOUND = register("soulbound", SoulboundEnchant::new);

	public static final RegistryObject<Enchantment> THUNDER = register("thunder", () -> new ModDamageEnchant(Enchantment.Rarity.UNCOMMON, ModDamageEnchant.Type.THUNDER, ModEnchantHelper.SLOT_HAND));
	public static final RegistryObject<Enchantment> TEMPEST = register("tempest", TempestEnchant::new);
	public static final RegistryObject<Enchantment> STORM = register("storm", StormEnchant::new);

	public static final RegistryObject<Enchantment> REAPER = register("reaper", ReaperEnchant::new);

	public static final RegistryObject<Enchantment> MOD_LOOTING = registerVanilla("looting", () -> new ModLootBonusEnchant(Rarity.RARE, ModEnchantHelper.ALL_WEAPONS, ModEnchantHelper.SLOT_HAND));
	public static final RegistryObject<Enchantment> MOD_FORTUNE = registerVanilla("fortune", () -> new ModLootBonusEnchant(Rarity.RARE, EnchantmentType.DIGGER, ModEnchantHelper.SLOT_HAND));

	public static final RegistryObject<Enchantment> MOD_PROTECTION_ALL = registerVanilla("protection", () -> new ModProtectionEnchant(Rarity.COMMON, ModProtectionEnchant.Type.ALL, ModEnchantHelper.SLOT_ARMOR));
	public static final RegistryObject<Enchantment> MOD_PROTECTION_FIRE = registerVanilla("fire_protection", () -> new ModProtectionEnchant(Rarity.UNCOMMON, ModProtectionEnchant.Type.FIRE, ModEnchantHelper.SLOT_ARMOR));
	public static final RegistryObject<Enchantment> MOD_PROTECTION_PROJ = registerVanilla("projectile_protection", () -> new ModProtectionEnchant(Rarity.UNCOMMON, ModProtectionEnchant.Type.PROJECTILE, ModEnchantHelper.SLOT_ARMOR));
	public static final RegistryObject<Enchantment> MOD_PROTECTION_BLAST = registerVanilla("blast_protection", () -> new ModProtectionEnchant(Rarity.RARE, ModProtectionEnchant.Type.EXPLOSION, ModEnchantHelper.SLOT_ARMOR));
	public static final RegistryObject<Enchantment> MOD_PROTECTION_FALL = registerVanilla("feather_falling", () -> new ModProtectionEnchant(Rarity.UNCOMMON, ModProtectionEnchant.Type.FALL, EquipmentSlotType.FEET));

	public static final RegistryObject<Enchantment> MOD_UNBREAKING = registerVanilla("unbreaking", () -> new ModUnbreakingEnchant(Rarity.UNCOMMON, ModEnchantHelper.SLOT_ALL));
	public static final RegistryObject<Enchantment> MOD_EFFICIENCY = registerVanilla("efficiency", () -> new ModEfficiencyEnchant(Rarity.COMMON, ModEnchantHelper.SLOT_HAND));

	public static final RegistryObject<Enchantment> MOD_POWER = registerVanilla("power", () -> new ModDamageEnchant(Enchantment.Rarity.COMMON, ModDamageEnchant.Type.POWER, ModEnchantHelper.SLOT_HAND));
	public static final RegistryObject<Enchantment> MOD_SMITE = registerVanilla("smite", () -> new ModDamageEnchant(Enchantment.Rarity.UNCOMMON, ModDamageEnchant.Type.SMITE, EquipmentSlotType.MAINHAND));
	public static final RegistryObject<Enchantment> MOD_BANE_OF_ARTHROPODS = registerVanilla("bane_of_arthropods", () -> new ModDamageEnchant(Enchantment.Rarity.UNCOMMON, ModDamageEnchant.Type.BANE, EquipmentSlotType.MAINHAND));
	public static final RegistryObject<Enchantment> MOD_IMPALING = registerVanilla("impaling", () -> new ModDamageEnchant(Enchantment.Rarity.RARE, ModDamageEnchant.Type.IMPALING, ModEnchantHelper.SLOT_HAND));
	
	public static final RegistryObject<Enchantment> MOD_FLAME = registerVanilla("flame", () -> new ModFlameEnchant(Enchantment.Rarity.RARE, ModEnchantHelper.ALL_WEAPONS, ModEnchantHelper.SLOT_HAND));
	public static final RegistryObject<Enchantment> MOD_KNOCKBACK = registerVanilla("knockback", () -> new ModKnockbackEnchant(Enchantment.Rarity.UNCOMMON, ModEnchantHelper.MELEE_WEAPONS, ModEnchantHelper.SLOT_HAND));
	
	
	public static final RegistryObject<Enchantment> MOD_SHARPNESS = registerVanilla("sharpness", DummyEnchant::new);
	public static final RegistryObject<Enchantment> MOD_FIRE_ASPECT = registerVanilla("fire_aspect", DummyEnchant::new);
	   }
