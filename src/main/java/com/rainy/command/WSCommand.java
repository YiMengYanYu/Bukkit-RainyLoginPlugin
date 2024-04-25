package com.rainy.command;

import com.rainy.websocket.WebSocketClient;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author YiMeng
 * @DateTime: 2024/4/25 上午11:47
 * @Description: TODO
 */
public class WSCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.isOp()) {
                return false;
            }
        }
        WebSocketClient.ws();
        Bukkit.getLogger().info("ws重启成功");
        return true;
    }
}
