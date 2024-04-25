package com.rainy.entity;

/**
 * @author YiMeng
 * @DateTime: 2024/4/24 下午4:34
 * @Description: TODO
 */
public class LoginConfig {

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
     *webSocket地址
     */
    public static String webSocketUrl;


}
