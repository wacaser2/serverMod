package com.skyline.servermod.common.items;

import java.util.ArrayList;
import java.util.List;

import com.skyline.servermod.ServerMod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public final class ModItems {
	public static List<Item> MOD_ITEM_LIST = new ArrayList<Item>();
	
	public static Item register(String modid, String name, Item item) {
		MOD_ITEM_LIST.add(item.setRegistryName(modid, name));
		return item;
	}

	public static final Item EMERALD_NOTE = register(ServerMod.MODID, "emerald_note",
			new Item(new Item.Properties().group(ItemGroup.MISC)));

	public static final Item REAM = register(ServerMod.MODID, "ream",
			new Item(new Item.Properties().group(ItemGroup.MISC)));
}
