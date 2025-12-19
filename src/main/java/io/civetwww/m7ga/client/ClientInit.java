package io.civetwww.m7ga.client;


import io.civetwww.m7ga.client.model.LayerDefinitions;
import io.civetwww.m7ga.client.model.armor.ArmorModels;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

public class ClientInit {
    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ClientInit::registerLayerDefinitions);
        modEventBus.addListener(ClientInit::addLayersEvent);
        modEventBus.addListener(ClientInit::registerArmorRenderers);
    }
    
    public static void registerArmorRenderers(RegisterClientReloadListenersEvent event) {
        // 在客户端重载监听器注册事件中注册护甲渲染器
        ArmorRendererRegistry.registerArmorRenderers();
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        LayerDefinitions.init(event::registerLayerDefinition);
    }

    public static void addLayersEvent(EntityRenderersEvent.AddLayers event) {
        addLayers(event.getContext());
    }

    public static void addLayers(EntityRendererProvider.Context context) {
        ArmorModels.init(context);
    }
}