package io.civetwww.m7ga.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.civetwww.m7ga.server.DimGen.HomeSweetHome;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class HSHCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("m7ga")
                        .then(Commands.literal("home")
                                .requires(source -> source.hasPermission(0)) // 需要权限等级2才能使用
                                .executes(HSHCommands::teleportToHomeDimension)
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
}