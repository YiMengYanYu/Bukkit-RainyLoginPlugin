package com.rainy.listener;

import com.google.inject.Inject;
import com.rainy.entity.LoginConfig;
import com.rainy.util.config.WhiteListConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Set;
import java.util.logging.Logger;

/**
 * @author YiMeng
 * @DateTime: 2024/4/24 下午6:30
 * @Description: 玩家登录事件监听
 */
public class PlayerLoginListener implements Listener {
    @Inject
    private   Logger logger ;
    @EventHandler
    public  void  playerLoginEvent(PlayerLoginEvent event){


        String playerName = event.getPlayer().getName();


        //如果白名单没有开就不拦截
        if(!LoginConfig.whitelistCheckSwitch){

            return;

        }

        if (WhiteListConfigUtil.whitelist ==null ||!WhiteListConfigUtil.whitelist.contains(playerName)) {
          //  event.setKickMessage("你还没有服务器白名单");
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST,"你还没有服务器白名单");
        }


    }


}
