package com.ym.listener;

import com.ym.util.ConfigUtil;
import com.ym.util.SessionUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author YiMeng
 * @DateTime: 2024/4/4 0:04
 * @Description: TODO
 */
public class PlayerListener implements Listener {



    /**
     * 玩家进入时
     *
     * @param playerJoinEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void playerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        // 阻止默认的加入消息显示
        playerJoinEvent.setJoinMessage(null);
        isNoRegister(player);
        String hostString = player.getAddress().getHostString();
        Bukkit.getConsoleSender().sendMessage(hostString);

    }

    private void isNoRegister(Player player)
    {
        if (ConfigUtil.isRegister(player.getName())) {
            isNoLogin(player);
            return;
        }

        player.sendMessage("请注册后进入游戏");
        player.sendMessage("/reg <密码> <密码>");
    }

    private  void isNoLogin(Player player) {
        String playerIp = SessionUtil.getPlayerIpByName(player.getName());
        String ip = player.getAddress().getHostString();
        if (Objects.equals(playerIp, ip)) {
            player.sendMessage(ChatColor.GREEN+"通过ip自动登录");
            return;
        }

        SessionUtil.destroySession(player.getName());

        player.sendMessage("请登录");
        player.sendMessage("/login <密码>");
    }
}
