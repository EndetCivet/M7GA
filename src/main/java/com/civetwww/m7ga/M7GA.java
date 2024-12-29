package com.civetwww.m7ga;

import com.civetwww.m7ga.UI.tabs;
import com.civetwww.m7ga.blocks.ModBlocks;
import com.civetwww.m7ga.items.ModItems;
import com.civetwww.m7ga.modgenerated.modgenerated;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


@Mod(M7GA.MODID)
@EventBusSubscriber(bus =  EventBusSubscriber.Bus.GAME,value = Dist.CLIENT)
public class M7GA {
    public static final String MODID = "m7ga";

    @SubscribeEvent//注册玩家登录监听
    public static void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // 检查到玩家登录后，向玩家发送一条消息
        var player = event.getEntity();
        var LoggedMassages = Component.translatable("恭喜");
        player.sendSystemMessage(LoggedMassages.withStyle(ChatFormatting.AQUA));
    }
    private void onServerStarting(ServerStartingEvent event)
    {
        // 当服务器启动时执行
        System.out.println("七彩境界！启动！");
        System.out.println("Make RainbowWorld Great Again!");
    }





    public M7GA(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        tabs.TABS.register(modEventBus);
        modEventBus.addListener(tabs::buildCreativeTabContent);
        ModBlocks.register(modEventBus);
        modEventBus.addListener(modgenerated::gatherData);
    }

}

