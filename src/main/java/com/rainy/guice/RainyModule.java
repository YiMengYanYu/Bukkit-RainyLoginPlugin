package com.rainy.guice;

import com.google.inject.AbstractModule;
import com.rainy.RainyLogin;
import com.rainy.command.BaiCommand;
import com.rainy.command.LoginCommand;
import com.rainy.command.RegCommand;
import com.rainy.command.WSCommand;
import com.rainy.evevthandler.CancellationEventsHandler;
import com.rainy.listener.PlayerJoinListener;
import com.rainy.listener.PlayerLoginListener;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginLogger;

import java.util.logging.Logger;

/**
 * @author YiMeng
 * @DateTime: 2024/4/29 下午9:13
 * @Description: TODO
 */
public class RainyModule extends AbstractModule {

    private RainyLogin rainyLogin;

    public RainyModule(RainyLogin rl) {
        this.rainyLogin = rl;
    }

    @Override
    protected void configure() {

        bind(RainyLogin.class).toInstance(rainyLogin);
        bind(PluginLogger.class).toInstance((PluginLogger) rainyLogin.getLogger());


        bind(ConsoleCommandSender.class).toInstance(Bukkit.getConsoleSender());



        bind(PlayerJoinListener.class).asEagerSingleton();
        bind(PlayerLoginListener.class).asEagerSingleton();
        bind(CancellationEventsHandler.class).asEagerSingleton();

        bind(BaiCommand.class).asEagerSingleton();
        bind(LoginCommand.class).asEagerSingleton();
        bind(RegCommand.class).asEagerSingleton();
        bind(WSCommand.class).asEagerSingleton();
    }

//    @Provides
//    @Singleton
//    private PlayerJoinListener providePlayerJoinListener() {
//
//        return new PlayerJoinListener();
//    }
}
