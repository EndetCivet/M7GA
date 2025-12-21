package io.civetwww.m7ga.common.items.tools;


import io.civetwww.m7ga.common.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RainbowPickaxe extends PickaxeItem {
    public RainbowPickaxe(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entity) {
        boolean result = super.mineBlock(stack, level, state, pos, entity);

        // 检查是否是玩家并且蹲下，蹲下则不执行连锁采集效果
        boolean isSneaking = entity instanceof Player player && player.isShiftKeyDown();
        if (isSneaking) {
            return result;
        }

        // 检查是否是矿物方块（BlockTags.MINEABLE_WITH_PICKAXE和Tags.Blocks.ORES）
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) && state.is(Tags.Blocks.ORES)) {
            // 找到并破坏相连的所有矿物方块
            List<BlockPos> oreBlocks = findConnectedOres(level, pos, state);

            // 破坏找到的所有矿物方块
            for (BlockPos blockPos : oreBlocks) {
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.is(BlockTags.MINEABLE_WITH_PICKAXE) && blockState.is(Tags.Blocks.ORES)) {
                    // 使用实体破坏方块，这样可以正确消耗耐久度
                    level.destroyBlock(blockPos, true, entity);
                }
            }
        }
        // 检查是否是七彩草方块
        else if (state.getBlock() == ModBlocks.RAINBOW_GRASS.get()) {
            // 找到并破坏相连的七彩草
            List<BlockPos> grassBlocks = findConnectedGrass(level, pos, state);

            // 破坏找到的所有七彩草方块
            for (BlockPos blockPos : grassBlocks) {
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.getBlock() == ModBlocks.RAINBOW_GRASS.get()) {
                    // 使用实体破坏方块，这样可以正确消耗耐久度
                    level.destroyBlock(blockPos, true, entity);
                }
            }
        }

        return result;
    }

    // 查找相连的矿物方块
    private List<BlockPos> findConnectedOres(Level level, BlockPos startPos, BlockState startState) {
        List<BlockPos> oreBlocks = new ArrayList<>();
        List<BlockPos> toCheck = new ArrayList<>();

        // 开始位置加入待检查列表
        toCheck.add(startPos);
        oreBlocks.add(startPos);

        // 简单的BFS查找相连的同一种矿物方块
        while (!toCheck.isEmpty()) {
            BlockPos currentPos = toCheck.removeFirst();

            // 检查6个方向
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = currentPos.relative(direction);
                BlockState neighborState = level.getBlockState(neighborPos);

                // 检查是否是可挖掘的矿物方块，并且与开始方块相同，并且不在已找到的列表中
                if (neighborState.is(BlockTags.MINEABLE_WITH_PICKAXE) &&
                        neighborState.is(Tags.Blocks.ORES) &&
                        neighborState.getBlock() == startState.getBlock() &&
                        !oreBlocks.contains(neighborPos)) {

                    // 添加到结果列表和待检查列表
                    oreBlocks.add(neighborPos);
                    toCheck.add(neighborPos);
                }
            }
        }

        return oreBlocks;
    }

    // 查找相连的七彩草方块
    private List<BlockPos> findConnectedGrass(Level level, BlockPos startPos, BlockState startState) {
        List<BlockPos> grassBlocks = new ArrayList<>();
        List<BlockPos> toCheck = new ArrayList<>();

        // 开始位置加入待检查列表
        toCheck.add(startPos);
        grassBlocks.add(startPos);

        // 简单的BFS查找相连的七彩草方块
        while (!toCheck.isEmpty()) {
            BlockPos currentPos = toCheck.removeFirst();

            // 检查6个方向
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = currentPos.relative(direction);
                BlockState neighborState = level.getBlockState(neighborPos);

                // 检查是否是七彩草方块，并且不在已找到的列表中
                if (neighborState.getBlock() == ModBlocks.RAINBOW_GRASS.get() &&
                        !grassBlocks.contains(neighborPos)) {

                    // 添加到结果列表和待检查列表
                    grassBlocks.add(neighborPos);
                    toCheck.add(neighborPos);
                }
            }
        }

        return grassBlocks;
    }
}
