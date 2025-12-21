package io.civetwww.m7ga.common.items.armor;

import com.google.common.base.Suppliers;
import io.civetwww.m7ga.common.items.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
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
    public void appendHoverText(@NotNull ItemStack stack, @Nullable TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        long currentTime = System.currentTimeMillis();

        // 每个字粉白随机闪烁标题
        String titleText = "这么可爱真是抱歉❤";
        StringBuilder titleBuilder = new StringBuilder();

        for (int i = 0; i < titleText.length(); i++) {
            char c = titleText.charAt(i);
            // 每个字符独立随机闪烁
            boolean isPink = ((currentTime / 10 + i * 7) % 20000) < 10; // 每个字符独立计时
            String colorCode = isPink ? "§f" : "§d"; // 粉色或白色
            titleBuilder.append(colorCode).append("§l").append(c);
        }

        tooltipComponents.add(Component.literal(titleBuilder.toString()));
        tooltipComponents.add(Component.literal(""));

        // 七彩随机效果列表
        tooltipComponents.add(Component.literal("套装效果:").withStyle(ChatFormatting.GRAY));

        // 七彩颜色数组
        ChatFormatting[] rainbowColors = {
                ChatFormatting.RED, ChatFormatting.GOLD, ChatFormatting.YELLOW,
                ChatFormatting.GREEN, ChatFormatting.AQUA, ChatFormatting.BLUE,
                ChatFormatting.LIGHT_PURPLE
        };

        // 效果列表
        String[] effects = {
                "免疫负面效果",
                "移速提升 +3%",
                "最大生命值 +20",
                "击退抗性 +0.7",
                "再生 II",
                "夜视",
                "幸运 VII",
                "火免",
                "饱食",
                "空手怪力",
                "※飞行"
        };

        // 为每个效果随机分配颜色
        for (int i = 0; i < effects.length; i++) {
            int colorIndex = (int)((currentTime / 5000 + i) % rainbowColors.length);
            ChatFormatting effectColor = rainbowColors[colorIndex];
            tooltipComponents.add(Component.literal("• " + effects[i]).withStyle(effectColor));
        }


        if (context != null) {
            super.appendHoverText(stack, context, tooltipComponents, isAdvanced);
        }
    }

    @Override
    public ResourceLocation getArmorTexture(@NotNull ItemStack stack, @NotNull Entity entity, @NotNull EquipmentSlot slot, ArmorMaterial.@NotNull Layer layer, boolean innerModel) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/models/armor/maid_armor.png");
    }
}