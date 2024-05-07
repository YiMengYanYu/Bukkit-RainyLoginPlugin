package com.rainy.util.config;

import com.rainy.entity.PasswordConfigEntity;
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
public class PasswordPolicyConfigUtil {
    private static YamlConfiguration configuration;

    private static final Logger logger = Bukkit.getLogger();

    private  static  final  String fileName = "password-policy-config.yml";
    /**
     * 设置密码配置
     */
    public static void setConfig() {
        configuration = ConfigUtil.createConfig(fileName);

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


            String tuoFeng = configuration.getString("password." + param);
            try {
                field.set(null, tuoFeng);
                //     logger.info(param + ":" + field.get(tuoFeng));


            } catch (IllegalAccessException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "加载密码强度配置时出错" + e.getMessage());
                e.printStackTrace();
            }

        }


    }


    /**
     * 将蛇形命名转换为驼峰命名。
     *
     * @param snakeStr 蛇形命名的字符串，例如"hello_world"。
     * @return 转换后的驼峰命名字符串，例如"helloWorld"。
     */
    public static String snakeCaseToCamelCase(String snakeStr) {
        StringBuilder camelStr = new StringBuilder();
        boolean nextUpperCase = false;

        // 遍历字符串，将蛇形命名转换为驼峰命名
        for (int i = 0; i < snakeStr.length(); i++) {
            char c = snakeStr.charAt(i);
            // 当遇到下划线时，标记下一个字符需要大写
            if (c == '_') {
                nextUpperCase = true;
            } else {
                // 如果下一个字符需要大写，则将其转换为大写并添加到结果中
                if (nextUpperCase) {
                    camelStr.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    // 否则，将字符转换为小写并添加到结果中
                    camelStr.append(Character.toLowerCase(c));
                }
            }
        }

        return camelStr.toString();
    }


}
