package com.rainy.bot.config;

import com.rainy.bot.RainyBukkitBot;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author YiMeng
 * @DateTime: 2024/5/7 下午3:10
 * @Description: TODO
 */
public class BotConfig {

    private static RainyBukkitBot javaPlugin = RainyBukkitBot.getInstance(RainyBukkitBot.class);

    private static String configName = "BotConfig.yml";

    public static boolean webSocketCheckSwitch;

    public static String webSocketUrl;

    public static void createBotConfig() {

        YamlConfiguration config = createConfig(configName);
        //WebSocket配置
        webSocketCheckSwitch = config.getBoolean("webSocket.webSocketCheckSwitch");
        //webSocketUrl
        webSocketUrl = config.getString("webSocket.webSocketUrl");

    }


    /**
     * 使用Bukkit加载配置文件,如果resources(资源目录)下没有配置文件就不会加载
     */
    public static YamlConfiguration createConfig(String configName) {


        File file = new File(javaPlugin.getDataFolder(), configName);
        if (!file.exists()) {
            javaPlugin.getLogger().info(configName + " 文件创建成功");
            javaPlugin.saveResource(configName, false);
        }
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        javaPlugin.getLogger().info(configName + " 文件成功加载");
        return yamlConfiguration;
    }

}
