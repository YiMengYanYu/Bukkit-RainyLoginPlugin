package com.ym.util.config;


import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author YiMeng
 * @DateTime: 2024/4/24 下午4:08
 * @Description: TODO
 */
public class LoginConfigUtil extends ConfigUtil {




    public static  void  createLoginConfig(JavaPlugin javaPlugin){

        loadData(javaPlugin,"login-config.yml" );



    }
}
