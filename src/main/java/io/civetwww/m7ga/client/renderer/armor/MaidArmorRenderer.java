package io.civetwww.m7ga.client.renderer.armor;

import io.civetwww.m7ga.client.model.armor.ArmorModels;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class MaidArmorRenderer implements IClientItemExtensions {
    
    @Override
    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, 
                                                 EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
        var model = ArmorModels.get(itemStack);
        if (model != null) {
            return model;
        }
        return original;
    }
}