package com.ym.util;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * @author YiMeng
 * @DateTime: 2024/4/11 23:33
 * @Description: TODO
 */
public abstract class EventUtil extends PlayerEvent implements Cancellable {

    public EventUtil(Player who) {
        super(who);
    }
}
