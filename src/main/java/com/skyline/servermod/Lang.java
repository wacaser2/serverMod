package com.skyline.servermod;

import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.blocks.ModBlocks.BlockSet;
import com.skyline.servermod.common.enchantments.ModEnchants;
import com.skyline.servermod.common.items.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.LanguageProvider;

public class Lang extends LanguageProvider {
	public Lang(DataGenerator gen) {
		super(gen, ServerMod.MODID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add(ModBlocks.SHALE.get(), "Shale Block");

		for (BlockSet blockSet : ModBlocks.VARIANT_SETS) {
			addBlockSet(blockSet);
		}

		add(ModItems.EMERALD_NOTE.get(), "Emerald Note");
		add(ModItems.REAM.get(), "Ream of Paper");
		add(ModItems.CARROT_BUSHEL.get(), "Bushel of Carrots");
		add(ModItems.POTATO_BUSHEL.get(), "Bushel of Potatoes");
		add(ModItems.BEETROOT_BUSHEL.get(), "Bushel of Beetroots");
		add(ModItems.BEETROOT_SEEDS_FEEDBAG.get(), "Beetroot Seeds Feedbag");
		add(ModItems.MELON_SEEDS_FEEDBAG.get(), "Melon Seeds Feedbag");
		add(ModItems.PUMPKIN_SEEDS_FEEDBAG.get(), "Pumpkin Seeds Feedbag");
		add(ModItems.WHEAT_SEEDS_FEEDBAG.get(), "Wheat Seeds Feedbag");
		add(ModItems.QUIVER.get(), "Quiver");
		add(ModItems.CARTON.get(), "Carton of Eggs");
		add(ModItems.PILLOW.get(), "Feather Pillow");
		add(ModItems.RUBY.get(), "Ruby");

		add(ModItems.HAMMER.get(), "Hammer");

		add(ModEnchants.BLASTING.get(), "Blasting");
		add(ModEnchants.SMELTING.get(), "Smelting");
		add(ModEnchants.SOULBOUND.get(), "Soulbound");
		add(ModEnchants.TIMELESS.get(), "Timeless");
		add(ModEnchants.THUNDER.get(), "Thunder");
		add(ModEnchants.TEMPEST.get(), "Tempest");
		add(ModEnchants.STORM.get(), "Storm");
		add(ModEnchants.COMBINE.get(), "Combine");
		add(ModEnchants.REAPER.get(), "Reaper");
		add(ModEnchants.TOPPLING.get(), "Toppling");

		add("commands.faction.register", "Successfully registered %1$s at %2$s");
		add("commands.faction.register.move", "Successfully moved %1$s to %2$s");
		add("commands.faction.register.rename", "Successfully renamed %1$s to %2$s");
		add("commands.faction.list", "Faction: %1$s Online: %2$s | Active: %3$s | Total: %4$s");
		add("commands.faction.query", "%1$s is a part of %2$s");
		add("commands.faction.queryteam", "Your teammate %1$s is at %2$s");
		add("commands.faction.queryteamlist", "Online: [ %1$s ], Offline: [ %2$s ], Inactive: [ %3$s ]");
		add("commands.faction.switch", "Successfully switched factions to %s");
		add("commands.faction.hide", "Successfully removed %s");
		add("commands.faction.unhide", "Successfully revived %s");
		add("commands.faction.clear", "Permanently deleted %s");
		add("argument.faction.invalid", "Faction name: %s is invalid");
	}

	private void addBlockSet(BlockSet blockSet) {
		String name = new TranslationTextComponent(blockSet.baseBlock.getTranslationKey()).getString().replace(" Block", "").replace("Block of ", "");

		add(blockSet.variants.get(0).get(), name + " Stairs");
		add(blockSet.variants.get(1).get(), name + " Slab");
		add(blockSet.variants.get(2).get(), name + " Wall");
	}
}
