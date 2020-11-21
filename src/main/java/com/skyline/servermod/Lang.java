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
		add(ModBlocks.EYE_BLOCK.get(), "Eye Block");
		add(ModBlocks.RUBY_BLOCK.get(), "Ruby Block");
		add(ModBlocks.TEMPERED_GLASS.get(), "Tempered Glass");
		add(ModBlocks.CRYSTAL_GLASS.get(), "Crystal Glass");

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

		add("enchantment.level.11", "XI");
		add("enchantment.level.12", "XII");
		add("enchantment.level.13", "XIII");
		add("enchantment.level.14", "XIV");
		add("enchantment.level.15", "XV");
		add("enchantment.level.16", "XVI");
		add("enchantment.level.17", "XVII");
		add("enchantment.level.18", "XVIII");
		add("enchantment.level.19", "XIX");

		add("enchantment.level.20", "XX");
		add("enchantment.level.21", "XXI");
		add("enchantment.level.22", "XXII");
		add("enchantment.level.23", "XXIII");
		add("enchantment.level.24", "XXIV");
		add("enchantment.level.25", "XXV");
		add("enchantment.level.26", "XXVI");
		add("enchantment.level.27", "XXVII");
		add("enchantment.level.28", "XXVIII");
		add("enchantment.level.29", "XXIX");

		add("enchantment.level.30", "XXX");
		add("enchantment.level.31", "XXXI");
		add("enchantment.level.32", "XXXII");
		add("enchantment.level.33", "XXXIII");
		add("enchantment.level.34", "XXXIV");
		add("enchantment.level.35", "XXXV");
		add("enchantment.level.36", "XXXVI");
		add("enchantment.level.37", "XXXVII");
		add("enchantment.level.38", "XXXVIII");
		add("enchantment.level.39", "XXXIX");

		add("enchantment.level.40", "XL");
		add("enchantment.level.41", "XLI");
		add("enchantment.level.42", "XLII");
		add("enchantment.level.43", "XLIII");
		add("enchantment.level.44", "XLIV");
		add("enchantment.level.45", "XLV");
		add("enchantment.level.46", "XLVI");
		add("enchantment.level.47", "XLVII");
		add("enchantment.level.48", "XLVIII");
		add("enchantment.level.49", "XLIX");

		add("enchantment.level.50", "L");
	}

	private void addBlockSet(BlockSet blockSet) {
		String name = new TranslationTextComponent(blockSet.baseBlock.getTranslationKey()).getString().replace(" Block", "").replace("Block of ", "");

		add(blockSet.variants.get(0).get(), name + " Stairs");
		add(blockSet.variants.get(1).get(), name + " Slab");
		add(blockSet.variants.get(2).get(), name + " Wall");
	}
}
