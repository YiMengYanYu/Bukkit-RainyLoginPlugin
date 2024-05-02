package com.rainy.command;

import com.rainy.util.CommandUtil;
import com.rainy.util.SessionUtil;
import com.rainy.util.config.RegConfigUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/**
 * @author YiMeng
 * @DateTime: 2024/4/5 2:22
 * @Description: TODO
 */
public class LoginCommand implements CommandExecutor {
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

        Player player = CommandUtil.playerCommandHandler(sender, args, 1);
        String name = sender.getName();

        if (SessionUtil.getPlayerState(name)) {
            sender.sendMessage("您已经登录过了，请不要重复登录");
            return  true;
        }
        if (player == null) {
            return false;
        }

        String password = args[0];
        String loginPassword = RegConfigUtil.getData().getString(name + ".password");

        if (loginPassword == null) {
            sender.sendMessage("请先注册");
            return true;
        }

        if (password == null) {
            return false;
        }

        if (password.equals(loginPassword)) {
            sender.sendMessage(ChatColor.YELLOW + "登录成功");
            //登录成功后，玩家取消无敌
            player.setInvulnerable(false);
            SessionUtil.setPlayerEntityMapByPlayer(player);
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "密码错误");
            return false;
        }



    }


}
