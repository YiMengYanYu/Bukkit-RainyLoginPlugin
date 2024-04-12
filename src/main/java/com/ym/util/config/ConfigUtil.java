package com.ym.util.config;

import com.ym.constants.FileConstant;
import com.ym.ymlogin.YMLogin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @author YiMeng
 * @DateTime: 2024/4/12 19:20
 * @Description: 配置文件通用父类，请不要直接调用
 */
public  class ConfigUtil {
    protected static YamlConfiguration data;
    protected static File file = null;


    /**
     * 使用Bukkit加载配置文件,如果resources(资源目录)下没有配置文件就不会加载
     * @param ymLogin
     * @param configName
     */
    protected  static void loadData(YMLogin ymLogin,String configName) {


        ConfigUtil.file = new File(ymLogin.getDataFolder(), configName);
        if (!ConfigUtil.file.exists()) {
            ymLogin.getLogger().info(configName+" 文件不存在，已创建新文件");
            ymLogin.saveResource(configName, false);
        }
        data = YamlConfiguration.loadConfiguration(ConfigUtil.file);
        ymLogin.getLogger().info(configName+" 文件成功加载");
    }


    /**
     * 传统File方式创建文件
     * @param fileName
     */
    public static void create(String fileName) {

        // 定义文件路径（相对路径）
        String relativePath = FileConstant.YMLOGIN_YML_PATH+fileName;

        // 创建File对象
        File passwordFile = new File(relativePath);

        // 判断文件夹YMLogin是否存在，如果不存在则创建
        File ymLoginDirectory = new File("./YMLogin");
        if (!ymLoginDirectory.exists()) {
            ymLoginDirectory.mkdir(); // 创建目录
        }

        // 判断文件是否存在，如果不存在则创建
        if (!passwordFile.exists()) {
            try {
                passwordFile.createNewFile(); // 创建文件
                System.out.println("文件player_passwords.yml已创建于 ./YMLogin 目录下");
            } catch (IOException e) {
                System.err.println("创建文件player_passwords.yml时发生错误: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("文件player_passwords.yml已存在于 ./YMLogin 目录下");
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
