package com.ym.command;

import com.ym.util.ConfigUtil;
import com.ym.util.SessionUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author YiMeng
 * @DateTime: 2024/4/5 2:23
 * @Description: TODO
 */
public class RegCommand implements CommandExecutor {

    /**
     * 如果是有效命令，则为true，否则为false
     * @param sender 命令的来源
     * @param command 执行的命令
     * @param label 使用的命令的别名
     * @param args 传递的命令参数
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length!=2){
                sender.sendMessage(ChatColor.YELLOW+"请输入两次密码");
                for (int i = 0; i < args.length; i++) {
                    System.out.println(i+1+"是"+args[i]);
                }
                return false;
            }


            if (!args[0].equals(args[1])){
                sender.sendMessage(ChatColor.YELLOW+"两次输入的密码不一致");

                return false;

            }

            Player player = ((Player) sender).getPlayer();
            String name = player.getName();
            String uuid = player.getUniqueId().toString();
            regPlayer(name,uuid,args[0],sender);

            return true;
        }
        System.out.println("控制台禁止执行注册命令");
        return  true;
    }

    /**
     * 注册玩家
     * @param name 玩家名字
     * @param uuid 玩家uuid
     * @param password 玩家密码
     * @param sender 命令的来源
     */
    private  void  regPlayer(String name,String uuid ,String password,CommandSender sender){

        Map<String,String> hashMap=new HashMap(16);
        YamlConfiguration data = ConfigUtil.getData();
        if (data.get(name)!=null) {
            sender.sendMessage(ChatColor.RED+"您已经注册过了,不可以重复注册");
            return;
        }
        hashMap.put("uuid",uuid);
        hashMap.put("password",password);
        data.set(name,hashMap);
        try {
            data.save(ConfigUtil.getFile());
            sender.sendMessage(ChatColor.YELLOW+"注册成功");
            SessionUtil.setPlayerEntityMapByPlayer((Player) sender);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
