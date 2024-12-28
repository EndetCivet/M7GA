package com.civetwww.modtest1;

import com.civetwww.modtest1.items.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;


@Mod(ModTest1.MODID)
@EventBusSubscriber(bus =  EventBusSubscriber.Bus.GAME,value = Dist.CLIENT)
public class ModTest1 {
    public static final String MODID = "modtest1";
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent//注册玩家登录监听
    public static void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // 检查到玩家登录后，向玩家发送一条消息
        var player = event.getEntity();
        var LoggedMassages = Component.translatable("恭喜");
        player.sendSystemMessage(LoggedMassages.withStyle(ChatFormatting.AQUA));
    }

    public static DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final String TEST_TAB_ID = "test_tab";
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TEST_TAB;

    static {
        TEST_TAB = TABS.register(TEST_TAB_ID,
                () -> CreativeModeTab.builder()
                        .title(Component.literal("tab title"))
                        .icon(() -> new ItemStack(ModItems.PICKAXE.get()))
                        .build());
    }

    public ModTest1(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.ITEMS.register(modEventBus);
        TABS.register(modEventBus);
        modEventBus.addListener(ModTest1::buildCreativeTabContent);
        modEventBus.addListener(ModTest1::onGatherData);
    }

    private static void buildCreativeTabContent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == TEST_TAB.get()) {
            event.accept(ModItems.PICKAXE.get());
        }
    }

    public static void onGatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var packOutput = gen.getPackOutput();
        var helper = event.getExistingFileHelper();
        gen.addProvider(event.includeClient(), new EnglishLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new ChineseLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new ModelProvider(packOutput, helper));
    }
    public static class EnglishLanguageProvider extends LanguageProvider {
        public EnglishLanguageProvider(PackOutput gen) {
            super(gen, "modtest1", "en_us");
        }

        @Override
        protected void addTranslations() {
            this.add(ModItems.PICKAXE.get(), "Rainbow Pickaxe");

        }
    }

    public static class ChineseLanguageProvider extends LanguageProvider {
        public ChineseLanguageProvider(PackOutput gen) {
            super(gen, "modtest1", "zh_cn");
        }

        @Override
        protected void addTranslations() {
            this.add(ModItems.PICKAXE.get(), "彩虹镐");

        }
    }

    public static class ModelProvider extends ItemModelProvider {
        public ModelProvider(PackOutput gen, ExistingFileHelper helper) {
            super(gen, "modtest1", helper);
        }

        @Override
        protected void registerModels() {
            this.singleTexture(ModItems.PICKAXE_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath("modtest1", "item/" + ModItems.PICKAXE_ID));
        }
    }
}

