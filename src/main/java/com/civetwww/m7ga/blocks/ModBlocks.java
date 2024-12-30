package com.civetwww.m7ga.blocks;

import com.civetwww.m7ga.M7GA;
import com.civetwww.m7ga.items.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(M7GA.MODID);
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static final String ICHOR_BLOCK_ID = "ichor_block";
    public static final DeferredBlock<Block> ICHOR_BLOCK = registerBlock(ICHOR_BLOCK_ID,
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .requiresCorrectToolForDrops()
                    .strength(17.0F, 1200.0F)
                    .sound(SoundType.NETHERITE_BLOCK)
                 ));
    public static final String RAINBOW_BLOCK_ID = "rainbow_block";
    public static final DeferredBlock<Block> RAINBOW_BLOCK = registerBlock(RAINBOW_BLOCK_ID,
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .requiresCorrectToolForDrops()
                    .strength(17.0F, 1200.0F)
                    .sound(SoundType.NETHERITE_BLOCK)
            ));
    public static final String RAINBOW_GRASS_ID = "rainbow_grass";
    public static final DeferredBlock<Block> RAINBOW_GRASS = registerBlock(RAINBOW_GRASS_ID,
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .replaceable()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XYZ)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
                    .lightLevel(state -> 7)
            ));




    private static  <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }


}
