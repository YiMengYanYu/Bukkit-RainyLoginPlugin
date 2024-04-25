package com.rainy.util.config;


import com.rainy.entity.LoginConfig;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author YiMeng
 * @DateTime: 2024/4/24 下午4:08
 * @Description: TODO
 */
public class LoginConfigUtil  {

    private static YamlConfiguration data;
    private static File file = null;
    /**
     * 创建默认的配置文件
     * @param javaPlugin
     */
    public static  void  createLoginConfig(JavaPlugin javaPlugin){

        loadData(javaPlugin,"login-config.yml" );
        setConfig();

    }

    /**
     * 初始化登录配置
     */
    public  static void  setConfig(){

    //白名单配置
    LoginConfig.whitelistCheckSwitch= data.getBoolean("whitelist.whitelistCheckSwitch");
    //ip登录配置
    LoginConfig.ipLoginCheckSwitch= data.getBoolean("ipLogin.ipLoginCheckSwitch");
    //WebSocket配置
    LoginConfig.webSocketCheckSwitch= data.getBoolean("webSocket.webSocketCheckSwitch");
    //webSocketUrl
    LoginConfig.webSocketUrl= data.getString("webSocket.webSocketUrl");


    }
    /**
     * 使用Bukkit加载配置文件,如果resources(资源目录)下没有配置文件就不会加载
     * @param ymLogin
     * @param configName
     */
    protected  static void loadData(JavaPlugin ymLogin, String configName) {


        file = new File(ymLogin.getDataFolder(), configName);
        if (!file.exists()) {
            ymLogin.getLogger().info(configName+" 文件不存在，已创建新文件");
            ymLogin.saveResource(configName, false);
        }
        data = YamlConfiguration.loadConfiguration(file);
        ymLogin.getLogger().info(configName+" 文件成功加载");
    }

}
