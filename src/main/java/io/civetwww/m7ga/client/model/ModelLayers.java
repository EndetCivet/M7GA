package io.civetwww.m7ga.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import static io.civetwww.m7ga.M7GA.MODID;

public class ModelLayers {

    public static final ModelLayerLocation MAID_ARMOR_NORMAL = make("maid_armor_normal");
    public static final ModelLayerLocation MAID_ARMOR_DRESS = make("maid_armor_dress");

    private static ModelLayerLocation make(String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MODID, name), "main");
    }

}