package com.skyline.servermod.common.blocks;

import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GlassStairsBlock extends StairsBlock {

	public GlassStairsBlock(Supplier<BlockState> state, Properties props) {
		super(state, props);
	}

	@Override
	public VoxelShape func_230322_a_(BlockState p_230322_1_, IBlockReader p_230322_2_, BlockPos p_230322_3_,
			ISelectionContext p_230322_4_) {
		return VoxelShapes.empty();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}
}
