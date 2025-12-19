package io.civetwww.m7ga.client;

import io.civetwww.m7ga.client.renderer.armor.MaidArmorRenderer;
import io.civetwww.m7ga.common.items.ModItems;

import java.lang.reflect.Field;
import java.util.Map;

public class ArmorRendererRegistry {
    
    @SuppressWarnings("unchecked")
    public static void registerArmorRenderers() {
        try {
            // 使用反射访问 ClientExtensionsManager.ITEM_EXTENSIONS
            Class<?> clientExtensionsManagerClass = Class.forName("net.neoforged.neoforge.client.extensions.common.ClientExtensionsManager");
            Field itemExtensionsField = clientExtensionsManagerClass.getDeclaredField("ITEM_EXTENSIONS");
            itemExtensionsField.setAccessible(true);
            
            Map<Object, Object> itemExtensions = (Map<Object, Object>) itemExtensionsField.get(null);
            
            // 注册女仆护甲渲染器
            MaidArmorRenderer renderer = new MaidArmorRenderer();
            itemExtensions.put(ModItems.MAID_HEADGEAR.get(), renderer);
            itemExtensions.put(ModItems.MAID_SUIT.get(), renderer);
            itemExtensions.put(ModItems.MAID_SKIRT.get(), renderer);
            itemExtensions.put(ModItems.MAID_BOOTS.get(), renderer);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to register armor renderers", e);
        }
    }
}