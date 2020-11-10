package com.skyline.servermod;

import com.skyline.servermod.common.blocks.ModBlocks;
import com.skyline.servermod.common.blocks.ModBlocks.BlockSet;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class BlockStates extends BlockStateProvider {
	public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, ServerMod.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
//		// Unnecessarily complicated example to showcase how manual building works
//		ModelFile birchFenceGate = models().fenceGate("birch_fence_gate", mcLoc("block/birch_planks"));
//		ModelFile birchFenceGateOpen = models().fenceGateOpen("birch_fence_gate_open", mcLoc("block/birch_planks"));
//		ModelFile birchFenceGateWall = models().fenceGateWall("birch_fence_gate_wall", mcLoc("block/birch_planks"));
//		ModelFile birchFenceGateWallOpen = models().fenceGateWallOpen("birch_fence_gate_wall_open",
//				mcLoc("block/birch_planks"));
//		ModelFile invisbleModel = new UncheckedModelFile(new ResourceLocation("builtin/generated"));
//		VariantBlockStateBuilder builder = getVariantBuilder(Blocks.BIRCH_FENCE_GATE);
//		for (Direction dir : FenceGateBlock.HORIZONTAL_FACING.getAllowedValues()) {
//			int angle = (int) dir.getHorizontalAngle();
//			builder.partialState().with(FenceGateBlock.HORIZONTAL_FACING, dir).with(FenceGateBlock.IN_WALL, false)
//					.with(FenceGateBlock.OPEN, false).modelForState().modelFile(invisbleModel).nextModel()
//					.modelFile(birchFenceGate).rotationY(angle).uvLock(true).weight(100).addModel().partialState()
//					.with(FenceGateBlock.HORIZONTAL_FACING, dir).with(FenceGateBlock.IN_WALL, false)
//					.with(FenceGateBlock.OPEN, true).modelForState().modelFile(birchFenceGateOpen).rotationY(angle)
//					.uvLock(true).addModel().partialState().with(FenceGateBlock.HORIZONTAL_FACING, dir)
//					.with(FenceGateBlock.IN_WALL, true).with(FenceGateBlock.OPEN, false).modelForState()
//					.modelFile(birchFenceGateWall).rotationY(angle).uvLock(true).addModel().partialState()
//					.with(FenceGateBlock.HORIZONTAL_FACING, dir).with(FenceGateBlock.IN_WALL, true)
//					.with(FenceGateBlock.OPEN, true).modelForState().modelFile(birchFenceGateWallOpen).rotationY(angle)
//					.uvLock(true).addModel();
//		}
//
//		// Realistic examples using helpers
//		simpleBlock(Blocks.STONE, model -> ObjectArrays.concat(ConfiguredModel.allYRotations(model, 0, false),
//				ConfiguredModel.allYRotations(model, 180, false), ConfiguredModel.class));
//
//		// From here on, models are 1-to-1 copies of vanilla (except for model
//		// locations) and will be tested as such below
//		ModelFile block = models().getBuilder("block").guiLight(GuiLight.SIDE).transforms().transform(Perspective.GUI)
//				.rotation(30, 225, 0).scale(0.625f).end().transform(Perspective.GROUND).translation(0, 3, 0)
//				.scale(0.25f).end().transform(Perspective.FIXED).scale(0.5f).end()
//				.transform(Perspective.THIRDPERSON_RIGHT).rotation(75, 45, 0).translation(0, 2.5f, 0).scale(0.375f)
//				.end().transform(Perspective.FIRSTPERSON_RIGHT).rotation(0, 45, 0).scale(0.4f).end()
//				.transform(Perspective.FIRSTPERSON_LEFT).rotation(0, 225, 0).scale(0.4f).end().end();
//
//		models().getBuilder("cube").parent(block).element()
//				.allFaces((dir, face) -> face.texture("#" + dir.toString()).cullface(dir));
//
//		ModelFile furnace = models().orientable("furnace", mcLoc("block/furnace_side"), mcLoc("block/furnace_front"),
//				mcLoc("block/furnace_top"));
//		ModelFile furnaceLit = models().orientable("furnace_on", mcLoc("block/furnace_side"),
//				mcLoc("block/furnace_front_on"), mcLoc("block/furnace_top"));
//
//		getVariantBuilder(Blocks.FURNACE).forAllStates(
//				state -> ConfiguredModel.builder().modelFile(state.get(FurnaceBlock.LIT) ? furnaceLit : furnace)
//						.rotationY((int) state.get(FurnaceBlock.FACING).getOpposite().getHorizontalAngle()).build());
//
//		ModelFile barrel = models().cubeBottomTop("barrel", mcLoc("block/barrel_side"), mcLoc("block/barrel_bottom"),
//				mcLoc("block/barrel_top"));
//		ModelFile barrelOpen = models().cubeBottomTop("barrel_open", mcLoc("block/barrel_side"),
//				mcLoc("block/barrel_bottom"), mcLoc("block/barrel_top_open"));
//		directionalBlock(Blocks.BARREL, state -> state.get(BarrelBlock.PROPERTY_OPEN) ? barrelOpen : barrel); // Testing
//																												// custom
//																												// state
//																												// interpreter
//
//		logBlock((RotatedPillarBlock) Blocks.ACACIA_LOG);
//
		addSimpleBlock(ModBlocks.SHALE);
		
		for(BlockSet blockSet : ModBlocks.VARIANT_SETS) {
			registerBlockSet(blockSet);
		}
//
//		fenceBlock((FenceBlock) Blocks.ACACIA_FENCE, "acacia", mcLoc("block/acacia_planks"));
//		fenceGateBlock((FenceGateBlock) Blocks.ACACIA_FENCE_GATE, "acacia", mcLoc("block/acacia_planks"));
//
//		wallBlock((WallBlock) Blocks.COBBLESTONE_WALL, "cobblestone", mcLoc("block/cobblestone"));
//
//		paneBlock((PaneBlock) Blocks.GLASS_PANE, "glass", mcLoc("block/glass"), mcLoc("block/glass_pane_top"));
//
//		doorBlock((DoorBlock) Blocks.ACACIA_DOOR, "acacia", mcLoc("block/acacia_door_bottom"),
//				mcLoc("block/acacia_door_top"));
//		trapdoorBlock((TrapDoorBlock) Blocks.ACACIA_TRAPDOOR, "acacia", mcLoc("block/acacia_trapdoor"), true);
//		trapdoorBlock((TrapDoorBlock) Blocks.OAK_TRAPDOOR, "oak", mcLoc("block/oak_trapdoor"), false); // Test a
//																										// non-orientable
//																										// trapdoor
//
//		simpleBlock(Blocks.TORCH, models().torch("torch", mcLoc("block/torch")));
//		horizontalBlock(Blocks.WALL_TORCH, models().torchWall("wall_torch", mcLoc("block/torch")), 90);
	}
	
	private void addSimpleBlock(RegistryObject<Block> block) {
		String name = block.get().getRegistryName().getPath();
		simpleBlock(block.get(), models().cubeAll(name, modLoc("block/" + name)));
		
		addBlockItem(block);
	}
	
	private void registerBlockSet(BlockSet blockSet) {
		String baseName = blockSet.baseBlock.getRegistryName().getPath().replace("_block", "");
		ResourceLocation baseLoc = blockSet.baseBlock.getRegistryName();
		ResourceLocation tex = mcLoc("block/" + baseLoc.getPath());
//
		stairsBlock((StairsBlock) blockSet.variants.get(0).get(), baseName, tex);
		slabBlock((SlabBlock) blockSet.variants.get(1).get(), baseLoc, tex);
		wallBlock((WallBlock) blockSet.variants.get(2).get(), baseName, tex);
		
		addBlockItem(blockSet.variants.get(0));
		addBlockItem(blockSet.variants.get(1));
		addBlockItem(blockSet.variants.get(2), "_post");
	}
	
	private void addBlockItem(RegistryObject<Block> variant) {
		simpleBlockItem(variant.get(), models().getExistingFile(modLoc("block/" + variant.get().getRegistryName().getPath())));
	}
	
	private void addBlockItem(RegistryObject<Block> variant, String path_append) {
		simpleBlockItem(variant.get(), models().getExistingFile(modLoc("block/" + variant.get().getRegistryName().getPath() + path_append)));
	}
}