package com.rainy.util.config;

import com.rainy.RainyLogin;
import com.rainy.constants.FileConstant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.framework.qual.CFComment;

import java.io.File;
import java.io.IOException;

/**
 * @author YiMeng
 * @DateTime: 2024/4/12 19:20
 * @Description:
 */
public class ConfigUtil {

    private static RainyLogin rainyLogin = RainyLogin.getInstance(RainyLogin.class);

    /**
     * 使用Bukkit加载配置文件,如果resources(资源目录)下没有配置文件就不会加载
     *
     */
    public static YamlConfiguration createConfig(String configName) {


        File file = new File(rainyLogin.getDataFolder(), configName);
        if (!file.exists()) {
            rainyLogin.getLogger().info(configName + " 文件创建成功");
            rainyLogin.saveResource(configName, false);
        }
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        rainyLogin.getLogger().info(configName + " 文件成功加载");
        return yamlConfiguration;
    }


    /**
     * 传统File方式创建文件 不需要在资源文件夹中创建文件
     *
     * @param fileName
     */
    public static File createFile(String fileName) {

        // 定义文件路径（相对路径）
        String relativePath = FileConstant.YMLOGIN_YML_PATH + fileName;

        // 创建File对象
        File file = new File(relativePath);

        // 判断文件夹YMLogin是否存在，如果不存在则创建
        File ymLoginDirectory = new File(FileConstant.YMLOGIN_YML_PATH);
        if (!ymLoginDirectory.exists()) {
            ymLoginDirectory.mkdir(); // 创建目录
        }

        // 判断文件是否存在，如果不存在则创建
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    rainyLogin.getLogger().info(fileName + " 文件创建成功");
                    //创建文件后给文件对象赋值

                    return file;
                }
                throw new IOException();
            } catch (IOException e) {
                rainyLogin.getLogger().info("创建文件 " + fileName + "时发生错误: " + e.getMessage());

                return null;
            }
        } else {
            rainyLogin.getLogger().info(fileName + " 文件成功加载");
            // 文件存在就直接把文件对象赋值
            return  file;
        }

    }





}
