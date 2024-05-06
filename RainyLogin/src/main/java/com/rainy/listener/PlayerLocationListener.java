package com.rainy.listener;

import com.google.inject.Inject;
import com.rainy.util.SessionUtil;
import com.rainy.util.config.LoginConfigUtil;
import com.rainy.util.config.offlineLocationsConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;

import java.io.IOException;

/**
 * @author YiMeng
 * @DateTime: 2024/5/6 下午2:40
 * @Description: 记录退出玩家位置
 */
public class PlayerLocationListener implements Listener {


    @Inject
    private PluginLogger logger;

    /**
     * 玩家退出游戏时
     *
     * @param playerQuitEvent 玩家退出游戏时的事件
     */
    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        //如果ip登录配置是关闭的
        if (!LoginConfigUtil.ipLoginCheckSwitch) {
            SessionUtil.destroySession(player.getName());
        }

        try {

            offlineLocationsConfigUtil.setOfflineLocation(player);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //TODO 退出游戏后在多少秒内不让重连
    }

    /**
     * 玩家重生时
     * @param event
     */
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (!SessionUtil.getPlayerState(player.getName())) {

            World world = Bukkit.getWorld("world"); // 获取名为"world"的世界对象，根据你的服务器配置可能需要其他名字
            Location spawnLocation = world.getSpawnLocation();
            event.setRespawnLocation(spawnLocation);
        }

    }
}
