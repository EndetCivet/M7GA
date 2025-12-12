package io.civetwww.m7ga.event.item;

import io.civetwww.m7ga.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.joml.Vector3f;

public class RainbowGrass {
    //骨粉催生彩虹草
    @SubscribeEvent
    public static void onBoneMealUsed(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        ItemStack itemInHand = event.getItemStack();

        // 检查是否是对彩虹草使用骨粉
        if (level.getBlockState(pos).is(ModBlocks.RAINBOW_GRASS.get()) && itemInHand.is(Items.BONE_MEAL)) {
            // 确保在服务端执行
            if (level instanceof ServerLevel serverLevel) {
                RandomSource random = serverLevel.random;
                //音效
                serverLevel.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                // 在目标彩虹草上播放催生粒子效果
                spawnRainbowParticles(serverLevel, random, pos);

                // 生成1-3个彩虹草
                int count = random.nextInt(3) + 1; // 1-3个
                int successCount = 0;

                // 在以目标方块为中心的7x7x1范围内寻找合适的位置
                for (int i = 0; i < count * 10; i++) { // 尝试10倍次数确保找到足够位置
                    int offsetX = random.nextInt(7) - 3; // -3到3，形成7x7的X范围
                    int offsetY = 0; // 保持在同一高度
                    int offsetZ = random.nextInt(7) - 3; // -3到3，形成7x7的Z范围
                    BlockPos newPos = pos.offset(offsetX, offsetY, offsetZ);

                    // 检查位置是否适合生成彩虹草（下方是草方块，当前位置是空气）
                    if (level.isEmptyBlock(newPos) && level.getBlockState(newPos.below()).is(Blocks.GRASS_BLOCK)) {
                        serverLevel.setBlock(newPos, ModBlocks.RAINBOW_GRASS.get().defaultBlockState(), 3);

                        // 在新生成的彩虹草上播放催生粒子效果
                        spawnRainbowParticles(serverLevel, random, newPos);

                        successCount++;

                        if (successCount >= count) {
                            break;
                        }
                    }
                }

                // 消耗骨粉
                if (!player.isCreative()) {
                    itemInHand.shrink(1);
                }

            }
        }
    }

    // 生成彩虹渐变粒子效果的方法
    private static void spawnRainbowParticles(ServerLevel serverLevel, RandomSource random, BlockPos pos) {
        // 添加彩虹渐变粒子效果
        for (int j = 0; j < 10; j++) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
            double y = pos.getY() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 2.0;

            // 生成彩虹渐变颜色
            float hue = (float) j / 10.0f; // 0.0 to 1.0
            float currentR = 0.0f;
            float currentG = 0.0f;
            float currentB = 0.0f;
            float nextR = 0.0f;
            float nextG = 0.0f;
            float nextB = 0.0f;

            // HSL to RGB转换，创建彩虹色
            float c = 1.0f; // 饱和度
            float l = 0.8f; // 亮度，提高到0.8f使粒子更亮

            // 计算当前颜色
            float currentX = c * (1.0f - Math.abs((2.0f * hue) % 2.0f - 1.0f));
            float currentM = l - c / 2.0f;

            if (hue >= 0.0f && hue < 1.0f / 6.0f) {
                currentR = c + currentM;
                currentG = currentX + currentM;
                currentB = currentM;
            } else if (hue >= 1.0f / 6.0f && hue < 2.0f / 6.0f) {
                currentR = currentX + currentM;
                currentG = c + currentM;
                currentB = currentM;
            } else if (hue >= 2.0f / 6.0f && hue < 3.0f / 6.0f) {
                currentR = currentM;
                currentG = c + currentM;
                currentB = currentX + currentM;
            } else if (hue >= 3.0f / 6.0f && hue < 4.0f / 6.0f) {
                currentR = currentM;
                currentG = currentX + currentM;
                currentB = c + currentM;
            } else if (hue >= 4.0f / 6.0f && hue < 5.0f / 6.0f) {
                currentR = currentX + currentM;
                currentG = currentM;
                currentB = c + currentM;
            } else {
                currentR = c + currentM;
                currentG = currentM;
                currentB = currentX + currentM;
            }

            // 计算下一个颜色（用于平滑过渡）
            float nextHue = (hue + 0.1f) % 1.0f;
            float nextX = c * (1.0f - Math.abs((2.0f * nextHue) % 2.0f - 1.0f));
            float nextM = l - c / 2.0f;

            if (nextHue >= 0.0f && nextHue < 1.0f / 6.0f) {
                nextR = c + nextM;
                nextG = nextX + nextM;
                nextB = nextM;
            } else if (nextHue >= 1.0f / 6.0f && nextHue < 2.0f / 6.0f) {
                nextR = nextX + nextM;
                nextG = c + nextM;
                nextB = nextM;
            } else if (nextHue >= 2.0f / 6.0f && nextHue < 3.0f / 6.0f) {
                nextR = nextM;
                nextG = c + nextM;
                nextB = nextX + nextM;
            } else if (nextHue >= 3.0f / 6.0f && nextHue < 4.0f / 6.0f) {
                nextR = nextM;
                nextG = nextX + nextM;
                nextB = c + nextM;
            } else if (nextHue >= 4.0f / 6.0f && nextHue < 5.0f / 6.0f) {
                nextR = nextX + nextM;
                nextG = nextM;
                nextB = c + nextM;
            } else {
                nextR = c + nextM;
                nextG = nextM;
                nextB = nextX + nextM;
            }

            // 创建彩虹渐变粒子
            DustColorTransitionOptions dustTransitionOptions = new DustColorTransitionOptions(
                    new Vector3f(currentR, currentG, currentB), // 起始颜色
                    new Vector3f(nextR, nextG, nextB), // 结束颜色
                    1.0f // 大小
            );

            // 发送粒子
            serverLevel.sendParticles(dustTransitionOptions, x, y, z, 1, 0.0, 0.0, 0.0, 0.1);
        }
    }

}
