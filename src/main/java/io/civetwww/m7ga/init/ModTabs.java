package io.civetwww.m7ga.init;

import io.civetwww.m7ga.M7GA;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static io.civetwww.m7ga.common.blocks.ModBlocks.*;
import static io.civetwww.m7ga.common.items.ModItems.*;


public class ModTabs {
    public static DeferredRegister<net.minecraft.world.item.CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, M7GA.MODID);
    public static final String TEST_TAB_ID = "mod_tabs";
    public static final DeferredHolder<net.minecraft.world.item.CreativeModeTab, net.minecraft.world.item.CreativeModeTab> TEST_TAB;

    static {
        TEST_TAB = TABS.register(TEST_TAB_ID,
                () -> net.minecraft.world.item.CreativeModeTab.builder()
                        .title(Component.literal("Make RainbowWorld Great Again"))
                        .icon(() -> new ItemStack(RAINBOW_GRASS.get()))
                        .build());
    }

    public static void buildCreativeTabContent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == TEST_TAB.get()) {
            event.accept(RAINBOW_PICKAXE.get());
            event.accept(ICHOR_AXE.get());

            event.accept(RAINBOW_GRASS.get());

            event.accept(ICHOR_INGOT.get());
            event.accept(ICHOR_ROD.get());
            event.accept(ICHOR_CLOTH.get());

            event.accept(RAINBOW_INGOT.get());
            event.accept(RAINBOW_CLOTH.get());

            event.accept(RAINBOW_CORE.get());
            event.accept(ICHOR_CORE.get());
            event.accept(MEGA_TEMPLATE.get());

            event.accept(MAID_HEADGEAR.get());
            event.accept(MAID_SUIT.get());
            event.accept(MAID_SKIRT.get());
            event.accept(MAID_BOOTS.get());

            event.accept(ICHOR_HELMET.get());
            event.accept(ICHOR_CHESTPLATE.get());
            event.accept(ICHOR_LEGGINGS.get());
            event.accept(ICHOR_BOOTS.get());
        }
    }

}
