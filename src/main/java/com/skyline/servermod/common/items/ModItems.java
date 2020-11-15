package com.skyline.servermod.common.items;

import java.util.function.Supplier;

import com.skyline.servermod.ServerMod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ServerMod.MODID);

	public static RegistryObject<Item> register(final String name, final Supplier<? extends Item> supplier) {
		return ITEMS.register(name, supplier);
	}

	public static final RegistryObject<Item> EMERALD_NOTE = register("emerald_note", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> REAM = register("ream", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> CARROT_BUSHEL = register("carrot_bushel", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> POTATO_BUSHEL = register("potato_bushel", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> BEETROOT_BUSHEL = register("beetroot_bushel", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> BEETROOT_SEEDS_FEEDBAG = register("beetroot_seeds_feedbag", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> MELON_SEEDS_FEEDBAG = register("melon_seeds_feedbag", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> PUMPKIN_SEEDS_FEEDBAG = register("pumpkin_seeds_feedbag", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> WHEAT_SEEDS_FEEDBAG = register("wheat_seeds_feedbag", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> QUIVER = register("quiver", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> CARTON = register("carton", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> PILLOW = register("pillow", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> RUBY = register("ruby", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<Item> HAMMER = register("hammer", () -> new HammerItem(new Item.Properties().maxDamage(400).group(ItemGroup.COMBAT)));
}
