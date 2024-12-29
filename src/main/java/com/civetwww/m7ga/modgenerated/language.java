package com.civetwww.m7ga.modgenerated;

import com.civetwww.m7ga.M7GA;
import com.civetwww.m7ga.blocks.ModBlocks;
import com.civetwww.m7ga.items.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class language {
    public static class EnglishLanguageProvider extends LanguageProvider {
        public EnglishLanguageProvider(PackOutput gen) {
            super(gen, M7GA.MODID, "en_us");
        }

        @Override
        protected void addTranslations() {
            this.add(ModItems.RAINBOW_PICKAXE.get(), "Rainbow Pickaxe");
            this.add(ModBlocks.ICHOR_BLOCK.get(), "Ichor Block");
            this.add(ModItems.ICHOR_AXE.get(),"Ichor Axe");
            this.add(ModItems.ICHOR_INGOT.get(),"Ichor Ingot");
            this.add(ModItems.ICHOR_ROD.get(),"Ichor Rod");
            this.add(ModItems.RAINBOW_INGOT.get(),"Rainbow Ingot");
            this.add(ModBlocks.RAINBOW_BLOCK.get(),"RainBow Block");
            this.add(ModBlocks.RAINBOW_GRASS.get(),"Rainbow Cao!");
        }
    }

    public static class ChineseLanguageProvider extends LanguageProvider {
        public ChineseLanguageProvider(PackOutput gen) {
            super(gen, M7GA.MODID,"zh_cn");
        }

        @Override
        protected void addTranslations() {
            this.add(ModItems.RAINBOW_PICKAXE.get(), "彩虹镐");
            this.add(ModBlocks.ICHOR_BLOCK.get(), "灵宝块");
            this.add(ModItems.ICHOR_AXE.get(),"觉醒灵宝斧");
            this.add(ModItems.ICHOR_INGOT.get(),"灵宝锭");
            this.add(ModItems.ICHOR_ROD.get(),"灵宝棒");
            this.add(ModItems.RAINBOW_INGOT.get(),"彩虹锭");
            this.add(ModBlocks.RAINBOW_BLOCK.get(),"彩虹块");
            this.add(ModBlocks.RAINBOW_GRASS.get(),"七彩草！");
        }
    }
}
