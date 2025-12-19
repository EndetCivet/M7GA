package io.civetwww.m7ga.common.items;

import io.civetwww.m7ga.M7GA;
import io.civetwww.m7ga.common.items.armor.MaidArmorItem;
import io.civetwww.m7ga.common.items.tools.IchorAxe;
import io.civetwww.m7ga.common.items.tools.RainbowPickaxe;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import io.civetwww.m7ga.init.ModArmorMaterials;
import static io.civetwww.m7ga.init.ModToolsTiers.MODTOOLS_TIER;

public class ModItems {
    //注册器
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.Items.createItems(M7GA.MODID);

    public static DeferredItem<Item> registerItem(String name, Supplier<Item> itemSupplier) {
        return (DeferredItem<Item>) ITEMS.register(name,itemSupplier);
    }


    //灵宝系列物品
    public static final String ICHOR_INGOT_ID = "ichor_ingot";
    public static final Supplier<Item> ICHOR_INGOT = ITEMS.register(ICHOR_INGOT_ID,()-> new Item(new Item.Properties().fireResistant()));
    public static final String ICHOR_ROD_ID = "ichor_rod";
    public static final Supplier<Item> ICHOR_ROD = ITEMS.register(ICHOR_ROD_ID,()-> new Item(new Item.Properties().fireResistant()));
    public static final String ICHOR_CLOTH_ID = "ichor_cloth";
    public static final Supplier<Item> ICHOR_CLOTH = ITEMS.register(ICHOR_CLOTH_ID,()-> new Item(new Item.Properties().fireResistant()));

    //七彩系列物品
    public static final String RAINBOW_INGOT_ID = "rainbow_ingot";
    public static final Supplier<Item> RAINBOW_INGOT = ITEMS.register(RAINBOW_INGOT_ID,()-> new Item(new Item.Properties().fireResistant()));
    public static final String RAINBOW_CLOTH_ID = "rainbow_cloth";
    public static final Supplier<Item> RAINBOW_CLOTH = ITEMS.register(RAINBOW_CLOTH_ID,()-> new Item(new Item.Properties().fireResistant()));
    //锻造模板、核心
    public static final String ICHOR_CORE_ID = "ichor_core";
    public static final Supplier<Item> ICHOR_CORE = ITEMS.register(ICHOR_CORE_ID,()-> new Item(new Item.Properties().fireResistant()));
    public static final String RAINBOW_CORE_ID = "rainbow_core";
    public static final Supplier<Item> RAINBOW_CORE = ITEMS.register(RAINBOW_CORE_ID,()-> new Item(new Item.Properties().fireResistant()));
    public static final String MEGA_TEMPLATE_ID = "mega_template";
    public static final Supplier<Item> MEGA_TEMPLATE = ITEMS.register(MEGA_TEMPLATE_ID,()-> new Item(new Item.Properties().fireResistant()));

    //灵宝斧
    public static final String ICHOR_AXE_ID = "ichor_axe";
    public static final Supplier<AxeItem> ICHOR_AXE = ITEMS.register(ICHOR_AXE_ID, () -> new IchorAxe(
            MODTOOLS_TIER,
            new Item.Properties().attributes(
                    IchorAxe.createAttributes(
                            MODTOOLS_TIER,
                            27,
                            3f
                    )
            )
    ));

    //彩虹镐
    public static final String RAINBOW_PICKAXE_ID = "rainbow_pickaxe";
    public static final Supplier<PickaxeItem> RAINBOW_PICKAXE = ITEMS.register(RAINBOW_PICKAXE_ID,()-> new RainbowPickaxe(
            MODTOOLS_TIER,
            new Item.Properties().attributes(
                    RainbowPickaxe.createAttributes(
                            MODTOOLS_TIER,
                            17,
                            2f
                    )
            )
    ));

    //灵布护甲
    public static final String ICHOR_HELMET_ID = "ichor_helmet";
    public static final Supplier<ArmorItem> ICHOR_HELMET = ITEMS.register(ICHOR_HELMET_ID, () -> new ArmorItem(
            ModArmorMaterials.ICHOR_CLOTH_MATERIAL,
            ArmorItem.Type.HELMET,
            new Item.Properties()
    ));
    public static final String ICHOR_CHESTPLATE_ID = "ichor_chestplate";
    public static final Supplier<ArmorItem> ICHOR_CHESTPLATE = ITEMS.register(ICHOR_CHESTPLATE_ID, () -> new ArmorItem(
            ModArmorMaterials.ICHOR_CLOTH_MATERIAL,
            ArmorItem.Type.CHESTPLATE,
            new Item.Properties()
    ));
    public static final String ICHOR_LEGGINGS_ID = "ichor_leggings";
    public static final Supplier<ArmorItem> ICHOR_LEGGINGS = ITEMS.register(ICHOR_LEGGINGS_ID, () -> new ArmorItem(
            ModArmorMaterials.ICHOR_CLOTH_MATERIAL,
            ArmorItem.Type.LEGGINGS,
            new Item.Properties()
    ));
    public static final String ICHOR_BOOTS_ID = "ichor_boots";
    public static final Supplier<ArmorItem> ICHOR_BOOTS = ITEMS.register(ICHOR_BOOTS_ID, () -> new ArmorItem(
            ModArmorMaterials.ICHOR_CLOTH_MATERIAL,
            ArmorItem.Type.BOOTS,
            new Item.Properties()
    ));

    //女仆
    public static final String MAID_HEADGEAR_ID = "maid_headgear";
    public static final Supplier<ArmorItem> MAID_HEADGEAR = ITEMS.register(MAID_HEADGEAR_ID, () -> new MaidArmorItem(
            ModArmorMaterials.MAID_MATERIAL,
            ArmorItem.Type.HELMET,
            new Item.Properties().stacksTo(1)
    ));

    public static final String MAID_SUIT_ID = "maid_suit";
    public static final Supplier<ArmorItem> MAID_SUIT = ITEMS.register(MAID_SUIT_ID, () -> new MaidArmorItem(
            ModArmorMaterials.MAID_MATERIAL,
            ArmorItem.Type.CHESTPLATE,
            new Item.Properties().stacksTo(1)
    ));

    public static final String MAID_SKIRT_ID = "maid_skirt";
    public static final Supplier<ArmorItem> MAID_SKIRT = ITEMS.register(MAID_SKIRT_ID, () -> new MaidArmorItem(
            ModArmorMaterials.MAID_MATERIAL,
            ArmorItem.Type.LEGGINGS,
            new Item.Properties().stacksTo(1)
    ));

    public static final String MAID_BOOTS_ID = "maid_boots";
    public static final Supplier<ArmorItem> MAID_BOOTS = ITEMS.register(MAID_BOOTS_ID, () -> new MaidArmorItem(
            ModArmorMaterials.MAID_MATERIAL,
            ArmorItem.Type.BOOTS,
            new Item.Properties().stacksTo(1)
    ));


    //注册
    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}