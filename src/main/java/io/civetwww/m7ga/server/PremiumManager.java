package io.civetwww.m7ga.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.civetwww.m7ga.M7GA;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion.MOD_ID;

@EventBusSubscriber(modid = M7GA.MODID, bus = EventBusSubscriber.Bus.GAME)
public class PremiumManager {
    private static final ResourceLocation PREMIUM_FLIGHT = ResourceLocation.fromNamespaceAndPath(M7GA.MODID, "premium_flight");
    private static final String CONFIG_FILE_NAME = "premium_players.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // 预定义的玩家ID列表
    private static final Set<String> DEFAULT_PLAYERS = Set.of(
        "Civetnya",
        "weiai_world"
    );

    // 存储有权限的玩家名称
    private static final Set<String> premiumPlayers = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private static File configFile;

    // 存储服务器实例引用
    private static MinecraftServer serverInstance;

    public static void init(File configDir) {
        // 创建M7GA配置目录
        File m7gaConfigDir = new File(configDir, "config");
        m7gaConfigDir = new File(m7gaConfigDir, M7GA.MODID);
        if (!m7gaConfigDir.exists()) {
            boolean created = m7gaConfigDir.mkdirs();
            if (!created) {
                System.err.println("[M7GA]♥无法创建配置目录: " + m7gaConfigDir.getAbsolutePath());
                return;
            }
        }
        configFile = new File(m7gaConfigDir, CONFIG_FILE_NAME);
        loadConfig();
    }

    /**
     * 加载配置文件
     */
    private static void loadConfig() {
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                Type type = new TypeToken<Set<String>>() {}.getType();
                Set<String> loadedPlayers = GSON.fromJson(reader, type);
                if (loadedPlayers != null && !loadedPlayers.isEmpty()) {
                    premiumPlayers.clear();
                    premiumPlayers.addAll(loadedPlayers);
                    System.out.println("[M7GA]♥加载了 " + loadedPlayers.size() + " 个氪金玩家");
                } else {
                    // 如果配置文件为空或不存在，使用默认玩家列表
                    initializeDefaultPlayers();
                }
            } catch (IOException e) {
                System.err.println("[M7GA]♥加载氪金玩家配置失败: " + e.getMessage());
                // 加载失败时使用默认玩家列表
                initializeDefaultPlayers();
            }
        } else {
            // 创建默认配置文件
            initializeDefaultPlayers();
        }
    }

    /**
     * 初始化默认玩家列表
     */
    private static void initializeDefaultPlayers() {
        premiumPlayers.clear();
        premiumPlayers.addAll(DEFAULT_PLAYERS);
        saveConfig();
        System.out.println("[M7GA]♥已初始化默认氪金玩家列表: " + DEFAULT_PLAYERS);
    }

    /**
     * 保存配置文件
     */
    private static void saveConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(premiumPlayers, writer);
            System.out.println("[M7GA]♥配置文件已保存: " + configFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("[M7GA]♥保存氪金玩家配置失败: " + e.getMessage());
        }
    }

    /**
     * 添加玩家到付费列表
     */
    public static void addPremiumPlayer(String playerName) {
        premiumPlayers.add(playerName);
        saveConfig();
    }

    /**
     * 从付费列表移除玩家
     */
    public static void removePremiumPlayer(String playerName) {
        premiumPlayers.remove(playerName);
        saveConfig();
    }

    /**
     * 检查玩家是否有付费权限
     */
    public static boolean isPremiumPlayer(String playerName) {
        return premiumPlayers.contains(playerName);
    }

    /**
     * 获取所有付费玩家
     */
    public static Set<String> getAllPremiumPlayers() {
        return Collections.unmodifiableSet(premiumPlayers);
    }

    /**
     * 重置为默认玩家列表
     */
    public static void resetToDefaultPlayers() {
        initializeDefaultPlayers();
    }

    /**
     * 获取默认玩家列表
     */
    public static Set<String> getDefaultPlayers() {
        return Collections.unmodifiableSet(DEFAULT_PLAYERS);
    }

    /**
     * 通过名称获取在线玩家
     */
    private static ServerPlayer getServerPlayerByName(String playerName) {
        if (serverInstance != null) {
            return serverInstance.getPlayerList().getPlayerByName(playerName);
        }
        return null;
    }

    /**
     * 注册命令
     */
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        // 存储服务器实例引用
        serverInstance = event.getServer();

        // 初始化配置文件
        init(event.getServer().getServerDirectory().toFile());

        // 注册命令 - 只有OP权限（等级2）和Civetnya可以使用
        event.getServer().getCommands().getDispatcher().register(Commands.literal("premium")
                .requires(source -> {
                    // 检查是否有OP权限（等级2）
                    boolean hasOpPermission = source.hasPermission(2);

                    // 检查是否是Civetnya玩家
                    boolean isCivetnya = false;
                    if (source.getEntity() instanceof ServerPlayer) {
                        ServerPlayer player = (ServerPlayer) source.getEntity();
                        String playerName = player.getName().getString();
                        isCivetnya = "Civetnya".equalsIgnoreCase(playerName);
                    }

                    // 允许OP权限或Civetnya使用命令
                    return hasOpPermission || isCivetnya;
                })
                .then(Commands.literal("add")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(ctx -> {
                                    ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
                                    String playerName = player.getName().getString();
                                    addPremiumPlayer(playerName);
                                    ctx.getSource().sendSystemMessage(Component.literal("[M7GA]♥已添加玩家 " + playerName + " 到氪金列表！").withStyle(ChatFormatting.LIGHT_PURPLE));
                                    return 1;
                                }))
                        .then(Commands.argument("name", StringArgumentType.string())
                                .executes(ctx -> {
                                    String playerName = StringArgumentType.getString(ctx, "name");
                                    addPremiumPlayer(playerName);
                                    ctx.getSource().sendSystemMessage(Component.literal("[M7GA]♥已添加玩家 " + playerName + " 到氪金列表！").withStyle(ChatFormatting.LIGHT_PURPLE));
                                    return 1;
                                }))
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(ctx -> {
                                    ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
                                    String playerName = player.getName().getString();
                                    removePremiumPlayer(playerName);
                                    ctx.getSource().sendSystemMessage(Component.literal("[M7GA]♥已从氪金列表移除玩家 " + playerName + "！").withStyle(ChatFormatting.LIGHT_PURPLE));
                                    return 1;
                                }))
                        .then(Commands.argument("name", StringArgumentType.string())
                                .executes(ctx -> {
                                    String playerName = StringArgumentType.getString(ctx, "name");
                                    removePremiumPlayer(playerName);
                                    ctx.getSource().sendSystemMessage(Component.literal("[M7GA]♥已从氪金列表移除玩家 " + playerName + "！").withStyle(ChatFormatting.LIGHT_PURPLE));
                                    return 1;
                                }))
                )
                .then(Commands.literal("list")
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            source.sendSystemMessage(Component.literal("[M7GA]♥氪金玩家列表：").withStyle(ChatFormatting.LIGHT_PURPLE));

                            if (premiumPlayers.isEmpty()) {
                                source.sendSystemMessage(Component.literal("[M7GA]♥暂无氪金玩家").withStyle(ChatFormatting.LIGHT_PURPLE));
                            } else {
                                premiumPlayers.forEach(name -> {
                                    source.sendSystemMessage(Component.literal("[M7GA]♥-> " + name).withStyle(ChatFormatting.LIGHT_PURPLE));
                                });
                            }
                            return premiumPlayers.size();
                        }))
                .then(Commands.literal("reload")
                        .executes(ctx -> {
                            loadConfig();
                            ctx.getSource().sendSystemMessage(Component.literal("[M7GA]♥已重新加载氪金玩家配置！").withStyle(ChatFormatting.LIGHT_PURPLE));
                            return 1;
                        }))
                .then(Commands.literal("reset")
                        .executes(ctx -> {
                            resetToDefaultPlayers();
                            ctx.getSource().sendSystemMessage(Component.literal("[M7GA]♥已重置为默认氪金玩家列表！").withStyle(ChatFormatting.LIGHT_PURPLE));
                            return 1;
                        }))
                .then(Commands.literal("defaults")
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            source.sendSystemMessage(Component.literal("[M7GA]♥默认氪金玩家列表：").withStyle(ChatFormatting.LIGHT_PURPLE));

                            if (DEFAULT_PLAYERS.isEmpty()) {
                                source.sendSystemMessage(Component.literal("[M7GA]♥暂无默认玩家").withStyle(ChatFormatting.LIGHT_PURPLE));
                            } else {
                                DEFAULT_PLAYERS.forEach(name -> {
                                    source.sendSystemMessage(Component.literal("[M7GA]♥-> " + name).withStyle(ChatFormatting.LIGHT_PURPLE));
                                });
                            }
                            return DEFAULT_PLAYERS.size();
                        }))
        );
    }
}