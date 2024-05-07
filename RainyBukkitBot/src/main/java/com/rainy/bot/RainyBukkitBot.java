package com.rainy.bot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.rainy.bot.config.BotConfig;
import com.rainy.bot.command.WSCommand;
import com.rainy.bot.guice.RainyBotModule;
import com.rainy.bot.handler.ConsoleCommandSenderProxy;
import com.rainy.bot.websocket.WebSocketClient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class RainyBukkitBot extends JavaPlugin {

    private static Injector injector;

    public static <T> T getInstance(Class<T> type) {
        return injector.getInstance(type);
    }

    @Override
    public void onLoad() {

        this.getServer().getPluginManager().isPluginEnabled("RainyLogin");


        injector = Guice.createInjector(new RainyBotModule(this));


    }

    @Override
    public void onEnable() {
        Bukkit.getPluginCommand("rws").setExecutor(injector.getInstance(WSCommand.class));
        BotConfig.createBotConfig();
        WebSocketClient.setJavaPlugin(this);
        WebSocketClient.ws();

    }


    @Override
    public void onDisable() {
        super.onDisable();
    }
}