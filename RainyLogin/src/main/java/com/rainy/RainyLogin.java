package com.rainy;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.rainy.command.BaiCommand;
import com.rainy.command.ChangePasswordCommand;
import com.rainy.command.LoginCommand;
import com.rainy.command.RegCommand;

import com.rainy.evevthandler.CancellationEventsHandler;
import com.rainy.guice.RainyModule;
import com.rainy.listener.PlayerLocationListener;
import com.rainy.listener.PlayerLoginListener;
import com.rainy.listener.PlayerNameListener;
import com.rainy.listener.PlayerWhiteListListener;
import com.rainy.util.config.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * @author YiMeng
 */
public final class RainyLogin extends JavaPlugin {


    private static Injector injector = null;

    public static <T> T getInstance(Class<T> type) {

        return injector.getInstance(type);
    }

    /**
     * 世界加载前
     */
    @Override
    public void onLoad() {

        injector = Guice.createInjector(new RainyModule(this));

        this.getLogger().info("RainyLogin开始加载配置文件");
        //创建玩家退出时的位置配置文件
        offlineLocationsConfigUtil.createOfflineLocationsConfig();
        //WebSocketClient.setJavaPlugin(this);
        //登录配置文件
        LoginConfigUtil.createLoginConfig();
        //白名单配置文件
        WhiteListConfigUtil.createWhiteListConfig();

        //密码强度配置文件
        PasswordPolicyConfigUtil.setConfig();

        //注册信息配置文件
        PlayerPasswordsUtil.createPlayerPasswords();

    }

    /**
     * 启动时
     */
    @Override
    public void onEnable() {


        //注册监听器
        Bukkit.getPluginManager().registerEvents(injector.getInstance(PlayerWhiteListListener.class), this);
        Bukkit.getPluginManager().registerEvents(injector.getInstance(PlayerLoginListener.class), this);
        Bukkit.getPluginManager().registerEvents(injector.getInstance(PlayerNameListener.class), this);
        Bukkit.getPluginManager().registerEvents(injector.getInstance(PlayerLocationListener.class), this);

        //取消事件处理器
        Bukkit.getPluginManager().registerEvents(injector.getInstance(CancellationEventsHandler.class), this);



        Bukkit.getPluginCommand("register").setExecutor(injector.getInstance(RegCommand.class));
        Bukkit.getPluginCommand("login").setExecutor(injector.getInstance(LoginCommand.class));
        Bukkit.getPluginCommand("bai").setExecutor(injector.getInstance(BaiCommand.class));
        Bukkit.getPluginCommand("changepassword").setExecutor(injector.getInstance(ChangePasswordCommand.class));




        this.getLogger().info("RainyLogin登录插件启动成功");


    }

    /**
     * 关闭时
     */
    @Override
    public void onDisable() {
        offlineLocationsConfigUtil.setAllOfflineLocation();

        // Plugin shutdown logic
        this.getLogger().info("RainyLogin插件关闭");
    }


}
