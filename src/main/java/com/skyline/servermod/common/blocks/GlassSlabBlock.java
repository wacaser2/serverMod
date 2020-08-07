package main.java.com.skyline.servermod.common.blocks;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.properties.SlabType;

public class GlassSlabBlock extends SlabBlock {
	public GlassSlabBlock(Properties props) {
		super(props);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this, state.get(TYPE) == SlabType.DOUBLE ? 2 : 1));
	}

	@Override
	public boolean isTransparent(BlockState state) {
		return true;
	}
}
