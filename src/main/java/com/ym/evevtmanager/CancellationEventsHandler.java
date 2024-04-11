package com.ym.evevtmanager;

import com.ym.util.SessionUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

/**
 * @author YiMeng
 * @DateTime: 2024/4/12 1:03
 * @Description: TODO
 */
public class CancellationEventsHandler implements Listener {


    private void handleEvents(PlayerEvent event) {
        if (event instanceof Cancellable) {
            String name = event.getPlayer().getName();
            if (!SessionUtil.getPlayerState(name)) {
                ((Cancellable) event).setCancelled(true);
            }

        }


    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        handleEvents(event);
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (!stringContainsAny(event.getMessage(), new String[] { "/login","/reg"})) {
            handleEvents(event);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        handleEvents(event);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        handleEvents(event);
    }

    @EventHandler
    public void onPlayerBreak(PlayerHarvestBlockEvent event) {
        handleEvents(event);
    }

    @EventHandler
    public void onPlayerDamage(PlayerItemDamageEvent event) {
        handleEvents(event);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        handleEvents(event);
    }

    public  boolean stringContainsAny(String input, String[] params) {
        for(String str : params) {
            if (input.contains(str)) {
                return true;
            }
        }
        return false;
    }


        /**
     * 当玩家打开物品栏时触发本事件
     *
     * @param event
     */
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
       event.getPlayer().sendMessage("你打开了物品栏");


    }
}
