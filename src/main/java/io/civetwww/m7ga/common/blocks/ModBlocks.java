package io.civetwww.m7ga.common.blocks;

import io.civetwww.m7ga.M7GA;
import io.civetwww.m7ga.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(M7GA.MODID);
    private static <T extends Block> DeferredBlock<T> registerBlock(Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(ModBlocks.RAINBOW_GRASS_ID, block);
        registerBlockItem(toReturn);
        return toReturn;
    }

    public static final String RAINBOW_GRASS_ID = "rainbow_grass";
    public static final DeferredBlock<Block> RAINBOW_GRASS = registerBlock(
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
            ) {
                @Override
                public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
                    // 检查下方是否是草方块
                    BlockState belowBlock = world.getBlockState(pos.below());
                    return belowBlock.is(Blocks.GRASS_BLOCK);
                }
            });




    private static  <T extends Block> void registerBlockItem(DeferredBlock<T> block) {
        ModItems.ITEMS.register(ModBlocks.RAINBOW_GRASS_ID, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }


}
