package io.civetwww.m7ga.init;

import io.civetwww.m7ga.M7GA;
import io.civetwww.m7ga.items.ModItems;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;


public class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, M7GA.MODID);

    public static final Holder<ArmorMaterial> ICHOR_CLOTH_MATERIAL = ARMOR_MATERIALS.register("ichor_cloth", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, 7);
            map.put(ArmorItem.Type.LEGGINGS, 7);
            map.put(ArmorItem.Type.CHESTPLATE, 7);
            map.put(ArmorItem.Type.HELMET, 7);
            map.put(ArmorItem.Type.BODY, 7);
    }), 77, SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(ModItems.ICHOR_CLOTH.get()),
        List.of(
        new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(M7GA.MODID, "ichor_cloth"))
    ), 7.0F, 0.17F));

    public static final Holder<ArmorMaterial> RAINBOW_CLOTH_MATERIAL = ARMOR_MATERIALS.register("rainbow_cloth", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 7);
                map.put(ArmorItem.Type.LEGGINGS, 7);
                map.put(ArmorItem.Type.CHESTPLATE, 7);
                map.put(ArmorItem.Type.HELMET, 7);
                map.put(ArmorItem.Type.BODY, 7);
            }), 77, SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(ModItems.RAINBOW_CLOTH.get()),
            List.of(
                    new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(M7GA.MODID, "rainbow_cloth"))
            ), 7.0F, 0.17F));
}
