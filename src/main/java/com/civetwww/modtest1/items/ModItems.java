package com.civetwww.modtest1.items;

import com.civetwww.modtest1.ModTest1;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.Items.createItems(ModTest1.MODID);
    public static final String PICKAXE_ID = "rainbow_pickaxe";
    public static final DeferredItem<Item> PICKAXE = registerItem(PICKAXE_ID,()-> new Item(new Item.Properties().fireResistant()));

    public static DeferredItem<Item> registerItem(String name, Supplier<Item> itemSupplier) {
        return (DeferredItem<Item>) ITEMS.register(name,itemSupplier);
    }
}
