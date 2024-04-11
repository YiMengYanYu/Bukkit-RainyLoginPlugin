package com.ym.ymlogin;

import com.ym.command.LoginCommand;
import com.ym.command.RegCommand;
import com.ym.evevtmanager.CancellationEventsHandler;
import com.ym.listener.PlayerListener;

import com.ym.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YiMeng
 */
public final class YMLogin extends JavaPlugin {

    /**
     * 世界加载前
     */
    @Override
    public void onLoad() {
        Bukkit.getConsoleSender().sendMessage("§a世界正在加载");


    }

    /**
     * 启动时
     */
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§a 插件启动成功");
        //注册玩家监听器
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new CancellationEventsHandler(), this);
        ConfigUtil.loadData(this);
        YamlConfiguration data = ConfigUtil.getData();

        Bukkit.getConsoleSender().sendMessage("§"+data.toString());
        Bukkit.getPluginCommand("register").setExecutor(new RegCommand());
        Bukkit.getPluginCommand("login").setExecutor(new LoginCommand());




    }

    /**
     * 关闭时
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("§a 插件关闭");
    }




}
