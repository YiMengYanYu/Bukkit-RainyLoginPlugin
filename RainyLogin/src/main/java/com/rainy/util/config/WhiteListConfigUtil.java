package com.rainy.util.config;

import com.rainy.constants.FileConstant;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YiMeng
 * @DateTime: 2024/4/24 下午4:17
 * @Description: 登录插件的白名单工具类
 */
public class WhiteListConfigUtil {
    private static YamlConfiguration data;
    private static File file = null;

    private static List<String> whitelist = new ArrayList<>();

    private  static  final  String fileName="whitelist.yml";

    public static void createWhiteListConfig() {
        data = ConfigUtil.createConfig(fileName);
        whitelist = data.getStringList("whitelist");
    }

    /**
     * 加载配置文件的白名单列表
     * 此方法危险
     */
    public static void loadWhiteList() {

        whitelist = data.getStringList("whitelist");

        //去重
        //  whitelist = whitelist.stream().distinct().collect(Collectors.toList());

    }

    /**
     * 保存白名单配置
     */
    public static void addWhiteList(String playerName) {
        if ("".equals(playerName)|| playerName==null) {

            return;
        }

        whitelist.add(playerName);
        data.set("whitelist", whitelist);
        whitelist = whitelist.stream().distinct().collect(Collectors.toList());
        try {
            data.save(FileConstant.YMLOGIN_YML_PATH+fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static  void removeWhiteList(String playerName) throws IOException {
        whitelist.remove(playerName);


        data.set("whitelist", whitelist);
        data.save(FileConstant.YMLOGIN_YML_PATH+fileName);
    }

    /**
     * 玩家在白名单返回true 否则返回false
     * @param playerName
     * @return
     */
    public static boolean contains(String playerName) {
        Bukkit.getConsoleSender().sendMessage(whitelist.contains(playerName)+"111111111111111111111111111111111111111111111");
        return whitelist.contains(playerName);

    }

}
