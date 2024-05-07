package com.rainy.guice;

import com.google.inject.AbstractModule;
import com.rainy.RainyLogin;
import com.rainy.command.BaiCommand;
import com.rainy.command.LoginCommand;
import com.rainy.command.RegCommand;

import com.rainy.evevthandler.CancellationEventsHandler;
import com.rainy.listener.PlayerLocationListener;
import com.rainy.listener.PlayerLoginListener;
import com.rainy.listener.PlayerNameListener;
import com.rainy.listener.PlayerWhiteListListener;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginLogger;

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



        bind(ConsoleCommandSender.class).toInstance(Bukkit.getConsoleSender());


        bind(PlayerLoginListener.class).asEagerSingleton();
        bind(PlayerWhiteListListener.class).asEagerSingleton();
        bind(CancellationEventsHandler.class).asEagerSingleton();

        bind(BaiCommand.class).asEagerSingleton();
        bind(LoginCommand.class).asEagerSingleton();
        bind(RegCommand.class).asEagerSingleton();
        // bind(WSCommand.class).asEagerSingleton();

        bind(PlayerLocationListener.class).asEagerSingleton();
        bind(PlayerNameListener.class).asEagerSingleton();
    }

//    @Provides
//    @Singleton
//    private PlayerLoginListener providePlayerJoinListener() {
//
//        return new PlayerLoginListener();
//    }
}
