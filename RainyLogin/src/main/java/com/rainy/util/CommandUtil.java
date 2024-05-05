package com.rainy.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author YiMeng
 * @DateTime: 2024/4/13 21:30
 * @Description: TODO
 */
public class CommandUtil {

    /**
     * 玩家命令处理器
     * @param
     */
    public static Player playerCommandHandler(CommandSender sender,String[] param,int paramLength){

        if (sender instanceof Player) {
           Player player =(Player) sender;

            if (param.length!=paramLength) {
                player.sendMessage(ChatColor.RED+"命令参数异常");
                return null;
            }
                return player;
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"控制台禁止执行该命令");

        return null;
    }



}
