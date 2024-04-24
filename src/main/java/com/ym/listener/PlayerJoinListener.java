package com.ym.listener;

import com.ym.util.config.RegConfigUtil;
import com.ym.util.SessionUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author YiMeng
 * @DateTime: 2024/4/4 0:04
 * @Description: TODO
 */
public class PlayerJoinListener implements Listener {



    /**
     * 玩家进入时
     *
     * @param playerJoinEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void playerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        //设置玩家无敌
        player.setInvulnerable(true);

        // 阻止默认的加入消息显示
        playerJoinEvent.setJoinMessage(null);
        isNoRegister(player);


    }

    /**
     * 玩家退出游戏时
     * @param playerQuitEvent
     */
    @EventHandler
    public  void  playerQuitEvent(PlayerQuitEvent playerQuitEvent){




    }

    /**
     * 检测是否注册
     * @param player
     */
    private void isNoRegister(Player player)
    {
        if (RegConfigUtil.isRegister(player.getName())) {
            isNoLogin(player);
            return;
        }
        SessionUtil.destroySession(player.getName());

        ScheduledExecutorService executor =new ScheduledThreadPoolExecutor(1);

        Runnable taskLogin = () -> {

            if (SessionUtil.getPlayerState(player.getName())) {
                executor.shutdown();

            }else {

                player.sendMessage("请注册后进入游戏");
                player.sendMessage("/reg <密码> <密码>");
            }

        };
        executor.scheduleAtFixedRate(taskLogin, 0, 5, TimeUnit.SECONDS);
    }

    private  void isNoLogin(Player player) {
        String playerIp = SessionUtil.getPlayerIpByName(player.getName());
        String ip = player.getAddress().getHostString();
        if (Objects.equals(playerIp, ip)) {
            player.setInvulnerable(false);
            player.sendMessage(ChatColor.GREEN+"通过ip自动登录");
            return;
        }
        //如果登录IP和上次不一致就先销毁session
        SessionUtil.destroySession(player.getName());

        ScheduledExecutorService executor =new ScheduledThreadPoolExecutor(1);

        Runnable taskLogin = () -> {

            if (SessionUtil.getPlayerState(player.getName())) {

                executor.shutdown();

            }else {
                player.sendMessage("请登录");
                player.sendMessage("/login <密码>");
            }

        };
        executor.scheduleAtFixedRate(taskLogin, 0, 5, TimeUnit.SECONDS);

    }
}
