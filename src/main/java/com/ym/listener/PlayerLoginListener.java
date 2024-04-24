package com.ym.listener;

import com.ym.util.config.WhiteListConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.logging.Logger;

/**
 * @author YiMeng
 * @DateTime: 2024/4/24 下午6:30
 * @Description: 玩家登录事件监听
 */
public class PlayerLoginListener implements Listener {
    private  static Logger logger = Bukkit.getLogger();
    @EventHandler
    public  void  playerLoginEvent(PlayerLoginEvent event){

        logger.info( event.getPlayer().getName());
        String playerName = event.getPlayer().getName();
        if (WhiteListConfigUtil.whitelist ==null ||!WhiteListConfigUtil.whitelist.contains(playerName)) {
          //  event.setKickMessage("你还没有服务器白名单");
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST,"你还没有服务器白名单");
        }else {

            event.allow();
        }


    }


}
