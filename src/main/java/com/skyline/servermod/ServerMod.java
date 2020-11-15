package com.skyline.servermod;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.blocks.ModBlocks.BlockSet;
import com.skyline.servermod.common.commands.FactionCommand;
import com.skyline.servermod.common.enchantments.ModEnchants;
import com.skyline.servermod.common.items.ModItems;
import com.skyline.servermod.common.looters.ModLooters;

import net.minecraft.block.DoorBlock;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(ServerMod.MODID)
public class ServerMod {
	public static final String MODID = "servermod";

	public ServerMod() {
		ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ModEnchants.ENCHANTS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ModEnchants.VANILLA_ENCHANTS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ModLooters.LOOTERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static final SoundEvent eggDavid = new SoundEvent(new ResourceLocation(MODID, "egg_david"));
	public static boolean isDoor = false;

	@Mod.EventBusSubscriber(bus = Bus.FORGE)
	public static class ForgeRegistryEvents {
		@SubscribeEvent
		public static void registerCommands(@Nonnull final RegisterCommandsEvent event) {
			FactionCommand.register(event.getDispatcher());
		}

		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public static void onPlayerTick(@Nonnull final PlayerTickEvent event) {
			if (event.player instanceof ClientPlayerEntity) {
				if (event.player.getGameProfile().getName().equalsIgnoreCase("WaffleFries")) {
					boolean tmpDoor = event.player.getEntityWorld().getBlockState(new BlockPos(event.player.getPositionVec())).getBlock() instanceof DoorBlock;
					if (isDoor && !tmpDoor) {
						event.player.playSound(eggDavid, 1f, 1f);
					}
					isDoor = tmpDoor;
				}
			}
		}

		@SubscribeEvent
		public static void onVillagerTrades(@Nonnull final VillagerTradesEvent event) {
			if (VillagerProfession.LIBRARIAN.equals(event.getType())) {
				Map<Integer, List<ITrade>> trades = event.getTrades();
				Random r = new Random();
				trades.forEach((key, value) -> {
					trades.put(key, value.stream().map(trade -> {
						MerchantOffer offer = trade.getOffer(null, r);
						if (offer.getSellingStack().getItem() instanceof EnchantedBookItem) {
							return new ModBookTrade(offer.getGivenExp(), false);
						}
						return trade;
					}).collect(Collectors.toList()));
					if (key == 5) {
						value.add(new ModBookTrade(20, true));
					}
				});
			}
		}
	}

	static class ModBookTrade implements VillagerTrades.ITrade {
		private final int xpValue;
		private final boolean max;

		public ModBookTrade(int xpValueIn, boolean max) {
			this.xpValue = xpValueIn;
			this.max = max;
		}

		public MerchantOffer getOffer(Entity trader, Random rand) {
			List<Enchantment> list = ForgeRegistries.ENCHANTMENTS.getValues().stream().filter(Enchantment::func_230309_h_).collect(Collectors.toList());
			Enchantment enchantment = list.get(rand.nextInt(list.size()));
			int i = max ? enchantment.getMaxLevel() : MathHelper.nextInt(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
			ItemStack itemstack = EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(enchantment, i));
			int j = enchantment.getMinEnchantability(i) + rand.nextInt(enchantment.getMaxEnchantability(i));
			if (enchantment.isTreasureEnchantment()) {
				j *= 2;
			}

			Item currency = Items.EMERALD;
			if (j > 64) {
				j = (j + 3) >> 2;
				currency = Items.EMERALD_BLOCK;
				if (j > 64) {
					j = (j + 3) >> 2;
					currency = ModItems.EMERALD_NOTE.get();
				}
			}

			return new MerchantOffer(new ItemStack(currency, j), new ItemStack(Items.BOOK), itemstack, 12, this.xpValue, 0.2F);
		}
	}

	@Mod.EventBusSubscriber(bus = Bus.MOD)
	public static class DataGenerationEvents {

		@SubscribeEvent
		public static void onRenderTypeSetup(FMLClientSetupEvent event) {
			setupBlockSetRenderType(ModBlocks.GLASS_SET, RenderType.getCutoutMipped());
			setupBlockSetRenderType(ModBlocks.BLACK_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.BLUE_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.BROWN_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.CYAN_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.GRAY_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.GREEN_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.LIGHT_BLUE_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.LIGHT_GRAY_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.LIME_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.MAGENTA_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.ORANGE_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.PINK_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.PURPLE_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.RED_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.WHITE_STAINED_GLASS_SET, RenderType.getTranslucent());
			setupBlockSetRenderType(ModBlocks.YELLOW_STAINED_GLASS_SET, RenderType.getTranslucent());

			setupBlockSetRenderType(ModBlocks.ICE_SET, RenderType.getTranslucent());
		}

		private static void setupBlockSetRenderType(BlockSet bs, RenderType type) {
			bs.variants.forEach((variant) -> {
				RenderTypeLookup.setRenderLayer(variant.get(), type);
			});
		}

		@SubscribeEvent
		public static void gatherData(GatherDataEvent event) {
			DataGenerator gen = event.getGenerator();

			if (event.includeClient()) {
				gen.addProvider(new Lang(gen));

				BlockStateProvider blockModels = new BlockStates(gen, event.getExistingFileHelper());
				ItemModelProvider itemModels = new ItemModels(gen, event.getExistingFileHelper());
				gen.addProvider(itemModels);
				gen.addProvider(blockModels);
			}
			if (event.includeServer()) {
				gen.addProvider(new Recipes(gen));
				gen.addProvider(new Tags(gen));
				gen.addProvider(new LootTables(gen));
			}
		}
	}
}
