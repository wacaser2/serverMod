package com.skyline.servermod.common.enchantments;

import com.skyline.servermod.ServerMod;
import com.skyline.servermod.common.ModHooks;
import com.skyline.servermod.common.ModHooks.TopplingEvent;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class TopplingEnchant extends Enchantment {
	public TopplingEnchant() {
		super(Enchantment.Rarity.RARE, ModEnchantHelper.AXE, ModEnchantHelper.SLOT_HAND);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 12 + 12 * enchantmentLevel;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 64;
	}

	@Override
	public int getMaxLevel() { return 20; }

	public boolean func_230309_h_() {
		return true;
	}

	@EventBusSubscriber(modid = ServerMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class EventHandlers {
		@SubscribeEvent
		public static void onBreakBlock(final BreakEvent event) {
			if (event instanceof TopplingEvent || !(event.getPlayer() instanceof ServerPlayerEntity)) { return; }
			ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
			if (player != null) {
				World world = (World) event.getWorld();
				BlockState state = event.getState();
				Block block = state.getBlock();
				BlockPos pos = event.getPos();
				ItemStack tool = player.getHeldItemMainhand();
				int topLvl;
				if (tool.isEnchanted() && (topLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchants.TOPPLING.get(), tool)) > 0 && block.canHarvestBlock(state, world, pos, player)) {
					topLvl *= 2;
					for (int lvl = 0; lvl < topLvl; lvl++) {
						pos = pos.up();
						Block newBlock = world.getBlockState(pos).getBlock();
						if (newBlock == block) {
							ModHooks.tryHarvestBlock(world, player, pos);
						} else {
							break;
						}
					}
				}
			}
		}
	}
}
