package com.rainy.evevthandler;

import com.rainy.util.SessionUtil;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * @author YiMeng
 * @DateTime: 2024/4/12 1:03
 * @Description: 本类只做取消未登录的不合法事件
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

        Player player = event.getPlayer();
        String name = player.getName();
        if (!SessionUtil.getPlayerState(name)) {
            Location from = event.getFrom();
            Location to = event.getTo();

            double dx = from.getX() - to.getX();

            double dz = from.getZ() - to.getZ();


            // 如果是水平移动，取消事件
            if (Math.abs(dx) > 0 || Math.abs(dz) > 0) {
                event.setCancelled(true);
            }


        }
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
            String name = humanEntity.getName();
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
    public void playerPortalEvent(PlayerPortalEvent event) {

        handleEvents(event);

    }

    /**
     * 玩家切换左右手
     *
     * @param event
     */
    @EventHandler
    public void playerChangedMainHandEvent(PlayerChangedMainHandEvent event) {

        handleEvents(event);

    }

    /**
     * 当玩家点击一个实体时调用此事件.
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

        // 处理玩家与实体交互的事件
        handleEvents(event);
    }

    /**
     * 当玩家在实体上点击某实体上的某位置时触发此事件.
     *
     * @param event
     */
    @EventHandler
    public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {

        // 处理玩家与实体交互的事件
        handleEvents(event);
    }

    /**
     * 当一个实体受到另外一个实体伤害时触发该事件
     *
     * @param event
     */
    @EventHandler
    public static void playerattack(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player p1 = (Player) event.getDamager();
            Player p2 = (Player) event.getEntity();

            if (event instanceof Cancellable) {
                String name = p1.getName();
                if (!SessionUtil.getPlayerState(name)) {
                    ((Cancellable) event).setCancelled(true);
                }
            }
        }
    }
}
