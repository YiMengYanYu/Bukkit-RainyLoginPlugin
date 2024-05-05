package com.rainy.util.config;

import com.rainy.constants.FileConstant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;

/**
 * @author YiMeng
 * @DateTime: 2024/5/4 下午10:27
 * @Description: 玩家上次登录位置配置文件
 */
public class offlineLocationsConfigUtil {

    private static YamlConfiguration config;

    private static HashMap hashMap = new HashMap(16);


    private static final String fileName = "offline-locations.yml";

    /**
     * 创建文件
     */
    public static void createOfflineLocationsConfig() {
        config = ConfigUtil.createConfig(fileName);

    }

    /**
     * 获取玩家上次退出的位置
     * @param playerName
     * @return
     */
    public static Location getLocationByPlayerName(String playerName) {

        String location = config.getString("offlineLocations." + playerName);

        return strToLocation(location);
    }

    /**
     * 保存玩家退出服务器的位置
     * @param player
     * @throws IOException
     */
    public static void setOfflineLocation(Player player) throws IOException {
        String playerName = player.getName();
        String locationStr = locationToStr(player.getLocation());

        config.set("offlineLocations." + playerName, locationStr);
        config.save(FileConstant.YMLOGIN_YML_PATH+fileName);
    }


    /**
     * 字符串转成位置
     *
     * @param str
     * @return
     */
    private static Location strToLocation(String str) {
        Location location;
        try {
            String[] locStrs = str.split(":");
            World world = Bukkit.getWorld(locStrs[0]);
            double x = Double.parseDouble(locStrs[1]);
            double y = Double.parseDouble(locStrs[2]);
            double z = Double.parseDouble(locStrs[3]);
            float yaw = Float.parseFloat(locStrs[4]);
            float pitch = Float.parseFloat(locStrs[5]);
            location = new Location(world, x, y, z, yaw, pitch);
        } catch (Exception e) {
            location = getDefaultWorld().getSpawnLocation();
        }
        return location;

    }

    /**
     * 位置转成字符串
     *
     * @param location
     * @return
     */
    private static String locationToStr(Location location) {
        try {
            return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
        } catch (Exception ignored) {
            location = getDefaultWorld().getSpawnLocation();
        }
        return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();

    }

    /**
     * 获取默认世界
     */
    private static World getDefaultWorld() {

        try {
            InputStream is = new BufferedInputStream(Files.newInputStream(new File("server.properties").toPath()));

            Properties properties = new Properties();
            properties.load(is);
            String worldName = properties.getProperty("level-name");
            is.close();
            return Bukkit.getWorld(worldName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Bukkit.getWorlds().get(0);
    }


}
