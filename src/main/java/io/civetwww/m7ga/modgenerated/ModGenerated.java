package io.civetwww.m7ga.modgenerated;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class ModGenerated {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var packOutput = gen.getPackOutput();
        var helper = event.getExistingFileHelper();
        var lookup = event.getLookupProvider();
        gen.addProvider(event.includeClient(), new Language.ChineseLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new ModResources.ModelProvider(packOutput, helper));
        //gen.addProvider(event.includeClient(), new ModResources.StateProvider(packOutput, helper));
    }

}

