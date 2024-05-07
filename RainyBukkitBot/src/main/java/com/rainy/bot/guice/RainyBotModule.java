package com.rainy.bot.guice;

import com.google.inject.AbstractModule;
import com.rainy.bot.RainyBukkitBot;
import com.rainy.bot.command.WSCommand;

public class RainyBotModule extends AbstractModule {


    public RainyBotModule() {
    }

    RainyBukkitBot rainyBukkitBot = null;

    public RainyBotModule(RainyBukkitBot rainyBukkitBot) {
        this.rainyBukkitBot = rainyBukkitBot;
    }

    @Override
    protected void configure() {

        bind(WSCommand.class).asEagerSingleton();
        bind(RainyBukkitBot.class).toInstance(rainyBukkitBot);

    }
}