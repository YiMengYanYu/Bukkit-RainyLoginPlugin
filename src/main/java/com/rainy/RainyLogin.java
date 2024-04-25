package com.rainy;

import com.rainy.command.BaiCommand;
import com.rainy.command.LoginCommand;
import com.rainy.command.RegCommand;
import com.rainy.command.WSCommand;
import com.rainy.evevtmanager.CancellationEventsHandler;
import com.rainy.listener.PlayerJoinListener;

import com.rainy.listener.PlayerLoginListener;
import com.rainy.util.config.LoginConfigUtil;
import com.rainy.util.config.PasswordConfigSetUtil;
import com.rainy.util.config.RegConfigUtil;

import com.rainy.util.config.WhiteListConfigUtil;
import com.rainy.websocket.WebSocketClient;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * @author YiMeng
 */
public final class RainyLogin extends JavaPlugin {

    /**
     * 世界加载前
     */
    @Override
    public void onLoad() {
        WebSocketClient.setJavaPlugin(this);
        //登录配置文件
        LoginConfigUtil.createLoginConfig(this);
        //白名单配置文件
        WhiteListConfigUtil.createWhiteListConfig(this);
        PasswordConfigSetUtil.setConfig(this);
        Bukkit.broadcastMessage("服务器已刷新请重新登录");
        Bukkit.broadcastMessage("/login <密码>");
        //注册配置文件
        RegConfigUtil.loadData();
        WebSocketClient.ws();
    }

    /**
     * 启动时
     */
    @Override
    public void onEnable() {


        Bukkit.getConsoleSender().sendMessage("§a登录插件启动成功");
        //注册玩家监听器
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new CancellationEventsHandler(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(), this);


        YamlConfiguration data = RegConfigUtil.getData();

        Bukkit.getConsoleSender().sendMessage("§"+data.toString());
        Bukkit.getPluginCommand("register").setExecutor(new RegCommand());
        Bukkit.getPluginCommand("login").setExecutor(new LoginCommand());
        Bukkit.getPluginCommand("bai").setExecutor(new BaiCommand());

        Bukkit.getPluginCommand("rws").setExecutor(new WSCommand());





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
