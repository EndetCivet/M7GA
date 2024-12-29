package com.civetwww.m7ga.modgenerated;

import com.civetwww.m7ga.M7GA;
import com.civetwww.m7ga.blocks.ModBlocks;
import com.civetwww.m7ga.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class resource {
    public static class ModelProvider extends ItemModelProvider {
        public ModelProvider(PackOutput gen, ExistingFileHelper helper) {
            super(gen, M7GA.MODID, helper);
        }

        @Override
        protected void registerModels() {
            this.singleTexture(ModItems.RAINBOW_PICKAXE_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID, "item/" + ModItems.RAINBOW_PICKAXE_ID));
            this.singleTexture(ModItems.ICHOR_AXE_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID, "item/" + ModItems.ICHOR_AXE_ID));
            this.singleTexture(ModItems.ICHOR_INGOT_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID, "item/" + ModItems.ICHOR_INGOT_ID));
            this.singleTexture(ModItems.ICHOR_ROD_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID, "item/" + ModItems.ICHOR_ROD_ID));
            this.singleTexture(ModItems.RAINBOW_INGOT_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID, "item/" + ModItems.RAINBOW_INGOT_ID));
        }
    }
    public static class StateProvider extends BlockStateProvider {
        public StateProvider(PackOutput gen, ExistingFileHelper helper) {
            super(gen, M7GA.MODID, helper);
        }

        @Override
        protected void registerStatesAndModels() {
            this.simpleBlock(ModBlocks.ICHOR_BLOCK.get(), this.cubeAll(ModBlocks.ICHOR_BLOCK.get()));
            this.simpleBlockItem(ModBlocks.ICHOR_BLOCK.get(), this.cubeAll(ModBlocks.ICHOR_BLOCK.get()));
            this.simpleBlock(ModBlocks.RAINBOW_BLOCK.get(), this.cubeAll(ModBlocks.RAINBOW_BLOCK.get()));
            this.simpleBlockItem(ModBlocks.RAINBOW_BLOCK.get(), this.cubeAll(ModBlocks.RAINBOW_BLOCK.get()));
        }
    }
}
