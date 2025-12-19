package io.civetwww.m7ga.common.items.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

import static io.civetwww.m7ga.common.blocks.ModBlocks.RAINBOW_GRASS;

public class IchorAxe extends AxeItem {
    public IchorAxe(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        boolean result = super.mineBlock(stack, level, state, pos, entity);

        // 检查是否是玩家并且蹲下，蹲下则不执行连锁采集效果
        boolean isSneaking = entity instanceof Player player && player.isShiftKeyDown();
        if (isSneaking) {
            return result;
        }

        // 检查是否是木头方块
        if (state.is(BlockTags.LOGS)) {
            // 找到并破坏整棵树
            List<BlockPos> treeBlocks = findTreeBlocks(level, pos, state);

            // 破坏找到的所有木头方块
            for (BlockPos blockPos : treeBlocks) {
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.is(BlockTags.LOGS)) {
                    // 使用实体破坏方块，这样可以正确消耗耐久度
                    level.destroyBlock(blockPos, true, entity);
                }
            }
        }
        // 检查是否是七彩草方块
        else if (state.getBlock() == RAINBOW_GRASS.get()) {
            // 找到并破坏相连的七彩草
            List<BlockPos> grassBlocks = findConnectedGrass(level, pos, state);

            // 破坏找到的所有七彩草方块
            for (BlockPos blockPos : grassBlocks) {
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.getBlock() == RAINBOW_GRASS.get()) {
                    // 使用实体破坏方块，这样可以正确消耗耐久度
                    level.destroyBlock(blockPos, true, entity);
                }
            }
        }

        return result;
    }

    // 查找整棵树的所有木头方块
    private List<BlockPos> findTreeBlocks(Level level, BlockPos startPos, BlockState startState) {
        List<BlockPos> treeBlocks = new ArrayList<>();
        List<BlockPos> toCheck = new ArrayList<>();

        // 开始位置加入待检查列表
        toCheck.add(startPos);
        treeBlocks.add(startPos);

        // 简单的BFS查找相连的木头方块
        while (!toCheck.isEmpty()) {
            BlockPos currentPos = toCheck.removeFirst();

            // 检查6个方向
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = currentPos.relative(direction);
                BlockState neighborState = level.getBlockState(neighborPos);

                // 检查是否是同一种木头方块，并且不在已找到的列表中
                if (neighborState.is(BlockTags.LOGS) &&
                        neighborState.getBlock() == startState.getBlock() &&
                        !treeBlocks.contains(neighborPos)) {

                    // 添加到结果列表和待检查列表
                    treeBlocks.add(neighborPos);
                    toCheck.add(neighborPos);
                }
            }
        }

        return treeBlocks;
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
                if (neighborState.getBlock() == RAINBOW_GRASS.get() &&
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
