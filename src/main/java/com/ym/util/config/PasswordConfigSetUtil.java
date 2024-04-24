package com.ym.util.config;

import com.ym.entity.PasswordConfigEntity;
import com.ym.ymlogin.YMLogin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

/**
 * @author YiMeng
 * @DateTime: 2024/4/14 4:33
 * @Description: TODO
 */
public class PasswordConfigSetUtil {
    private static YamlConfiguration data;
    private static File file = null;
    private static final Logger logger = Bukkit.getLogger();

    /**
     * 设置密码配置
     */
    public static void setConfig(YMLogin ymLogin) {
        loadData(ymLogin, "password-policy-config.yml");
        YamlConfiguration ymlData = getData();
        Field[] fields = PasswordConfigEntity.class.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            if (field.getType() != String.class) {
                continue;
            }
            String variableName = field.getName();
            field.setAccessible(true);
            String param = snakeCaseToCamelCase(variableName);


            String tuoFeng = ymlData.getString("password." + param);
            try {
                field.set(null, tuoFeng);
                logger.info(param + ":" + field.get(tuoFeng));


            } catch (IllegalAccessException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "加载密码强度配置时出错" + e.getMessage());
                e.printStackTrace();
            }

        }


    }


    public static String snakeCaseToCamelCase(String snakeStr) {
        StringBuilder camelStr = new StringBuilder();
        boolean nextUpperCase = false;

        for (int i = 0; i < snakeStr.length(); i++) {
            char c = snakeStr.charAt(i);
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    camelStr.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    camelStr.append(Character.toLowerCase(c));
                }
            }
        }

        return camelStr.toString();
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
    /**
     * 通过File对象重新加载配置文件 返回 YamlConfiguration
     * @return YamlConfiguration
     */
    public static YamlConfiguration getData() {
        data = YamlConfiguration.loadConfiguration(file);
        return data;
    }

    /**
     * 获取File对象
     * @return
     */
    public static File getFile() {
        return file;
    }

}
