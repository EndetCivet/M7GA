package io.civetwww.m7ga.event.player;

import io.civetwww.m7ga.server.PremiumManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static io.civetwww.m7ga.M7GA.MODID;

@EventBusSubscriber(modid = MODID)
public class PremiumEvents {

    // 飞行能力的属性修饰符ID
    private static final ResourceLocation PREMIUM_FLIGHT = ResourceLocation.fromNamespaceAndPath(MODID, "premium_flight");

    /**
     * 玩家加入世界时授予飞行权限
     */
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            String playerName = player.getName().getString();

            // 检查玩家是否有权限
            if (PremiumManager.isPremiumPlayer(playerName)) {
                grantFlightPermission(player);
                player.sendSystemMessage(Component.literal("这里是塔台,允许起飞!").withStyle(ChatFormatting.GREEN));
            }
        }
    }

    /**
     * 玩家离开世界时移除飞行权限
     */
    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            String playerName = player.getName().getString();

            // 检查玩家是否有权限
            if (PremiumManager.isPremiumPlayer(playerName)) {
                revokeFlightPermission(player);
            }
        }
    }

    /**
     * 玩家重生时重新授予飞行权限
     */
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            String playerName = player.getName().getString();

            // 检查玩家是否有权限
            if (PremiumManager.isPremiumPlayer(playerName)) {
                // 延迟执行，确保玩家完全重生
                player.getServer().execute(() -> {
                    grantFlightPermission(player);
                });
            }
        }
    }

    /**
     * 玩家切换维度时保持飞行权限
     */
    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            String playerName = player.getName().getString();

            // 检查玩家是否有权限
            if (PremiumManager.isPremiumPlayer(playerName)) {
                // 延迟执行，确保维度切换完成
                player.getServer().execute(() -> {
                    grantFlightPermission(player);
                });
            }
        }
    }

    /**
     * 授予玩家飞行权限
     */
    private static void grantFlightPermission(ServerPlayer player) {
        // 确保是服务器玩家
        if (player != null && !player.level().isClientSide()) {
            // 添加飞行属性修饰符
            if (player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT) != null &&
                    !player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT).hasModifier(PREMIUM_FLIGHT)) {
                player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT).addPermanentModifier(
                        new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                                PREMIUM_FLIGHT,
                                1.0,
                                net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE
                        )
                );
            }

            // 更新玩家能力
            player.onUpdateAbilities();
        }
    }

    /**
     * 移除玩家飞行权限
     */
    private static void revokeFlightPermission(ServerPlayer player) {
        // 确保是服务器玩家
        if (player != null && !player.level().isClientSide()) {
            // 移除飞行属性修饰符
            if (player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT) != null &&
                    player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT).hasModifier(PREMIUM_FLIGHT)) {
                player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT).removeModifier(PREMIUM_FLIGHT);
            }

            // 如果不是创造模式或旁观者模式，取消飞行
            if (!player.isCreative() && !player.isSpectator()) {
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
            }
        }
    }

    /**
     * 实时更新玩家飞行权限（可用于命令或其他事件触发）
     */
    public static void updatePlayerFlightPermission(ServerPlayer player) {
        String playerName = player.getName().getString();

        if (PremiumManager.isPremiumPlayer(playerName)) {
            grantFlightPermission(player);
            player.sendSystemMessage(Component.literal("飞行权限已更新!").withStyle(ChatFormatting.GREEN));
        } else {
            revokeFlightPermission(player);
            player.sendSystemMessage(Component.literal("飞行权限已移除!").withStyle(ChatFormatting.RED));
        }
    }
}