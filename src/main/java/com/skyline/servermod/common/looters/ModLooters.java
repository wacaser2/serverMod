package com.skyline.servermod.common.looters;

import com.skyline.servermod.ServerMod;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModLooters {
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOTERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, ServerMod.MODID);

	public static final RegistryObject<SmeltingEnchantLooter.Serializer> SMELTING_MODIFIER = LOOTERS.register("smelting", SmeltingEnchantLooter.Serializer::new);
	public static final RegistryObject<ReaperEnchantLooter.Serializer> REAPER_MODIFIER = LOOTERS.register("reaper", ReaperEnchantLooter.Serializer::new);
}
