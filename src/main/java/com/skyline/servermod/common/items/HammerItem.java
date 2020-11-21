package com.skyline.servermod.common.items;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.skyline.servermod.common.enchantments.ModEnchants;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HammerItem extends Item implements IVanishable {
	private static final Logger LOGGER = LogManager.getLogger();

	private final Multimap<Attribute, AttributeModifier> attributes;

	private int duration = 0;
	private boolean th = false;
	private boolean rh = false;
	private boolean pn = false;

	public HammerItem(Properties properties) {
		super(properties);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.field_233823_f_, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", 8.0D, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.field_233825_h_, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double) -3.2F, AttributeModifier.Operation.ADDITION));
		this.attributes = builder.build();
	}

	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (isCharged(stack)) {
			tooltip.add(new StringTextComponent("~~~"));
		}
	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributes : super.getAttributeModifiers(equipmentSlot);
	}

	public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative();
	}

	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damageItem(1, attacker, (wielder) -> {
			wielder.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
		return true;
	}

	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return Material.GLASS.equals(state.getMaterial()) ? 10F : 1.0F;
	}

	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (!Material.GLASS.equals(state.getMaterial()) || (double) state.getBlockHardness(worldIn, pos) != 0.0D) {
			stack.damageItem(2, entityLiving, (wielder) -> {
				wielder.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			});
		}
		return true;
	}

	public int getItemEnchantability() { return 1; }

	public UseAction getUseAction(ItemStack stack) {
		return isCharged(stack) ? UseAction.SPEAR : canThunder(stack) && duration < getChargeTime(stack) ? UseAction.BOW : canTempest(stack) ? UseAction.SPEAR : canStorm(stack) && duration < getStormTime(stack) ? UseAction.CROSSBOW : UseAction.NONE;
	}

	public int getUseDuration(ItemStack stack) {
		return 1000;
	}

	public boolean isCrossbow(ItemStack stack) {
		return true;
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		th = worldIn.isThundering();
		rh = worldIn.isRaining();
		pn = worldIn.canBlockSeeSky(new BlockPos(playerIn.getPositionVec()));

		if (!worldIn.isRemote()) {
			if (isCharged(stack) || canThunder(stack) || canTempest(stack) || canStorm(stack)) {
				playerIn.setActiveHand(handIn);
				return ActionResult.resultConsume(stack);
			}
		}
		return ActionResult.resultFail(stack);
	}

	private static Vector3d getTempestMotion(int spd, Vector3d vec) {
		double vel = spd * .01F;
		Vector3d m = vec.scale(vel).add(new Vector3d(0, .082F, 0));
		return m;
	}

	public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
		duration = getUseDuration(stack) - count;
		if (canTempest(stack)) {
			int spd = tempestPower(stack) - (th ? 0 : pn ? 1 : 2);
			if (spd > 0) {
				Vector3d m = getTempestMotion(spd, livingEntityIn.getLookVec());
				livingEntityIn.setMotion(livingEntityIn.getMotion().add(m.getX(), m.getY(), m.getZ()));
			}
		}
		if (!worldIn.isRemote()) {
			if (canThunder(stack) && !isCharged(stack) && duration == getChargeTime(stack)) {
				lightningStrike(worldIn, livingEntityIn, livingEntityIn.getPositionVec(), true);
			}
			if (canStorm(stack) && worldIn instanceof ServerWorld) {
				int sp = stormPower(stack);
				ServerWorld sw = (ServerWorld) worldIn;
				if (sp > 1) {
					if (rh) {
						if (duration == getStormTime(stack) / 2) {
							setStorm(sw, stack, true);
						}
					} else {
						if (duration == getStormTime(stack) / 2) {
							setStorm(sw, stack, false);
						} else if (duration == getStormTime(stack)) {
							setStorm(sw, stack, true);
						}
					}
				} else {
					if (rh) {
						if (duration == getStormTime(stack)) {
							setStorm(sw, stack, false);

						}
					}
				}
			}
		}
	}

	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (!worldIn.isRemote()) {
			duration = getUseDuration(stack) - timeLeft;
			if (isCharged(stack)) {
				shootLightning(worldIn, entityLiving, entityLiving.getActiveHand(), stack);
			} else if (canThunder(stack) && duration >= getChargeTime(stack)) {
				setCharged(stack, true);
			}
			if (canTempest(stack)) {
				int tp = tempestPower(stack);
				entityLiving.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, Math.min(duration, 20 * tp), tp, false, false));
			}

			duration = 0;
		}
	}

	private boolean canThunder(ItemStack stack) {
		return thunderPower(stack) > 0 && th && pn;
	}

	private boolean canTempest(ItemStack stack) {
		return pn && tempestPower(stack) > (th ? 0 : rh ? 1 : 2);
	}

	private boolean canStorm(ItemStack stack) {
		return !th && pn && stormPower(stack) > (rh ? 0 : 1);
	}

	private static int thunderPower(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(ModEnchants.THUNDER.get(), stack);
	}

	private static int tempestPower(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(ModEnchants.TEMPEST.get(), stack);
	}

	private static int stormPower(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(ModEnchants.STORM.get(), stack);
	}

	public static boolean isCharged(ItemStack stack) {
		CompoundNBT compoundnbt = stack.getTag();
		return compoundnbt != null && compoundnbt.getBoolean("Charged");
	}

	public static void setCharged(ItemStack stack, boolean chargedIn) {
		CompoundNBT compoundnbt = stack.getOrCreateTag();
		compoundnbt.putBoolean("Charged", chargedIn);
	}

	public static int getChargeTime(ItemStack stack) {
		return 100 - 10 * thunderPower(stack);
	}

	public static int getStormTime(ItemStack stack) {
		return 200;
	}

	public void setStorm(ServerWorld world, ItemStack stack, boolean thunder) {
		world.func_241113_a_(0, 1000, true, thunder);
		if (!canThunder(stack)) {
			duration = 0;
		}
		th = thunder;
		rh = true;
	}

	public static void shootLightning(World worldIn, LivingEntity shooter, Hand handIn, ItemStack stack) {
		if (!worldIn.isRemote) {
			float f = shooter.rotationPitch;
			float f1 = shooter.rotationYaw;
			Vector3d vector3d = shooter.getEyePosition(1.0F);
			float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
			float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
			float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
			float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));
			float f6 = f3 * f4;
			float f7 = f2 * f4;
			double d0 = 10 * EnchantmentHelper.getEnchantmentLevel(ModEnchants.THUNDER.get(), stack);
			Vector3d vector3d1 = vector3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
			BlockRayTraceResult result = worldIn.rayTraceBlocks(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.OUTLINE, FluidMode.ANY, shooter));

			if (!RayTraceResult.Type.MISS.equals(result.getType())) {
				stack.damageItem(5, shooter, (player) -> {
					player.sendBreakAnimation(handIn);
				});
				int thp = thunderPower(stack);
				Random rng = shooter.getRNG();
				Vector3d hit = result.getHitVec();
				float spread = 3F;
				for (int i = 0; i < thp; i++) {
					lightningStrike(worldIn, shooter, hit, false);
					hit = hit.add(new Vector3d(rng.nextGaussian() * spread, 0, rng.nextGaussian() * spread));
				}
				afterStorm(worldIn, shooter, stack);
			}
		}
	}

	private static void lightningStrike(World worldIn, LivingEntity shooter, Vector3d target, boolean harmless) {
		LightningBoltEntity lightningEntity = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
		if (shooter instanceof ServerPlayerEntity) {
			lightningEntity.setCaster((ServerPlayerEntity) shooter);
		}
		lightningEntity.func_233623_a_(harmless);
		lightningEntity.setPosition(target.getX(), target.getY(), target.getZ());
		worldIn.addEntity(lightningEntity);
	}

	private static void afterStorm(World worldIn, LivingEntity shooter, ItemStack stack) {
		setCharged(stack, false);
		if (shooter instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) shooter;
			serverplayerentity.addStat(Stats.ITEM_USED.get(stack.getItem()));
		}
	}
}
