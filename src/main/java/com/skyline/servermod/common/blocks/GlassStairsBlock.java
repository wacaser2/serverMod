package main.java.com.skyline.servermod.common.blocks;

import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class GlassStairsBlock extends StairsBlock {

	public GlassStairsBlock(Supplier<BlockState> state, Properties props) {
		super(state, props.notSolid());
	}

	@Override
	public boolean isTransparent(BlockState state) {
		return true;
	}
}
