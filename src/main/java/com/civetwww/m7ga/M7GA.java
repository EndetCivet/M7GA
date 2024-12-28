package com.civetwww.m7ga;

import com.civetwww.m7ga.blocks.ModBlocks;
import com.civetwww.m7ga.items.ModItems;
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
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;


@Mod(M7GA.MODID)
@EventBusSubscriber(bus =  EventBusSubscriber.Bus.GAME,value = Dist.CLIENT)
public class M7GA {
    public static final String MODID = "m7ga";
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent//注册玩家登录监听
    public static void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // 检查到玩家登录后，向玩家发送一条消息
        var player = event.getEntity();
        var LoggedMassages = Component.translatable("恭喜");
        player.sendSystemMessage(LoggedMassages.withStyle(ChatFormatting.AQUA));
    }

    public static DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final String TEST_TAB_ID = "mod_tab";
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TEST_TAB;

    static {
        TEST_TAB = TABS.register(TEST_TAB_ID,
                () -> CreativeModeTab.builder()
                        .title(Component.literal("Make RainbowWorld Great Again"))
                        .icon(() -> new ItemStack(ModItems.RAINBOW_PICKAXE.get()))
                        .build());
    }

    public M7GA(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        TABS.register(modEventBus);
        modEventBus.addListener(M7GA::buildCreativeTabContent);
        ModBlocks.register(modEventBus);
        modEventBus.addListener(M7GA::onGatherData);
    }

    private static void buildCreativeTabContent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == TEST_TAB.get()) {
            event.accept(ModItems.RAINBOW_PICKAXE.get());
            event.accept(ModItems.ICHOR_AXE.get());
            event.accept(ModItems.ICHOR_INGOT.get());
            event.accept(ModBlocks.ICHOR_BLOCK.get());
            event.accept(ModItems.ICHOR_ROD.get());
            event.accept(ModItems.RAINBOW_INGOT.get());
            event.accept(ModBlocks.RAINBOW_BLOCK.get());

        }
    }

    public static void onGatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var packOutput = gen.getPackOutput();
        var helper = event.getExistingFileHelper();
        gen.addProvider(event.includeClient(), new EnglishLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new ChineseLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new ModelProvider(packOutput, helper));
        gen.addProvider(event.includeClient(), new StateProvider(packOutput, helper));
    }
    public static class EnglishLanguageProvider extends LanguageProvider {
        public EnglishLanguageProvider(PackOutput gen) {
            super(gen, MODID, "en_us");
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
        }
    }

    public static class ChineseLanguageProvider extends LanguageProvider {
        public ChineseLanguageProvider(PackOutput gen) {
            super(gen, MODID,"zh_cn");
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
        }
    }

    public static class ModelProvider extends ItemModelProvider {
        public ModelProvider(PackOutput gen, ExistingFileHelper helper) {
            super(gen, MODID, helper);
        }

        @Override
        protected void registerModels() {
            this.singleTexture(ModItems.RAINBOW_PICKAXE_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(MODID, "item/" + ModItems.RAINBOW_PICKAXE_ID));
            this.singleTexture(ModItems.ICHOR_AXE_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(MODID, "item/" + ModItems.ICHOR_AXE_ID));
            this.singleTexture(ModItems.ICHOR_INGOT_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(MODID, "item/" + ModItems.ICHOR_INGOT_ID));
            this.singleTexture(ModItems.ICHOR_ROD_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(MODID, "item/" + ModItems.ICHOR_ROD_ID));
            this.singleTexture(ModItems.RAINBOW_INGOT_ID, ResourceLocation.withDefaultNamespace("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath(MODID, "item/" + ModItems.RAINBOW_INGOT_ID));


        }
    }
    public static class StateProvider extends BlockStateProvider {
        public StateProvider(PackOutput gen, ExistingFileHelper helper) {
            super(gen, MODID, helper);
        }

        @Override
        protected void registerStatesAndModels() {
            this.simpleBlock(ModBlocks.ICHOR_BLOCK.get(), this.cubeAll(ModBlocks.ICHOR_BLOCK.get()));
            this.simpleBlockItem(ModBlocks.ICHOR_BLOCK.get(), this.cubeAll(ModBlocks.ICHOR_BLOCK.get()));
            this.simpleBlock(ModBlocks.RAINBOW_BLOCK.get(), this.cubeAll(ModBlocks.RAINBOW_BLOCK.get()));
            this.simpleBlockItem(ModBlocks.RAINBOW_BLOCK.get(), this.cubeAll(ModBlocks.RAINBOW_BLOCK.get()));
        }
    }
}

