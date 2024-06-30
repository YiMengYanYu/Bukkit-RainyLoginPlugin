package com.rainy.command;

import com.google.inject.Inject;
import com.rainy.util.CommandUtil;
import com.rainy.util.PasswordCheckUtil;
import com.rainy.util.config.PlayerPasswordsUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author YiMeng
 * @DateTime: 2024/6/30 下午2:20
 * @Description: 更改密码命令
 */
public class ChangePasswordCommand implements CommandExecutor {

    @Inject
    private Logger logger;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = CommandUtil.playerCommandHandler(sender, args, 2);
        if (player == null) {
            return false;
        }
        String oldPassword = args[0];
        String newPassword = args[1];
        //判断是否注册
        if (PlayerPasswordsUtil.isRegister(player.getName())) {
            //判断旧密码是否正确
            if (PlayerPasswordsUtil.getPlayerPassword(player.getName()).equals(oldPassword)) {
                //判断新密码是否符合要求
                if (PasswordCheckUtil.checkPassword(newPassword,player)) {
                    try {
                        if (PlayerPasswordsUtil.changePlayerPassword(player.getName(),newPassword)) {
                            player.sendMessage(ChatColor.YELLOW+"修改密码成功");
                        }

                    } catch (IOException e) {
                        logger.info("修改密码时产生"+e.getMessage());
                    }
                }

            } else {
                player.sendMessage("旧密码输入错误无法修改密码");
            }

        } else {
            player.sendMessage("未注册请先注册");
        }

        return true;
    }

//    @Override
//    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
//        return List.of();
//    }
}
