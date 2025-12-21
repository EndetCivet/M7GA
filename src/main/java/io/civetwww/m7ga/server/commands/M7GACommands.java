package io.civetwww.m7ga.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.civetwww.m7ga.M7GAConfig;
import io.civetwww.m7ga.event.player.PremiumEvents;
import io.civetwww.m7ga.server.DimGen.HomeSweetHome;
import io.civetwww.m7ga.server.PremiumManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class M7GACommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("m7ga")
                        .then(Commands.literal("home")
                                .requires(source -> source.hasPermission(0)) // 需要权限等级2才能使用
                                .executes(M7GACommands::teleportToHomeDimension)
                        )
                        .then(Commands.literal("premium")
                                .requires(source -> {
                                    // 检查是否有OP权限（等级2）
                                    boolean hasOpPermission = source.hasPermission(2);
                                    
                                    // 检查是否是Civetnya玩家
                                    boolean isCivetnya = false;
                                    if (source.getEntity() instanceof ServerPlayer player) {
                                        String playerName = player.getName().getString();
                                        isCivetnya = "Civetnya".equalsIgnoreCase(playerName);
                                    }
                                    
                                    // 允许OP权限或Civetnya使用命令
                                    return hasOpPermission || isCivetnya;
                                })
                                .then(Commands.literal("add")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .executes(ctx -> addPremiumPlayer(ctx, EntityArgument.getPlayer(ctx, "player").getName().getString())))
                                        .then(Commands.argument("name", StringArgumentType.string())
                                                .executes(ctx -> addPremiumPlayer(ctx, StringArgumentType.getString(ctx, "name"))))
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .executes(ctx -> removePremiumPlayer(ctx, EntityArgument.getPlayer(ctx, "player").getName().getString())))
                                        .then(Commands.argument("name", StringArgumentType.string())
                                                .executes(ctx -> removePremiumPlayer(ctx, StringArgumentType.getString(ctx, "name"))))
                                )
                                .then(Commands.literal("list")
                                        .executes(M7GACommands::listPremiumPlayers)
                                )
                                .then(Commands.literal("reload")
                                        .executes(M7GACommands::reloadPremiumConfig)
                                )
                                .then(Commands.literal("reset")
                                        .executes(M7GACommands::resetPremiumPlayers)
                                )
                                .then(Commands.literal("defaults")
                                        .executes(M7GACommands::showDefaultPlayers)
                                )
                        )
                        .then(Commands.literal("config")
                                .requires(source -> source.hasPermission(2)) // 需要OP权限
                                .then(Commands.literal("flight")
                                        .executes(M7GACommands::toggleFlight)
                                )
                                .then(Commands.literal("armorEffects")
                                        .executes(M7GACommands::toggleArmorEffects)
                                )
                                .then(Commands.literal("status")
                                        .executes(M7GACommands::showConfigStatus)
                                )
                        )
        );
    }

    private static int teleportToHomeDimension(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayerOrException();

        // 获取目标维度
        ServerLevel homeDimension = source.getServer().getLevel(HomeSweetHome.HOME_SWEET_HOME_DIM);

        // 检查维度是否存在
        if (homeDimension == null) {
            source.sendFailure(Component.literal("无法找到维度"));
            return 0;
        }

        // 执行传送
        BlockPos targetPos = new BlockPos(0, 1, 0); // 传送至坐标
        player.teleportTo(homeDimension, targetPos.getX(), targetPos.getY(), targetPos.getZ(), player.getYRot(), player.getXRot());

        // 发送成功消息
        source.sendSuccess(() -> Component.literal("已传送"), true);

        return 1; // 返回值表示命令执行成功
    }

    // Premium玩家管理命令
    private static int addPremiumPlayer(CommandContext<CommandSourceStack> context, String playerName) {
        PremiumManager.addPremiumPlayer(playerName);
        
        // 实时更新所有在线玩家的飞行权限
        CommandSourceStack source = context.getSource();
        source.getServer().getPlayerList().getPlayers().forEach(PremiumEvents::updatePlayerFlightPermission);
        
        context.getSource().sendSystemMessage(Component.literal("[M7GA]♥已添加玩家 " + playerName + " 到氪金列表，已更新所有在线玩家权限！").withStyle(ChatFormatting.LIGHT_PURPLE));
        return 1;
    }

    private static int removePremiumPlayer(CommandContext<CommandSourceStack> context, String playerName) {
        PremiumManager.removePremiumPlayer(playerName);
        
        // 实时更新所有在线玩家的飞行权限
        CommandSourceStack source = context.getSource();
        source.getServer().getPlayerList().getPlayers().forEach(PremiumEvents::updatePlayerFlightPermission);
        
        context.getSource().sendSystemMessage(Component.literal("[M7GA]♥已从氪金列表移除玩家 " + playerName + "，已更新所有在线玩家权限！").withStyle(ChatFormatting.LIGHT_PURPLE));
        return 1;
    }

    private static int listPremiumPlayers(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        source.sendSystemMessage(Component.literal("[M7GA]♥氪金玩家列表：").withStyle(ChatFormatting.LIGHT_PURPLE));

        var premiumPlayers = PremiumManager.getAllPremiumPlayers();
        if (premiumPlayers.isEmpty()) {
            source.sendSystemMessage(Component.literal("[M7GA]♥暂无氪金玩家").withStyle(ChatFormatting.LIGHT_PURPLE));
        } else {
            premiumPlayers.forEach(name -> {
                source.sendSystemMessage(Component.literal("[M7GA]♥-> " + name).withStyle(ChatFormatting.LIGHT_PURPLE));
            });
        }
        return premiumPlayers.size();
    }

    private static int reloadPremiumConfig(CommandContext<CommandSourceStack> context) {
        PremiumManager.loadConfig();
        
        // 实时更新所有在线玩家的飞行权限
        CommandSourceStack source = context.getSource();
        source.getServer().getPlayerList().getPlayers().forEach(PremiumEvents::updatePlayerFlightPermission);
        
        context.getSource().sendSystemMessage(Component.literal("[M7GA]♥已重新加载氪金玩家配置，已更新所有在线玩家权限！").withStyle(ChatFormatting.LIGHT_PURPLE));
        return 1;
    }

    private static int resetPremiumPlayers(CommandContext<CommandSourceStack> context) {
        PremiumManager.resetToDefaultPlayers();
        
        // 实时更新所有在线玩家的飞行权限
        CommandSourceStack source = context.getSource();
        source.getServer().getPlayerList().getPlayers().forEach(PremiumEvents::updatePlayerFlightPermission);
        
        context.getSource().sendSystemMessage(Component.literal("[M7GA]♥已重置为默认氪金玩家列表，已更新所有在线玩家权限！").withStyle(ChatFormatting.LIGHT_PURPLE));
        return 1;
    }

    private static int showDefaultPlayers(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        source.sendSystemMessage(Component.literal("[M7GA]♥默认氪金玩家列表：").withStyle(ChatFormatting.LIGHT_PURPLE));

        var defaultPlayers = PremiumManager.getDefaultPlayers();
        if (defaultPlayers.isEmpty()) {
            source.sendSystemMessage(Component.literal("[M7GA]♥暂无默认玩家").withStyle(ChatFormatting.LIGHT_PURPLE));
        } else {
            defaultPlayers.forEach(name -> {
                source.sendSystemMessage(Component.literal("[M7GA]♥-> " + name).withStyle(ChatFormatting.LIGHT_PURPLE));
            });
        }
        return defaultPlayers.size();
    }

    // 配置管理命令
    private static int toggleFlight(CommandContext<CommandSourceStack> context) {
        M7GAConfig config = M7GAConfig.getInstance();
        config.togglePremiumFlight();
        
        // 实时更新所有在线玩家的飞行权限
        CommandSourceStack source = context.getSource();
        source.getServer().getPlayerList().getPlayers().forEach(PremiumEvents::updatePlayerFlightPermission);

        context.getSource().sendSystemMessage(Component.literal("[M7GA]♥飞行功能已" + config.getFlightStatus() + "，已更新所有在线玩家权限").withStyle(ChatFormatting.LIGHT_PURPLE));
        return 1;
    }

    private static int toggleArmorEffects(CommandContext<CommandSourceStack> context) {
        M7GAConfig config = M7GAConfig.getInstance();
        config.toggleArmorEffects();
        context.getSource().sendSystemMessage(Component.literal("[M7GA]♥护甲效果已" + config.getArmorEffectsStatus()).withStyle(ChatFormatting.LIGHT_PURPLE));
        return 1;
    }

    private static int showConfigStatus(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        M7GAConfig config = M7GAConfig.getInstance();
        
        source.sendSystemMessage(Component.literal("[M7GA]♥当前配置状态：").withStyle(ChatFormatting.LIGHT_PURPLE));
        source.sendSystemMessage(Component.literal("[M7GA]♥飞行功能: " + config.getFlightStatus()).withStyle(ChatFormatting.LIGHT_PURPLE));
        source.sendSystemMessage(Component.literal("[M7GA]♥护甲效果: " + config.getArmorEffectsStatus()).withStyle(ChatFormatting.LIGHT_PURPLE));
        
        return 1;
    }
}