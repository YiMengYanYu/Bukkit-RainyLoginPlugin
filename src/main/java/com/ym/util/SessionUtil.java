package com.ym.util;

import com.ym.entity.PlayerEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YiMeng
 * @DateTime: 2024/4/12 2:54
 * @Description: 玩家登录信息操作的工具类
 */
public class SessionUtil {

    /**
     * 根据玩家名称 存储玩家实体
     */
    private static Map<String, PlayerEntity> playerEntityMap = new HashMap<>(16);


    /**
     * 通过玩家名称 获取玩家实体
     * @param playerName
     * @return
     */
    public static PlayerEntity getPlayerEntityByPlayerName(String playerName) {
        PlayerEntity playerEntity = playerEntityMap.get(playerName);

        return playerEntity;
    }
    /**
     * 获取玩家ip
     * @param playerName
     * @return
     */
    public static String getPlayerIpByName(String playerName) {

        PlayerEntity playerEntity = getPlayerEntityByPlayerName(playerName);
        if (playerEntity==null) {
            return  null;
        }

        return playerEntity.getPlayerIp();
    }


    /**
     * 存储玩家实体
     * @param player
     */
    public  static void  setPlayerEntityMapByPlayer(Player player){
        String playerName = player.getName();
        String ip = player.getAddress().getHostString();
        PlayerEntity playerEntity =new PlayerEntity(player,playerName,ip);
        playerEntityMap.put(playerName,playerEntity);
    }

    
    public static boolean getPlayerState(String playerName) {
        PlayerEntity playerEntity = playerEntityMap.get(playerName);
        return playerEntity!=null;
    }

    /**
     * 删除对应玩家实体
     * @param playerName
     */
    public static  void  destroySession(String playerName) {
        playerEntityMap.remove(playerName);
    }

}
