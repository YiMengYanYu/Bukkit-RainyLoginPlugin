package com.rainy;

import org.bukkit.plugin.java.JavaPlugin;

public class RainyBukkitBot extends JavaPlugin {


    @Override
    public void onLoad() {
        this.getServer().getPluginManager().isPluginEnabled("RainyLogin");

    }

    @Override
    public void onEnable() {
        super.onEnable();
    }


    @Override
    public void onDisable() {
        super.onDisable();
    }
}