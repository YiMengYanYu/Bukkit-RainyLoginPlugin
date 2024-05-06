package com.rainy.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

/**
 * @author YiMeng
 * @DateTime: 2024/5/6 下午2:35
 * @Description: 检测玩家名称是否符合要求
 */
public class PlayerNameListener implements Listener {


    @EventHandler
    public void onPlayerLogin(PlayerPreLoginEvent event) {
        String playerName = event.getName();

        if (!matches(playerName)) {
            event.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, "§c§l你的名字含有非法字符，请修改后重试！");
        }
        if (playerName.length()<=1){
            event.disallow(PlayerPreLoginEvent.Result.KICK_OTHER,"§c§l你的名称长度不符合要求");
        }

    }

    public static boolean matches(String playerName) {
        String regex = "^[a-zA-Z0-9_]*$";
        return playerName.matches(regex);
    }
}
