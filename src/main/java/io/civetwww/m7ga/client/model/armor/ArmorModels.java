package io.civetwww.m7ga.client.model.armor;

import io.civetwww.m7ga.client.model.ModelLayers;
import io.civetwww.m7ga.lib.ArmorModel;
import io.civetwww.m7ga.common.items.armor.MaidArmorItem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class ArmorModels {

        private static Map<EquipmentSlot, ArmorModel> Maid = Collections.emptyMap();

        private static Map<EquipmentSlot, ArmorModel> makeIdol(EntityRendererProvider.Context ctx) {
            Map<EquipmentSlot, ArmorModel> ret = new EnumMap<>(EquipmentSlot.class);
            for (var slot : EquipmentSlot.values()) {
                var mesh = ctx.bakeLayer(slot != EquipmentSlot.LEGS ? ModelLayers.MAID_ARMOR_NORMAL : ModelLayers.MAID_ARMOR_DRESS);
                ret.put(slot, new ArmorModel(mesh, slot));
            }
            return ret;
        }

        public static void init(EntityRendererProvider.Context ctx) {
            Maid = makeIdol(ctx);

        }

        @Nullable
        public static ArmorModel get(ItemStack stack) {
            Item item = stack.getItem();
            if (item instanceof MaidArmorItem maid) {
                return Maid.get(maid.getEquipmentSlot());
            }
            return null;
        }
}