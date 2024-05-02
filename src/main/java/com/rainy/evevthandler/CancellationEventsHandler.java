package com.rainy.evevthandler;

import com.rainy.util.SessionUtil;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

/**
 * @author YiMeng
 * @DateTime: 2024/4/12 1:03
 * @Description: 取消事件处理程序
 */
public class CancellationEventsHandler implements Listener {

    /**
     * 处理事件
     *
     * @param event
     */
    private void handleEvents(PlayerEvent event) {
        if (event instanceof Cancellable) {
            String name = event.getPlayer().getName();
            if (!SessionUtil.getPlayerState(name)) {
                ((Cancellable) event).setCancelled(true);
            }
        }


    }
    /**
     * 当玩家移动时，该事件发生
     *
     * @param event
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        handleEvents(event);
    }

    /**
     * 当玩家执行一个命令时，该事件发生
     *
     * @param event
     */
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (!stringContainsAny(event.getMessage(), new String[]{"/login", "/reg"})) {
            handleEvents(event);
        }
    }

    /**
     * 当玩家发送聊天消息时，该事件发生
     *
     * @param event
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        handleEvents(event);
    }

    /**
     * 当玩家与一个物品互动时，该事件发生
     *
     * @param event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        handleEvents(event);
    }

    /**
     * 当玩家以导致其收集或“收获”的方式与一个方块互动时，该事件发生
     * @param event
     */
    // @EventHandler
    // public void onPlayerBreak(PlayerHarvestBlockEvent event) {
    //   handleEvents(event);
    //}


    /**
     * 玩家物品损坏事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerDamage(PlayerItemDamageEvent event) {
        handleEvents(event);
    }
    /**
     * 玩家速度事件
     *
     * @param event
     */
    @EventHandler
    public void playerVelocityEvent(PlayerVelocityEvent event) {
        handleEvents(event);

    }


    /**
     * 物品丢弃事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        handleEvents(event);
    }

    /**
     * 字符串中是否包含某个字符串
     *
     * @param input
     * @param params
     * @return
     */
    public boolean stringContainsAny(String input, String[] params) {
        for (String str : params) {
            if (input.contains(str)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 处理InventoryEvent事件
     *
     * @param event
     */
    private void handleInventoryEvents(InventoryEvent event) {
        if (event instanceof Cancellable) {

            HumanEntity humanEntity = event.getViewers().get(0);
            String name   =humanEntity.getName();
            if (!SessionUtil.getPlayerState(name)) {
                ((Cancellable) event).setCancelled(true);
            }
        }


    }


    /**
     * 当玩家点击物品栏时触发本事件
     *
     * @param event
     */
    @EventHandler
    public void playerclickinvevent(InventoryClickEvent event) {
        handleInventoryEvents(event);
    }

    /**
     * 当玩家打开物品栏时触发本事件
     *
     * @param event
     */
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        handleInventoryEvents(event);
    }

    /**
     * 当玩家制作物品时触发本事件
     *
     * @param event
     */
    @EventHandler
    public void craftItemEvent(CraftItemEvent event) {

        handleInventoryEvents(event);

    }

    /**
     * 当玩家传送时触发本事件
     *
     * @param event
     */
    @EventHandler
    public  void  playerPortalEvent(PlayerPortalEvent event){

        handleEvents(event);

    }

    /**
     * 玩家切换左右手
     * @param event
     */
    @EventHandler
    public  void playerChangedMainHandEvent (PlayerChangedMainHandEvent event){

        handleEvents(event);

    }


}
