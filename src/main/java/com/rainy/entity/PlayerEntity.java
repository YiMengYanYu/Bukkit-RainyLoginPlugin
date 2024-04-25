package com.rainy.entity;

import org.bukkit.entity.Player;

/**
 * @author YiMeng
 * @DateTime: 2024/4/12 3:55
 * @Description: 存储登录的玩家信息
 */
public class PlayerEntity {
    private Player player;

    private String playerName;

    private String playerIp;


    public PlayerEntity(Player player, String playerName, String playerIp) {
        this.player = player;
        this.playerName = playerName;
        this.playerIp = playerIp;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerIp() {
        return playerIp;
    }

    public void setPlayerIp(String playerIp) {
        this.playerIp = playerIp;
    }
}
