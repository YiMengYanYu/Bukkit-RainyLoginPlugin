package com.ym.util.config;

import com.ym.entity.PasswordConfigEntity;
import com.ym.ymlogin.YMLogin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

/**
 * @author YiMeng
 * @DateTime: 2024/4/14 4:33
 * @Description: TODO
 */
public class PasswordConfigSetUtil extends ConfigUtil {

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
}
