package io.civetwww.m7ga.modgenerated;

import io.civetwww.m7ga.M7GA;
import io.civetwww.m7ga.blocks.ModBlocks;
import io.civetwww.m7ga.items.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;



public class Language {

    public static class ChineseLanguageProvider extends LanguageProvider {
        public ChineseLanguageProvider(PackOutput gen) {
            super(gen, M7GA.MODID,"zh_cn");
        }

        @Override
        protected void addTranslations() {
            this.add(ModItems.RAINBOW_PICKAXE.get(), "彩虹镐");
            this.add(ModItems.ICHOR_AXE.get(),"觉醒灵宝斧");

            this.add(ModBlocks.RAINBOW_GRASS.get(),"七彩草！");

            this.add(ModItems.ICHOR_INGOT.get(),"灵宝锭");
            this.add(ModItems.ICHOR_ROD.get(),"灵宝棒");
            this.add(ModItems.ICHOR_CLOTH.get(),"灵布");

            this.add(ModItems.RAINBOW_INGOT.get(),"彩虹锭");
            this.add(ModItems.RAINBOW_CLOTH.get(),"七布");


            this.add(ModItems.RAINBOW_CORE.get(),"七核");
            this.add(ModItems.ICHOR_CORE.get(),"通天核心");
            this.add(ModItems.MEGA_TEMPLATE.get(),"觉醒模板");

            this.add(ModItems.RAINBOW_HELMET.get(), "魔女头饰");
            this.add(ModItems.RAINBOW_CHESTPLATE.get(), "魔女围巾");
            this.add(ModItems.RAINBOW_LEGGINGS.get(), "魔女短裙");
            this.add(ModItems.RAINBOW_BOOTS.get(), "魔女白丝");

            this.add(ModItems.ICHOR_HELMET.get(), "通天灵宝·明");
            this.add(ModItems.ICHOR_CHESTPLATE.get(), "通天灵宝·霄");
            this.add(ModItems.ICHOR_LEGGINGS.get(), "通天灵宝·疾");
            this.add(ModItems.ICHOR_BOOTS.get(), "通天灵宝·跃");


        }
    }
}
