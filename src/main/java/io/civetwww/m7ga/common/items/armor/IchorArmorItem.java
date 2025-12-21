package io.civetwww.m7ga.common.items.armor;

import com.google.common.base.Suppliers;
import io.civetwww.m7ga.common.items.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class IchorArmorItem extends ArmorItem {
    public static final Supplier<ItemStack[]> ARMOR_SET = Suppliers.memoize(() -> new ItemStack[] {
            new ItemStack(ModItems.ICHOR_HELMET.get()),
            new ItemStack(ModItems.ICHOR_CHESTPLATE.get()),
            new ItemStack(ModItems.ICHOR_LEGGINGS.get()),
            new ItemStack(ModItems.ICHOR_BOOTS.get())
    });

    public IchorArmorItem(Holder<ArmorMaterial> material, Type type, Properties props) {
        super(material, type, props);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        // 添加套装名称
        tooltipComponents.add(Component.literal("§6§l像神一样").withStyle(ChatFormatting.DARK_RED));
        tooltipComponents.add(Component.literal(""));

        // 套装效果标题
        tooltipComponents.add(Component.literal("§7套装效果:").withStyle(ChatFormatting.GRAY));
        
        // 套装效果列表
        tooltipComponents.add(Component.literal("§6• +3% 移动速度").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal("§6• +30 最大生命值").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal("§6• +7 攻击力").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal("§6• +50% 击退抗性").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal("§6• 夜视能力").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal("§6• 火焰免疫").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal("§6• 急迫 II").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal("§6• 饱食").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal("§6※ 飞行").withStyle(ChatFormatting.GOLD));

        if (context != null) {
            super.appendHoverText(stack, context, tooltipComponents, isAdvanced);
        }
    }

}