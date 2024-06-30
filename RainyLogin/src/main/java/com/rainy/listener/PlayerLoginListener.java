package com.rainy.listener;

import com.google.inject.Inject;
import com.rainy.RainyLogin;
import com.rainy.util.SessionUtil;
import com.rainy.util.config.PlayerPasswordsUtil;
import com.rainy.util.config.offlineLocationsConfigUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author YiMeng
 * @DateTime: 2024/4/4 0:04
 * @Description: 检测是否登录
 */
public class PlayerLoginListener implements Listener {

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

        //传送到出生点
        World world = Bukkit.getWorld("world");
        Location spawnLocation = world.getSpawnLocation();
        player.teleport(spawnLocation);

        //设置玩家初始位置
        //    setPlayerLocation(player);
        //查询是否注册成功
        if (isNoRegister(player)) {
            isNoLogin(player);
        }


    }


    /**
     * 检测是否注册
     *
     * @param player
     */
    private boolean isNoRegister(Player player) {
        //如果注册了
        if (PlayerPasswordsUtil.isRegister(player.getName())) {

            return true;
        }
        SessionUtil.destroySession(player.getName());


        new  BukkitRunnable() {

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

            if (Objects.equals(playerIp, ip) && !Objects.equals(ip,"127.0.0.1") && !ip.startsWith("192.168")) {
                //关闭无敌
                player.setInvulnerable(false);
                player.sendMessage(ChatColor.GREEN + "通过ip自动登录");
                //回到玩家上次的位置
                player.teleport(offlineLocationsConfigUtil.getLocationByPlayerName(player.getName()));
                return;
            }
            //如果登录的IP和上次不一致或者是本地IP地址就先销毁session
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


    private void setPlayerLocation(Player player) {
        if (player.isOnGround()) {
            return;
        }

        int y = (int) player.getLocation().getY() - 1; //获取玩家Y轴坐标既纵坐标
        Double x = ((int) player.getLocation().getX()) + 0.5;
        Double z = ((int) player.getLocation().getZ()) + 0.5;
        logger.info("========================================");
        logger.info("x" + x + "y" + y + "z" + z);
        Block block1 = player.getLocation().getBlock();
        if (block1.getType().equals(Material.AIR)) {
            while (true) {
                //获取遍历到的那个坐标
                Location location = new Location(player.getWorld(), x, y, z);
                //刷新玩家掉落预知位置
                Block block = player.getWorld().getBlockAt(location);

                Material blockType = block.getType();

                //获取掉落位置方块
                if (!blockType.equals(Material.AIR)) {


                    //更新位置
                    player.teleport(location.add(0, 1, 0));//把玩家扔过去
                    break;
                }
                y--;
            }
        }
    }
}
