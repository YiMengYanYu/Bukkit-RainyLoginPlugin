package com.rainy.util.config;

import com.rainy.constants.FileConstant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * @author YiMeng
 * @DateTime: 2024/4/5 0:17
 * @Description: 注册用户信息配置文件工具类
 */
public class RegConfigUtil  {

    private static YamlConfiguration data;
    private static File file = null;

    public static void loadData() {

       createFile(FileConstant.PLAYER_PASSWORDS_YML);
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
     * 传统File方式创建文件 不需要在资源文件夹中创建文件
     * @param fileName
     */
    protected static void createFile(String fileName) {

        // 定义文件路径（相对路径）
        String relativePath = FileConstant.YMLOGIN_YML_PATH+fileName;

        // 创建File对象
        File passwordFile = new File(relativePath);

        // 判断文件夹YMLogin是否存在，如果不存在则创建
        File ymLoginDirectory = new File(FileConstant.YMLOGIN_YML_PATH);
        if (!ymLoginDirectory.exists()) {
            ymLoginDirectory.mkdir(); // 创建目录
        }

        // 判断文件是否存在，如果不存在则创建
        if (!passwordFile.exists()) {
            try {
                if (passwordFile.createNewFile()) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"文件"+fileName+"已创建于 "+FileConstant.YMLOGIN_YML_PATH+"目录下");
                    //创建文件后给文件对象赋值
                    file=passwordFile;
                    return;
                }
                throw  new IOException();
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"创建文件 "+fileName+"时发生错误: " + e.getMessage());
            }
        } else {
            // 文件存在就直接把文件对象赋值
            file=passwordFile;
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"文件"+fileName+"已存在于 "+FileConstant.YMLOGIN_YML_PATH+" 目录下");
        }

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
