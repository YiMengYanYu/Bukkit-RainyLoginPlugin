package com.ym.command;

import com.ym.util.CommandUtil;
import com.ym.util.PasswordCheckUtil;
import com.ym.util.SessionUtil;
import com.ym.util.config.ConfigUtil;
import com.ym.util.config.RegConfigUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YiMeng
 * @DateTime: 2024/4/5 2:23
 * @Description: TODO
 */
public class RegCommand implements CommandExecutor {

    /**
     * 如果是有效命令，则为true，否则为false
     *
     * @param sender  命令的来源
     * @param command 执行的命令
     * @param label   使用的命令的别名
     * @param args    传递的命令参数
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = CommandUtil.playerCommandHandler(sender, args, 2);
        if (player==null) {
            return false;
        }

        if (!args[0].equals(args[1])) {
            sender.sendMessage(ChatColor.YELLOW + "两次输入的密码不一致");
            return false;
        }
        String name = player.getName();
        String uuid = player.getUniqueId().toString();
        regPlayer(name, uuid, args[0], (Player) sender);

        return true;
    }


    /**
     * 注册玩家
     *
     * @param name     玩家名字
     * @param uuid     玩家uuid
     * @param password 玩家密码
     * @param player   命令的来源
     */
    private void regPlayer(String name, String uuid, String password, Player player) {



        if (!PasswordCheckUtil.checkPassword(password,player)) {
            return;
        }
        Map<String, String> hashMap = new HashMap(8);
        YamlConfiguration data = RegConfigUtil.getData();
        if (data.get(name) != null) {
            player.sendMessage(ChatColor.RED + "您已经注册过了,不可以重复注册");
            return;
        }






        hashMap.put("uuid", uuid + "");
        hashMap.put("password", password + "");
        data.set(name, hashMap);
        try {
            data.save(RegConfigUtil.getFile());
            player.sendMessage(ChatColor.YELLOW + "注册成功");

            player.setInvulnerable(false);
            //注册后写入session
            SessionUtil.setPlayerEntityMapByPlayer(player);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
