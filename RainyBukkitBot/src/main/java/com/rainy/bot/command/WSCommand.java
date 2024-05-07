package com.rainy.bot.command;

import com.google.inject.Inject;
import com.rainy.bot.websocket.WebSocketClient;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

/**
 * @author YiMeng
 * @DateTime: 2024/4/25 上午11:47
 * @Description: TODO
 */
public class WSCommand implements CommandExecutor {

    @Inject
    private Logger logger;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.isOp()) {
                return false;
            }
        }

        WebSocketClient.ws();
        logger.info("ws重启成功");
        return true;
    }
}
