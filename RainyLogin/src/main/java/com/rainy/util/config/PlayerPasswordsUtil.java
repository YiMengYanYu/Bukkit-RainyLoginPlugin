package com.rainy.util.config;

import com.rainy.constants.FileConstant;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author YiMeng
 * @DateTime: 2024/4/5 0:17
 * @Description: 注册用户信息配置文件工具类
 */
public class PlayerPasswordsUtil {

    private static YamlConfiguration configuration;
    private static File file;

    public static void createPlayerPasswords() {

        file = ConfigUtil.createFile(FileConstant.PLAYER_PASSWORDS_YML);
        configuration = YamlConfiguration.loadConfiguration(file);

    }


    /**
     * 获取玩家是否注册
     *
     * @param playerName
     * @return 注册了返回true 没注册返回false
     */
    public static boolean isRegister(String playerName) {

        String password = configuration.getString(playerName + ".password");
        if (password == null || password.length() == 0) {
            return false;
        }

        return true;
    }

    /**
     * 保存玩家的密码
     *
     * @param playerName
     * @param password
     * @param uuid
     * @return
     */
    public static void setPlayerPassword(String playerName, String password, String uuid) throws IOException {
        HashMap hashMap = new HashMap(8);
        hashMap.put("uuid", uuid);
        hashMap.put("password", password);
        configuration.set(playerName, hashMap);
        configuration.save(file);


    }

    /**
     * 获取玩家的密码
     * @param playerName
     * @return
     */
    public static String getPlayerPassword(String playerName) {
        String password = configuration.getString(playerName + ".password");
        return password;
    }




}
