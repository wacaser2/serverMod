package com.skyline.servermod;

import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.blocks.ModBlocks.BlockSet;
import com.skyline.servermod.common.enchantments.ModEnchantments;
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
		
		for(BlockSet blockSet : ModBlocks.VARIANT_SETS) {
			addBlockSet(blockSet);
		}

		add(ModItems.EMERALD_NOTE.get(), "Emerald Note");
		add(ModItems.REAM.get(), "Ream of Paper");
		add(ModItems.HAMMER.get(), "Hammer");

		add(ModEnchantments.BLASTING.get(), "Blasting");
		add(ModEnchantments.SMELTING.get(), "Smelting");
		add(ModEnchantments.SOULBOUND.get(), "Soulbound");
		add(ModEnchantments.TIMELESS.get(), "Timeless");
		
		add("commands.faction.register", "Successfully registered %1$s at %2$s");
		add("commands.faction.register.move", "Successfully moved %1$s to %2$s");
		add("commands.faction.register.rename", "Successfully renamed %1$s to %$2s");
		add("commands.faction.list", "Faction, %1$s Online, %2$s | Active, %3$s | Total, %4$s");
		add("commands.faction.query", "%1$s is a part of %2$s");
		add("commands.faction.queryteam", "Your teammate %1$s is at %2$s");
		add("commands.faction.queryteamlist", "Online, [ %1$s ], Offline, [ %2$s ], Inactive, [ %3$s ]");
		add("commands.faction.switch", "Successfully switched factions to %s");
		add("commands.faction.hide", "Successfully removed %s");
		add("commands.faction.unhide", "Successfully revived %s");
		add("commands.faction.clear", "Permanently deleted %s");
		add("argument.faction.invalid", "Faction name, %s is invalid");
	}
	
	private void addBlockSet(BlockSet blockSet) {
		String name = new TranslationTextComponent(blockSet.baseBlock.getTranslationKey()).getString().replace(" Block", "").replace("Block of ", "");
		
		add(blockSet.variants.get(0).get(), name + " Stairs");
		add(blockSet.variants.get(1).get(), name + " Slab");
		add(blockSet.variants.get(2).get(), name + " Wall");
	}
}