package io.civetwww.m7ga.server.DimGen;

import com.mojang.serialization.MapCodec;
import io.civetwww.m7ga.M7GA;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class HomeSweetHome {
    // 维度类型键（用于代码中引用，具体配置在JSON中）
    public static final ResourceKey<DimensionType> HOME_SWEET_HOME_DIM_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    ResourceLocation.fromNamespaceAndPath(M7GA.MODID, "home_sweet_home"));

    // 维度键（用于传送等操作）
    public static final ResourceKey<Level> HOME_SWEET_HOME_DIM =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(M7GA.MODID, "home_sweet_home"));

    // 维度类型注册器（如果通过JSON配置，这个可能不需要实际注册内容）
    public static final DeferredRegister<DimensionType> DIMENSION_TYPES =
            DeferredRegister.create(Registries.DIMENSION_TYPE, M7GA.MODID);

    // 区块生成器注册器
    public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS =
            DeferredRegister.create(Registries.CHUNK_GENERATOR, M7GA.MODID);

    // 注册自定义区块生成器
    public static final Supplier<MapCodec<? extends ChunkGenerator>> HSH_CHUNK_GENERATOR =
            CHUNK_GENERATORS.register("hsh_chunk_generator",
                    () -> HSHChunkGenerator.CODEC); // 确保HSHChunkGenerator.CODEC存在且为public

    public static void register(IEventBus modEventBus) {
        DIMENSION_TYPES.register(modEventBus);
        CHUNK_GENERATORS.register(modEventBus);
    }
}