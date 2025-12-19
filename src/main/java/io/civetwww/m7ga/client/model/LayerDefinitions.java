package io.civetwww.m7ga.client.model;

import io.civetwww.m7ga.client.model.armor.MaidArmorModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class LayerDefinitions {
    public static void init(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> consumer) {

        consumer.accept(ModelLayers.MAID_ARMOR_NORMAL, () -> LayerDefinition.create(MaidArmorModel.createNormalMesh(), 128, 128));
        consumer.accept(ModelLayers.MAID_ARMOR_DRESS, () -> LayerDefinition.create(MaidArmorModel.createDressMesh(), 128, 128));

    }
}
