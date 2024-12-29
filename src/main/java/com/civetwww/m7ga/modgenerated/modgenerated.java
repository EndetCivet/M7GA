package com.civetwww.m7ga.modgenerated;

import com.civetwww.m7ga.M7GA;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class modgenerated {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var packOutput = gen.getPackOutput();
        var helper = event.getExistingFileHelper();
        var lookup = event.getLookupProvider();
        gen.addProvider(event.includeClient(), new language.EnglishLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new language.ChineseLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new resource.ModelProvider(packOutput, helper));
        gen.addProvider(event.includeClient(), new resource.StateProvider(packOutput, helper));
    }

}

