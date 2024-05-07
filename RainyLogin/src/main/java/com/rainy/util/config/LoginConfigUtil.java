package com.rainy.util.config;


import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author YiMeng
 * @DateTime: 2024/4/24 下午4:08
 * @Description: 文件名是login-config.yml
 */
public class LoginConfigUtil {

    private static YamlConfiguration config = null;



    /**
     * 白名单配置
     */
    public static boolean whitelistCheckSwitch;

    /**
     * IP登录配置
     */
    public static boolean ipLoginCheckSwitch;

    /**
     * WebSocket配置
     */
    public static boolean webSocketCheckSwitch;
    /**
     * webSocket地址
     */
    public static String webSocketUrl;

    private  static  final  String fileName = "login-config.yml";
    /**
     * 创建默认的配置文件
     *
     */
    public static void createLoginConfig() {

        config = ConfigUtil.createConfig(fileName);
        setConfig();

    }

    /**
     * 初始化登录配置
     */
    public static void setConfig() {

        //白名单配置
        LoginConfigUtil.whitelistCheckSwitch = config.getBoolean("whitelist.whitelistCheckSwitch");
        //ip登录配置
        LoginConfigUtil.ipLoginCheckSwitch = config.getBoolean("ipLogin.ipLoginCheckSwitch");



    }


}
