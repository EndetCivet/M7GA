package io.civetwww.m7ga.init;

import io.civetwww.m7ga.M7GA;
import io.civetwww.m7ga.common.items.ModItems;
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
    // 护甲材质注册器
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, M7GA.MODID);

    // 伊赫布护甲材质
    public static final Holder<ArmorMaterial> ICHOR_CLOTH_MATERIAL = ARMOR_MATERIALS.register("ichor_cloth", () -> new ArmorMaterial(
            // 设置各部位防御值
            new EnumMap<>(ArmorItem.Type.class) {{
                put(ArmorItem.Type.BOOTS, 7);     // 靴子
                put(ArmorItem.Type.LEGGINGS, 7);  // 护腿
                put(ArmorItem.Type.CHESTPLATE, 7); // 胸甲
                put(ArmorItem.Type.HELMET, 7);    // 头盔
            }},
            77,  // 附魔值
            SoundEvents.ARMOR_EQUIP_LEATHER,  // 装备声音
            () -> Ingredient.of(ModItems.ICHOR_CLOTH.get()),  // 修复材料
            List.of(
                    new ArmorMaterial.Layer(
                            ResourceLocation.fromNamespaceAndPath(M7GA.MODID, "ichor_cloth")  // 纹理位置
                    )
            ),
            7.0F,  // 韧性
            0.17F  // 击退抗性
    ));

    // 女仆护甲材质
    public static final Holder<ArmorMaterial> MAID_MATERIAL = ARMOR_MATERIALS.register("maid_armor", () -> new ArmorMaterial(
            // 设置各部位防御值
            new EnumMap<>(ArmorItem.Type.class) {{
                put(ArmorItem.Type.BOOTS, 7);     // 靴子
                put(ArmorItem.Type.LEGGINGS, 7);  // 护腿
                put(ArmorItem.Type.CHESTPLATE, 7); // 胸甲
                put(ArmorItem.Type.HELMET, 7);    // 头盔
            }},
            77,  // 附魔值
            SoundEvents.ARMOR_EQUIP_LEATHER,  // 装备声音
            () -> Ingredient.of(ModItems.RAINBOW_CLOTH.get()),  // 修复材料
            List.of(
                    new ArmorMaterial.Layer(
                            ResourceLocation.fromNamespaceAndPath(M7GA.MODID, "maid_armor")  // 纹理位置
                    )
            ),
            7.0F,  // 韧性
            0.17F  // 击退抗性
    ));
}
