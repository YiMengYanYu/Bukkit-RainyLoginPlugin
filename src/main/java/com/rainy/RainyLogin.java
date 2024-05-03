package com.rainy;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.rainy.command.BaiCommand;
import com.rainy.command.LoginCommand;
import com.rainy.command.RegCommand;
import com.rainy.command.WSCommand;
import com.rainy.evevthandler.CancellationEventsHandler;
import com.rainy.guice.RainyModule;
import com.rainy.listener.PlayerJoinListener;
import com.rainy.listener.PlayerLoginListener;
import com.rainy.util.config.LoginConfigUtil;
import com.rainy.util.config.PasswordPolicyConfigUtil;
import com.rainy.util.config.RegConfigUtil;
import com.rainy.util.config.WhiteListConfigUtil;
import com.rainy.websocket.WebSocketClient;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;


/**
 * @author YiMeng
 *
 */

public final class RainyLogin extends JavaPlugin {



    /**
     * 世界加载前
     */
    @Override
    public void onLoad() {


        this.getLogger().info("RainyLogin开始加载配置文件");

        WebSocketClient.setJavaPlugin(this);
        //登录配置文件
        LoginConfigUtil.createLoginConfig(this);
        //白名单配置文件
        WhiteListConfigUtil.createWhiteListConfig(this);
        //密码配置文件
        PasswordPolicyConfigUtil.setConfig(this);

        //注册配置文件
        RegConfigUtil.loadData();
        //尝试启动ws
        WebSocketClient.ws();
    }

    /**
     * 启动时
     */
    @Override
    public void onEnable() {
        Injector injector = Guice.createInjector(new RainyModule(this));




        //注册玩家监听器
        Bukkit.getPluginManager().registerEvents(injector.getInstance(PlayerJoinListener.class), this);
        //取消事件处理器
        Bukkit.getPluginManager().registerEvents(injector.getInstance(CancellationEventsHandler.class), this);
        Bukkit.getPluginManager().registerEvents(injector.getInstance(PlayerLoginListener.class), this);


        Bukkit.getPluginCommand("register").setExecutor(injector.getInstance(RegCommand.class));
        Bukkit.getPluginCommand("login").setExecutor(injector.getInstance(LoginCommand.class));
        Bukkit.getPluginCommand("bai").setExecutor(injector.getInstance(BaiCommand.class));

        Bukkit.getPluginCommand("rws").setExecutor(injector.getInstance(WSCommand.class));


        this.getLogger().info("RainyLogin登录插件启动成功");


    }

    /**
     * 关闭时
     */
    @Override
    public void onDisable() {

        // Plugin shutdown logic
        this.getLogger().info("RainyLogin插件关闭");
    }





}
