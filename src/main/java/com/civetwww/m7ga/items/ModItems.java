package com.civetwww.m7ga.items;

import com.civetwww.m7ga.M7GA;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModItems {
    //注册器
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.Items.createItems(M7GA.MODID);

    public static DeferredItem<Item> registerItem(String name, Supplier<Item> itemSupplier) {
        return (DeferredItem<Item>) ITEMS.register(name,itemSupplier);
    }
    //等级标签
    public static final Tier CUSTOM_TIER = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            77777,
            27f,
            7f,
            27,
            //
            () -> Ingredient.of(Tags.Items.NETHER_STARS)
    );
    //灵宝斧
    public static final String ICHOR_AXE_ID = "ichor_axe";
    public static final Supplier<AxeItem> ICHOR_AXE = ITEMS.register(ICHOR_AXE_ID, () -> new AxeItem(
            CUSTOM_TIER,
            new Item.Properties().attributes(
                    AxeItem.createAttributes(
                            CUSTOM_TIER,
                            27,
                            0f
                            )
            )
    ));

    //彩虹镐
    public static final String RAINBOW_PICKAXE_ID = "rainbow_pickaxe";
    public static final Supplier<PickaxeItem> RAINBOW_PICKAXE = ITEMS.register(RAINBOW_PICKAXE_ID,()-> new PickaxeItem(
            CUSTOM_TIER,
            new Item.Properties().attributes(
                    PickaxeItem.createAttributes(
                            CUSTOM_TIER,
                            7,
                            7f
                            )
            )
    ));
    //灵宝锭和灵宝棒
    public static final String ICHOR_INGOT_ID = "ichor_ingot";
    public static final Supplier<Item> ICHOR_INGOT = ITEMS.register(ICHOR_INGOT_ID,()-> new Item(new Item.Properties().fireResistant()));
    public static final String ICHOR_ROD_ID = "ichor_rod";
    public static final Supplier<Item> ICHOR_ROD = ITEMS.register(ICHOR_ROD_ID,()-> new Item(new Item.Properties().fireResistant()));

    //彩虹锭
    public static final String RAINBOW_INGOT_ID = "rainbow_ingot";
    public static final Supplier<Item> RAINBOW_INGOT = ITEMS.register(RAINBOW_INGOT_ID,()-> new Item(new Item.Properties().fireResistant()));

    //注册
    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}