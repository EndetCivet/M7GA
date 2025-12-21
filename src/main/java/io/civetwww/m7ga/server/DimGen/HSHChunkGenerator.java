package io.civetwww.m7ga.server.DimGen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HSHChunkGenerator extends ChunkGenerator {

    public static final MapCodec<HSHChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(
            p -> p.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter(generator -> generator.biomeSource))
                    .apply(p, HSHChunkGenerator::new));

    private static final BlockState WHITE_CONCRETE = Blocks.WHITE_CONCRETE.defaultBlockState();
    private static final BlockState LIGHT_GRAY_CONCRETE = Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState();
    private static final BlockState PINK_CONCRETE = Blocks.PINK_CONCRETE.defaultBlockState();
    private static final BlockState BED_ROCK = Blocks.BEDROCK.defaultBlockState();

    public HSHChunkGenerator(BiomeSource biomeSource) {
        super(biomeSource);
    }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(@NotNull WorldGenRegion p_223043_, long p_223044_, @NotNull RandomState p_223045_, @NotNull BiomeManager p_223046_, @NotNull StructureManager p_223047_, @NotNull ChunkAccess p_223048_, @NotNull GenerationStep.Carving p_223049_) {
        // 不应用任何雕刻器
    }

    @Override
    public void buildSurface(@NotNull WorldGenRegion p_223054_, @NotNull StructureManager p_223055_, @NotNull RandomState p_223056_, @NotNull ChunkAccess p_223057_) {
        // 不构建地表
    }

    @Override
    public void spawnOriginalMobs(@NotNull WorldGenRegion p_62167_) {
        // 不生成原始生物
    }

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(@NotNull Blender p_223190_, @NotNull RandomState p_223191_, @NotNull StructureManager p_223192_, @NotNull ChunkAccess chunk) {
        // 填充噪声数据
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // 设置基岩层
                chunk.setBlockState(new BlockPos(x, chunk.getMinBuildHeight(), z), BED_ROCK, false);

                // 填充从基岩层之上到表层(Y=0)之间的区域，使用淡灰色混凝土
                for (int y = chunk.getMinBuildHeight() + 1; y < 0; y++) {
                    chunk.setBlockState(new BlockPos(x, y, z), LIGHT_GRAY_CONCRETE, false);
                }

                // 在Y=0层放置表层
                if (z == 0 || x == 15) {
                    // 区块的最北边(Z=0)或最东边(X=15)使用粉色混凝土
                    chunk.setBlockState(new BlockPos(x, 0, z), PINK_CONCRETE, false);
                } else {
                    // 其余位置使用白色混凝土作为表层
                    chunk.setBlockState(new BlockPos(x, 0, z), WHITE_CONCRETE, false);
                }
            }
        }

        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getBaseHeight(int p_223089_, int p_223090_, @NotNull Heightmap.Types p_223091_, @NotNull LevelHeightAccessor p_223092_, @NotNull RandomState p_223093_) {
        return 0;
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int p_223078_, int p_223079_, @NotNull LevelHeightAccessor p_223080_, @NotNull RandomState p_223081_) {
        BlockState[] states = new BlockState[p_223080_.getHeight()];
        for (int i = 0; i < states.length; i++) {
            int y = i + p_223080_.getMinBuildHeight();
            if (y == p_223080_.getMinBuildHeight()) {
                states[i] = BED_ROCK;
            } else if (y < 0) {
                // 在Y=0以下，全部使用淡灰色混凝土
                states[i] = LIGHT_GRAY_CONCRETE;
            } else if (y == 0) {
                // 在Y=0层，如果是区块的最北边或最东边，使用粉色混凝土，否则使用白色混凝土
                if (p_223079_ % 16 == 0 || p_223078_ % 16 == 15) {
                    states[i] = PINK_CONCRETE;
                } else {
                    states[i] = WHITE_CONCRETE;
                }
            } else {
                // Y=0以上为空气
                states[i] = Blocks.AIR.defaultBlockState();
            }
        }
        return new NoiseColumn(p_223080_.getMinBuildHeight(), states);
    }

    @Override
    public void addDebugScreenInfo(@NotNull List<String> p_223175_, @NotNull RandomState p_223176_, @NotNull BlockPos p_223177_) {
        // 不添加调试信息
    }

    @Override
    public int getGenDepth() {
        return 384;
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinY() {
        return -64;
    }
}