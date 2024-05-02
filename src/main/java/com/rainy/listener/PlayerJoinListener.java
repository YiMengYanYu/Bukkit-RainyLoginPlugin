package com.rainy.listener;

import com.google.inject.Inject;
import com.rainy.RainyLogin;
import com.rainy.entity.LoginConfig;
import com.rainy.util.config.RegConfigUtil;
import com.rainy.util.SessionUtil;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author YiMeng
 * @DateTime: 2024/4/4 0:04
 * @Description: TODO
 */
public class PlayerJoinListener implements Listener {

    @Inject
    private RainyLogin rainyLogin;


    @Inject
    private Logger logger;

    /**
     * 玩家进入时
     *
     * @param playerJoinEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void playerJoin(PlayerJoinEvent playerJoinEvent) {
        // 阻止默认的加入消息显示
        playerJoinEvent.setJoinMessage(null);
        Player player = playerJoinEvent.getPlayer();
        //设置玩家无敌
        player.setInvulnerable(true);

        //设置玩家初始位置
        setPlayerLocation(player);
        //查询是否注册成功
        if (isNoRegister(player)) {
            isNoLogin(player);
        }


    }

    /**
     * 玩家退出游戏时
     *
     * @param playerQuitEvent
     */
    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent playerQuitEvent) {
        //如果ip登录配置是关闭的
        if (!LoginConfig.ipLoginCheckSwitch) {
            Player player = playerQuitEvent.getPlayer();

            SessionUtil.destroySession(player.getName());

        }


    }

    /**
     * 检测是否注册
     *
     * @param player
     */
    private boolean isNoRegister(Player player) {
        //如果注册了
        if (RegConfigUtil.isRegister(player.getName())) {

            return true;
        }
        SessionUtil.destroySession(player.getName());


        new BukkitRunnable() {

            @Override
            public void run() {

                if (SessionUtil.getPlayerState(player.getName())) {
                    this.cancel();
                } else {

                    player.sendMessage("请注册后进入游戏");
                    player.sendMessage("/reg <密码> <密码>");
                }

            }
        }.runTaskTimer(rainyLogin, 0L, 100L);


        return false;

    }

    /**
     * 检测是否在登录状态
     *
     * @param player
     */
    private void isNoLogin(Player player) {

        if (SessionUtil.getPlayerState(player.getName())) {

            String playerIp = SessionUtil.getPlayerIpByName(player.getName());
            String ip = player.getAddress().getHostString();
            if (Objects.equals(playerIp, ip)) {
                //关闭无敌
                player.setInvulnerable(false);
                player.sendMessage(ChatColor.GREEN + "通过ip自动登录");
                return;
            }
            //如果登录的IP和上次不一致就先销毁session
            SessionUtil.destroySession(player.getName());
        }
        new BukkitRunnable() {

            @Override
            public void run() {

                if (SessionUtil.getPlayerState(player.getName())) {
                    this.cancel();
                } else {
                    player.sendMessage("请登录");
                    player.sendMessage("/login <密码>");
                }

            }
        }.runTaskTimer(rainyLogin, 0L, 100L);

    }

    private  void  setPlayerLocation(Player player)
    {

        int y = (int) player.getLocation().getY()-1; //获取玩家Y轴坐标既纵坐标
        Block block1 = player.getLocation().getBlock();
        if(block1.getType().equals(Material.AIR)){
            for(int a=y;;a--){
                Location location = new Location(player.getWorld(),player.getLocation().getX(),a,player.getLocation().getZ());
                //刷新玩家掉落预知位置
                Block block = player.getWorld().getBlockAt(location);
                //获取掉落位置方块
                if(!block.getType().equals(Material.AIR)&&!block.getType().equals(Material.WATER)){
                    //判断是否非空气并且并且不是水
                    location = new Location(player.getWorld(),player.getLocation().getX(),a+1,player.getLocation().getZ());
                    //更新位置
                    player.teleport(location);//把玩家扔过去
                    break;
                }
            }
        }
    }
}
