package io.civetwww.m7ga.common.items.armor;

import com.google.common.base.Suppliers;
import io.civetwww.m7ga.init.ModArmorMaterials;
import io.civetwww.m7ga.common.items.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static io.civetwww.m7ga.M7GA.MODID;


public class MaidArmorItem extends ArmorItem {
    public static final Supplier<ItemStack[]> ARMOR_SET = Suppliers.memoize(() -> new ItemStack[] {
            new ItemStack(ModItems.MAID_HEADGEAR.get()),
            new ItemStack(ModItems.MAID_SUIT.get()),
            new ItemStack(ModItems.MAID_SKIRT.get()),
            new ItemStack(ModItems.MAID_BOOTS.get())
    });

    public MaidArmorItem(Holder<ArmorMaterial> material, Type type, Properties props) {
        super(material, type, props);
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers() {
        // 移除了单独的属性修饰符，改为在MaidArmorEvents中全套装备时统一添加
        return super.getDefaultAttributeModifiers();
    }


    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/models/armor/maid_armor.png");
    }

}