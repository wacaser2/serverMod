package com.skyline.servermod;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;

import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.blocks.ModBlocks.BlockSet;
import com.skyline.servermod.common.commands.FactionArgument;
import com.skyline.servermod.common.commands.FactionCommand;
import com.skyline.servermod.common.enchantments.ModEnchants;
import com.skyline.servermod.common.items.ModItems;
import com.skyline.servermod.common.looters.ModLooters;

import net.minecraft.block.DoorBlock;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ServerMod.MODID)
public class ServerMod {
	public static final String MODID = "servermod";

	public static final Random rand = new Random();

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
		public static void onEnchantmentLevelSet(@Nonnull final EnchantmentLevelSetEvent event) {
			int i = event.getItem().getItemEnchantability();
			int ret = 0;
			if (i > 0) {
				int power = event.getPower();
				int j2 = 1 + (power >> 1);
				int j = rand.nextInt(j2) + j2 + rand.nextInt(power + 1);
				int enchantNum = event.getEnchantRow();
				if (enchantNum == 0) {
					ret = Math.max(j / 3, 1);
				} else if (enchantNum == 1) {
					ret = j * 2 / 3 + 1;
				} else {
					ret = Math.max(j, power * 2);
				}
			}
			event.setLevel(ret);
		}

		@SubscribeEvent
		public static void onAnvilUpdate(@Nonnull final AnvilUpdateEvent event) {
			ItemStack left = event.getLeft();
			ItemStack right = event.getRight();
			ItemStack output = left.copy();
			int cost = 0;

			boolean bookEnchant = right.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(right).isEmpty();
			if (output.isDamageable() && output.getItem().getIsRepairable(output, right)) {
				int maxDurabilityToRepair = Math.min(output.getDamage(), output.getMaxDamage() / 4);
				if (maxDurabilityToRepair <= 0) {
					event.setOutput(ItemStack.EMPTY);
					event.setCost(0);
					return;
				}

				int maxRepairs;
				for (maxRepairs = 0; maxDurabilityToRepair > 0 && maxRepairs < right.getCount(); ++maxRepairs) {
					output.setDamage(output.getDamage() - maxDurabilityToRepair);
					maxDurabilityToRepair = Math.min(output.getDamage(), output.getMaxDamage() / 4);
					cost++;
				}

				event.setMaterialCost(maxRepairs);
			} else {
				if (!bookEnchant && (output.getItem() != right.getItem() || !output.isDamageable())) {
					event.setOutput(ItemStack.EMPTY);
					event.setCost(0);
					return;
				}

				if (output.isDamageable() && !bookEnchant) {
					int durability = output.getMaxDamage();
					int durabilityLeft = 2 * durability - left.getDamage() - right.getDamage();
					int durabilityAdd = 12 * durability / 100;
					int durabilityResult = Math.min(durability, durabilityLeft + durabilityAdd);
					output.setDamage(durability - durabilityResult);
					cost += 2;
				}

				Map<Enchantment, Integer> mapLeft = EnchantmentHelper.getEnchantments(left);
				Map<Enchantment, Integer> mapRight = EnchantmentHelper.getEnchantments(right);
				Map<Enchantment, Integer> mapOutput = EnchantmentHelper.getEnchantments(output);

				for (Enchantment enchant : mapRight.keySet()) {
					if (enchant != null) {
						int currLvl = mapLeft.getOrDefault(enchant, 0);
						int addLvl = mapRight.getOrDefault(enchant, 0);

						int newLvl = Math.max(currLvl, addLvl);
						if (addLvl == currLvl) {
							newLvl = Math.min(enchant.getMaxLevel(), newLvl + 1);
						}

						if (enchant.canApply(output)) {
							boolean canEnchant = true;
							for (Enchantment existing : mapLeft.keySet()) {
								if (enchant != existing && !enchant.isCompatibleWith(existing)) {
									canEnchant = false;
									break;
								}
							}

							if (canEnchant) {
								mapOutput.put(enchant, newLvl);

								int enchantMult = 1;
								switch (enchant.getRarity()) {
								case COMMON:
									enchantMult = 1;
									break;
								case UNCOMMON:
									enchantMult = 2;
									break;
								case RARE:
									enchantMult = 4;
									break;
								case VERY_RARE:
									enchantMult = 8;
									break;
								}

								if (bookEnchant) {
									enchantMult = Math.max(1, enchantMult / 2);
								}

								int enchantCost = enchantMult * (newLvl - currLvl);
								cost += enchantCost;
							}
						}
					}
				}
				EnchantmentHelper.setEnchantments(mapOutput, output);
			}

			if (StringUtils.isBlank(event.getName())) {
				if (output.hasDisplayName()) {
					cost++;
					output.clearCustomName();
				}
			} else if (!event.getName().equals(output.getDisplayName().getString())) {
				cost++;
				output.setDisplayName(new StringTextComponent(event.getName()));
			}
			if (bookEnchant && !output.isBookEnchantable(right)) output = ItemStack.EMPTY;

			if (cost <= 0) {
				output = ItemStack.EMPTY;
			}

			event.setOutput(output);
			event.setCost(cost);
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

//		@SubscribeEvent
//		public static void onItemExpire(final ItemExpireEvent event) {
//			ItemEntity entityItem = event.getEntityItem();
//			Item item = entityItem.getItem().getItem();
//			World world = entityItem.world;
//			if (!world.isRemote) {
//				if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof SaplingBlock) {
//					SaplingBlock sapling = (SaplingBlock) ((BlockItem) item).getBlock();
//					BlockPos pos = new BlockPos(entityItem.getPositionVec());
//					BlockState state = world.getBlockState(pos);
//					if (sapling.isValidPosition(state, world, pos)) {
//						world.setBlockState(pos, sapling.getPlant(world, pos), 3);
//					}
//				} else if (Items.EGG == item) {
//					for (int k = 0; k < entityItem.getItem().getCount(); k++) {
//						if (rand.nextInt(8) == 0) {
//							int i = 1;
//							if (rand.nextInt(32) == 0) {
//								i = 4;
//							}
//							for (int j = 0; j < i; ++j) {
//								ChickenEntity chickenentity = EntityType.CHICKEN.create(world);
//								chickenentity.setGrowingAge(-24000);
//								chickenentity.setLocationAndAngles(entityItem.getPosX(), entityItem.getPosY(), entityItem.getPosZ(), entityItem.rotationYaw, 0.0F);
//								world.addEntity(chickenentity);
//							}
//						}
//					}
//				}
//			}
//		}

		@SubscribeEvent
		public static void onVillagerTrades(@Nonnull final VillagerTradesEvent event) {
			if (VillagerProfession.LIBRARIAN.equals(event.getType())) {
				Map<Integer, List<ITrade>> trades = event.getTrades();
				trades.get(5).add(new ModBookTrade(20, 5));
				trades.forEach((key, value) -> {
					trades.put(key, value.stream().map(trade -> {
						MerchantOffer offer = trade.getOffer(null, rand);
						if (offer.getSellingStack().getItem() instanceof EnchantedBookItem) {
							return new ModBookTrade(offer.getGivenExp(), key);
						}
						return trade;
					}).collect(Collectors.toList()));
				});
			} else if (VillagerProfession.ARMORER.equals(event.getType()) || VillagerProfession.FLETCHER.equals(event.getType()) || VillagerProfession.TOOLSMITH.equals(event.getType()) || VillagerProfession.WEAPONSMITH.equals(event.getType())) {
				Map<Integer, List<ITrade>> trades = event.getTrades();
				trades.forEach((key, value) -> {
					trades.put(key, value.stream().map(trade -> {
						MerchantOffer offer = trade.getOffer(null, rand);
						return offer.getSellingStack().isEnchanted() ? new ModEnchantedItemTrade(offer, key) : trade;
					}).collect(Collectors.toList()));
				});
			}
		}
	}

	static class ModEnchantedItemTrade implements VillagerTrades.ITrade {
		private final MerchantOffer offer;
		private final int lvl;

		public ModEnchantedItemTrade(MerchantOffer offer, int lvl) {
			this.offer = offer;
			this.lvl = lvl;
		}

		public MerchantOffer getOffer(Entity trader, Random rand) {
			int enchantLvl = ((lvl - 1) << 4) + rand.nextInt(32);
			ItemStack stack = EnchantmentHelper.addRandomEnchantment(rand, new ItemStack(offer.getSellingStack().getItem()), enchantLvl, true);
			return new MerchantOffer(getCurrency(enchantLvl + rand.nextInt(enchantLvl)), offer.getBuyingStackSecond(), stack, offer.func_222214_i(), offer.getGivenExp(), offer.getPriceMultiplier());
		}
	}

	static class ModBookTrade implements VillagerTrades.ITrade {
		private final int xpValue;
		private final int lvl;

		public ModBookTrade(int xpValueIn, int lvl) {
			this.xpValue = xpValueIn;
			this.lvl = lvl;
		}

		public MerchantOffer getOffer(Entity trader, Random rand) {
			int enchantLvl = ((lvl - 1) << 4) + rand.nextInt(32);
			ItemStack book = EnchantmentHelper.addRandomEnchantment(rand, new ItemStack(Items.BOOK), enchantLvl, true);
			return new MerchantOffer(getCurrency(enchantLvl + rand.nextInt(enchantLvl)), new ItemStack(Items.BOOK), book, 12, this.xpValue, 0.2F);
		}
	}

	private static ItemStack getCurrency(int emeralds) {
		Item currency = Items.EMERALD;
		if (emeralds > 64) {
			emeralds = (emeralds + 3) >> 2;
			currency = Items.EMERALD_BLOCK;
			if (emeralds > 64) {
				emeralds = (emeralds + 3) >> 2;
				currency = ModItems.EMERALD_NOTE.get();
				if (emeralds > 64) {
					emeralds = (emeralds + 3) >> 2;
					currency = ModItems.RUBY.get();
				}
			}
		}
		return new ItemStack(currency, emeralds);
	}

	@Mod.EventBusSubscriber(bus = Bus.MOD)
	public static class ModSetupEvents {
		@SubscribeEvent
		public static void registerArgumentTypes(FMLCommonSetupEvent event) {
			ArgumentTypes.register("servermod:faction", FactionArgument.class, new FactionArgument.Serializer());
		}

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

			RenderTypeLookup.setRenderLayer(ModBlocks.TEMPERED_GLASS.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(ModBlocks.CRYSTAL_GLASS.get(), RenderType.getTranslucent());
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
