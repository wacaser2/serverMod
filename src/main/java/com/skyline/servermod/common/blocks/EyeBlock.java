package com.skyline.servermod.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class EyeBlock extends Block {

	public EyeBlock(Properties properties) {
		super(properties);
	}

	public float getEnchantPowerBonus(BlockState state, IWorldReader world, BlockPos pos) {
		return isBlockPowered(world, pos) ? 5 : 2;
	}

	private boolean isBlockPowered(IWorldReader world, BlockPos pos) {
		if (getRedstonePower(world, pos.down(), Direction.DOWN) > 0) {
			return true;
		} else if (getRedstonePower(world, pos.up(), Direction.UP) > 0) {
			return true;
		} else if (getRedstonePower(world, pos.north(), Direction.NORTH) > 0) {
			return true;
		} else if (getRedstonePower(world, pos.south(), Direction.SOUTH) > 0) {
			return true;
		} else if (getRedstonePower(world, pos.west(), Direction.WEST) > 0) {
			return true;
		} else {
			return getRedstonePower(world, pos.east(), Direction.EAST) > 0;
		}
	}

	private int getRedstonePower(IWorldReader world, BlockPos pos, Direction facing) {
		BlockState blockstate = world.getBlockState(pos);
		int i = blockstate.getWeakPower(world, pos, facing);
		return blockstate.shouldCheckWeakPower(world, pos, facing) ? Math.max(i, world.getStrongPower(pos, facing)) : i;
	}
}
