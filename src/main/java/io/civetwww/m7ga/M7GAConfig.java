package io.civetwww.m7ga;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class M7GAConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(M7GAConfig.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static M7GAConfig INSTANCE;
    
    // 配置项
    private boolean premiumFlightEnabled = true;
    private boolean armorEffectsEnabled = true;
    
    // 配置文件路径
    private Path configPath;
    
    private M7GAConfig() {
        // 私有构造函数，单例模式
    }
    
    public static M7GAConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new M7GAConfig();
        }
        return INSTANCE;
    }
    
    public void initialize(MinecraftServer server) {
        try {
            // 获取配置文件路径
            Path configDir = server.getWorldPath(LevelResource.ROOT).resolve("config");
            Files.createDirectories(configDir);
            configPath = configDir.resolve("m7ga/config.json");
            Files.createDirectories(configPath.getParent());
            
            // 加载配置
            loadConfig();
        } catch (IOException e) {
            LOGGER.error("[m7ga] 初始化M7GA配置失败", e);
        }
    }
    
    private void loadConfig() throws IOException {
        if (Files.exists(configPath)) {
            // 读取现有配置
            String content = Files.readString(configPath);
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            
            if (json.has("premiumFlightEnabled")) {
                premiumFlightEnabled = json.get("premiumFlightEnabled").getAsBoolean();
            }
            
            if (json.has("armorEffectsEnabled")) {
                armorEffectsEnabled = json.get("armorEffectsEnabled").getAsBoolean();
            }
            

            
            LOGGER.info("[m7ga] M7GA配置加载成功");
        } else {
            // 创建默认配置
            saveConfig();
            LOGGER.info("[m7ga] M7GA配置已使用默认值创建");
        }
    }
    
    private void saveConfig() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("premiumFlightEnabled", premiumFlightEnabled);
        json.addProperty("armorEffectsEnabled", armorEffectsEnabled);

        
        String content = GSON.toJson(json);
        Files.writeString(configPath, content);
    }
    
    // 飞行功能相关方法
    public boolean isPremiumFlightEnabled() {
        return premiumFlightEnabled;
    }
    
    public void setPremiumFlightEnabled(boolean enabled) {
        this.premiumFlightEnabled = enabled;
        try {
            saveConfig();
            LOGGER.info("[m7ga] 飞行功能已{}", enabled ? "开启" : "关闭");
        } catch (IOException e) {
            LOGGER.error("[m7ga] 飞行设置保存配置失败", e);
        }
    }
    
    public void togglePremiumFlight() {
        setPremiumFlightEnabled(!premiumFlightEnabled);
    }
    
    // 护甲效果相关方法
    public boolean isArmorEffectsEnabled() {
        return armorEffectsEnabled;
    }
    
    public void setArmorEffectsEnabled(boolean enabled) {
        this.armorEffectsEnabled = enabled;
        try {
            saveConfig();
            LOGGER.info("[m7ga] 护甲效果已{}", enabled ? "开启" : "关闭");
        } catch (IOException e) {
            LOGGER.error("[m7ga] 更新护甲效果设置后保存配置失败", e);
        }
    }
    
    public void toggleArmorEffects() {
        setArmorEffectsEnabled(!armorEffectsEnabled);
    }
    
    // 获取当前状态描述
    public String getFlightStatus() {
        return premiumFlightEnabled ? "开启" : "关闭";
    }
    
    public String getArmorEffectsStatus() {
        return armorEffectsEnabled ? "开启" : "关闭";
    }
}