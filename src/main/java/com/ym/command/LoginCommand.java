package com.ym.command;

import com.ym.util.ConfigUtil;
import com.ym.util.SessionUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * @author YiMeng
 * @DateTime: 2024/4/5 2:22
 * @Description: TODO
 */
public class LoginCommand implements CommandExecutor {
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
        String name = sender.getName();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                String password = args[0];
                String loginPassword = ConfigUtil.getData().getString(name + ".password");


                if (loginPassword == null){
                    sender.sendMessage("请先注册");
                    return false;
                }

                if (password == null){
                    return false;
                }

                if (password.equals(loginPassword)) {
                    sender.sendMessage(ChatColor.YELLOW+"登录成功");
                    SessionUtil.setPlayerEntityMapByPlayer(player);
                } else {
                    sender.sendMessage(ChatColor.RED+"密码错误");
                }
            } else {
                sender.sendMessage(ChatColor.RED+"密码参数错误");
                return false;
            }
            return true;
        }


        return false;
    }
}
