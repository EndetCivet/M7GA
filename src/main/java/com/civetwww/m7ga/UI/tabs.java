package com.civetwww.m7ga.UI;

import com.civetwww.m7ga.M7GA;
import com.civetwww.m7ga.blocks.ModBlocks;
import com.civetwww.m7ga.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class tabs {
    public static DeferredRegister<net.minecraft.world.item.CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, M7GA.MODID);
    public static final String TEST_TAB_ID = "mod_tabs";
    public static final DeferredHolder<net.minecraft.world.item.CreativeModeTab, net.minecraft.world.item.CreativeModeTab> TEST_TAB;

    static {
        TEST_TAB = TABS.register(TEST_TAB_ID,
                () -> net.minecraft.world.item.CreativeModeTab.builder()
                        .title(Component.literal("Make RainbowWorld Great Again"))
                        .icon(() -> new ItemStack(ModItems.RAINBOW_PICKAXE.get()))
                        .build());
    }

    public static void buildCreativeTabContent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == TEST_TAB.get()) {
            event.accept(ModItems.RAINBOW_PICKAXE.get());
            event.accept(ModItems.ICHOR_AXE.get());
            event.accept(ModItems.ICHOR_INGOT.get());
            event.accept(ModBlocks.ICHOR_BLOCK.get());
            event.accept(ModItems.ICHOR_ROD.get());
            event.accept(ModItems.RAINBOW_INGOT.get());
            event.accept(ModBlocks.RAINBOW_BLOCK.get());
            event.accept(ModBlocks.RAINBOW_GRASS.get());
        }
    }

}
