package com.skyline.servermod;

import com.skyline.servermod.common.items.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.fml.RegistryObject;

public class ItemModels extends ItemModelProvider {
		public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
			super(generator, ServerMod.MODID, existingFileHelper);
		}

		@Override
		protected void registerModels() {
			addItem(ModItems.EMERALD_NOTE);
			addItem(ModItems.HAMMER);
			addItem(ModItems.REAM);

			addItem(ModItems.CARROT_BUSHEL);
			addItem(ModItems.POTATO_BUSHEL);
			addItem(ModItems.BEETROOT_BUSHEL);
			addItem(ModItems.BEETROOT_SEEDS_FEEDBAG);
			addItem(ModItems.MELON_SEEDS_FEEDBAG);
			addItem(ModItems.PUMPKIN_SEEDS_FEEDBAG);
			addItem(ModItems.WHEAT_SEEDS_FEEDBAG);
			addItem(ModItems.QUIVER);
			addItem(ModItems.CARTON);
			addItem(ModItems.PILLOW);

			getBuilder("ruby").parent(new UncheckedModelFile("item/generated")).texture("layer0", mcLoc("item/ruby"));
		}
		
		private void addItem(RegistryObject<Item> item) {
			String path = item.get().getRegistryName().getPath();
			getBuilder(path).parent(new UncheckedModelFile("item/generated")).texture("layer0", modLoc("item/" + path));
		}
	}