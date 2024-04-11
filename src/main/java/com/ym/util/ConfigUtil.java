package com.ym.util;

import com.ym.ymlogin.YMLogin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author YiMeng
 * @DateTime: 2024/4/5 0:17
 * @Description: TODO
 */
public class ConfigUtil {

    private static YamlConfiguration data;
    private static File file = null;

    public static void loadData(YMLogin ymLogin) {
        file = new File(ymLogin.getDataFolder(), "YMLogin.yml");
        if (!file.exists()) {
            ymLogin.getLogger().info("YMLogin.yml 文件不存在，已创建新文件");
            ymLogin.saveResource("YMLogin.yml", false);
        }
        data = YamlConfiguration.loadConfiguration(file);
        if (data == null) {
            ymLogin.getLogger().warning("无法加载 YMLogin.yml 文件！");
            return;
        }
        ymLogin.getLogger().info("YMLogin.yml 文件成功加载");
    }

    /**
     * 获取配置文件
     * @return YamlConfiguration
     */
    public static YamlConfiguration getData() {
        data = YamlConfiguration.loadConfiguration(file);
        return data;
    }

    /**
     * 获取玩家是否注册
     * @param playerName
     * @return 注册了返回true 没注册返回false
     */
    public  static boolean isRegister(String playerName) {
        getData();
        String password = data.getString(playerName + ".password");
        if (password == null || password.length()==0 ) {
            return false;
        }

        return true;
    }

    public static File getFile() {
        return file;
    }

    /**
     * 重载
     */
    public static void reloadConfig(YMLogin ymLogin) {
        ymLogin.reloadConfig();
        ConfigUtil.loadData(ymLogin);
    }

}
