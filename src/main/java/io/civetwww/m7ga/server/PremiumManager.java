package io.civetwww.m7ga.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.civetwww.m7ga.M7GA;
import io.civetwww.m7ga.M7GAConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
                System.err.println("[M7GA] 无法创建配置目录: " + m7gaConfigDir.getAbsolutePath());
                return;
            }
        }
        configFile = new File(m7gaConfigDir, CONFIG_FILE_NAME);
        loadConfig();
    }

    /**
     * 加载配置文件
     */
    public static void loadConfig() {
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                Type type = new TypeToken<Set<String>>() {}.getType();
                Set<String> loadedPlayers = GSON.fromJson(reader, type);
                if (loadedPlayers != null && !loadedPlayers.isEmpty()) {
                    premiumPlayers.clear();
                    premiumPlayers.addAll(loadedPlayers);
                    System.out.println("[M7GA] 加载了 " + loadedPlayers.size() + " 个氪金玩家");
                } else {
                    // 如果配置文件为空或不存在，使用默认玩家列表
                    initializeDefaultPlayers();
                }
            } catch (IOException e) {
                System.err.println("[M7GA] 加载氪金玩家配置失败: " + e.getMessage());
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
        System.out.println("[M7GA] 已初始化默认氪金玩家列表: " + DEFAULT_PLAYERS);
    }

    /**
     * 保存配置文件
     */
    private static void saveConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(premiumPlayers, writer);
            System.out.println("[M7GA] 配置文件已保存: " + configFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("[M7GA] 保存氪金玩家配置失败: " + e.getMessage());
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
     * 检查玩家是否为付费玩家
     */
    public static boolean isPremiumPlayer(String playerName) {
        // 使用大小写不敏感的比较
        String lowerCaseName = playerName.toLowerCase();
        return premiumPlayers.stream()
                .anyMatch(name -> name.toLowerCase().equals(lowerCaseName));
    }

    /**
     * 检查玩家是否有付费飞行权限
     */
    public static boolean isPremiumPlayerWithFlight(String playerName) {
        boolean isPremium = isPremiumPlayer(playerName);
        boolean flightEnabled = M7GAConfig.getInstance().isPremiumFlightEnabled();
        
        if (isPremium && flightEnabled) {
            System.out.println("[m7ga] 玩家 " + playerName + " 拥有付费飞行权限");
        } else if (isPremium) {
            System.out.println("[m7ga] 玩家 " + playerName + " 是付费玩家，但飞行功能已关闭");
        } else {
            System.out.println("[m7ga] 玩家 " + playerName + " 无付费飞行权限");
        }
        
        return isPremium && flightEnabled;
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
     * 初始化服务器实例
     */
    public static void setServerInstance(MinecraftServer server) {
        serverInstance = server;
        // 初始化配置文件
        init(server.getServerDirectory().toFile());
    }
}