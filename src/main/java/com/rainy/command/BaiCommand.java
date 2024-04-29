package com.rainy.command;

import com.rainy.entity.PlayerEntity;
import com.rainy.util.SessionUtil;
import com.rainy.util.config.WhiteListConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import java.util.logging.Logger;

/**
 * @author YiMeng
 * @DateTime: 2024/4/24 下午3:33
 * @Description: TODO
 */
public class BaiCommand implements TabExecutor {

    private static final Logger logger =Bukkit.getLogger();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {



        if (args.length<=1||args[0]==null){
            sender.sendMessage("请输入正确的命令");
            return false;
        }

        switch (args[0]){
            case "add":
                if (args.length==2||args[1]!=null){
                    WhiteListConfigUtil.whitelist.add(args[1]);
                    WhiteListConfigUtil.saveConfig();

                    sender.sendMessage("保存白名单成功");


                }
                break;
            case "delete":
                if (args.length==2||args[1]!=null){
             //  PlayerEntity playerEntity = SessionUtil.getPlayerEntityByPlayerName(args[1]);


                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kick "+args[1]);
                    WhiteListConfigUtil.whitelist.remove(args[1]);

                    WhiteListConfigUtil.saveConfig();
                    sender.sendMessage("删除白名单成功");

                }
                break;

            default:
                sender.sendMessage("请输入正确的命令");
                break;
        }
        return false;
    }

    /**
     * tab命令补全
     * @param sender 发送者
     * @param command 执行的命令
     * @param alias 使用的别名
     * @param args 传递给命令的参数，包括final
     *
     * @return
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        LinkedList<String> tips = new LinkedList<>();

        //处理第一个参数
        if (args.length == 1) {
            //如果只输入一个空格时
            List<String> firstArgList = Arrays.asList("add", "delete");
            if (args[0].isEmpty()) {
                //添加所有信息
                tips.addAll(firstArgList);
                return tips;
            } else {
                //已经开始输入字符了，则遍历已有信息，并将 信息的小写toLowerCase()通过startWith()检查该arg[0]的小写是否匹配
                for (String firstArg : firstArgList) {
                    if (firstArg.toLowerCase().startsWith(args[0].toLowerCase())) {
                        tips.add(firstArg);
                    }
                }
                return tips;
            }
        }

        return tips;

    }
}