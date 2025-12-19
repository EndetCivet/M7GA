package io.civetwww.m7ga.modgenerated;

import io.civetwww.m7ga.M7GA;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static io.civetwww.m7ga.common.items.ModItems.*;

public class ModResources {
    public static class ModelProvider extends ItemModelProvider {
        public ModelProvider(PackOutput gen, ExistingFileHelper helper) {
            super(gen, M7GA.MODID, helper);
        }

        @Override
        protected void registerModels() {
            //工具
            this.singleTexture(RAINBOW_PICKAXE_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + RAINBOW_PICKAXE_ID));
            this.singleTexture(ICHOR_AXE_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + ICHOR_AXE_ID));
            //灵布物品
            this.singleTexture(ICHOR_INGOT_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + ICHOR_INGOT_ID));
            this.singleTexture(ICHOR_ROD_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + ICHOR_ROD_ID));
            this.singleTexture(ICHOR_CLOTH_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + ICHOR_CLOTH_ID));
            //七彩物品
            this.singleTexture(RAINBOW_INGOT_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + RAINBOW_INGOT_ID));
            this.singleTexture(RAINBOW_CLOTH_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + RAINBOW_CLOTH_ID));
            //觉醒模板核心
            this.singleTexture(MEGA_TEMPLATE_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + MEGA_TEMPLATE_ID));
            this.singleTexture(ICHOR_CORE_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + ICHOR_CORE_ID));
            this.singleTexture(RAINBOW_CORE_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + RAINBOW_CORE_ID));
            //灵布护甲
            this.singleTexture(ICHOR_HELMET_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + ICHOR_HELMET_ID));
            this.singleTexture(ICHOR_CHESTPLATE_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + ICHOR_CHESTPLATE_ID));
            this.singleTexture(ICHOR_LEGGINGS_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + ICHOR_LEGGINGS_ID));
            this.singleTexture(ICHOR_BOOTS_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + ICHOR_BOOTS_ID));
            //七彩护甲
            this.singleTexture(MAID_HEADGEAR_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + MAID_HEADGEAR_ID));
            this.singleTexture(MAID_SUIT_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + MAID_SUIT_ID));
            this.singleTexture(MAID_SKIRT_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + MAID_SKIRT_ID));
            this.singleTexture(MAID_BOOTS_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(M7GA.MODID,
                    "item/" + MAID_BOOTS_ID));
        }
    }
}
