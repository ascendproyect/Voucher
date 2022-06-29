package dev.hely.voucher.lib.menu;

import dev.hely.voucher.lib.CC;
import dev.hely.voucher.lib.menu.button.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Created By LeandroSSJ
 * Created on 28/11/2021
 */

public class MenuListener implements Listener {
    private final JavaPlugin plugin;

    public MenuListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onButtonPress(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu openMenu = Menu.getCurrentlyOpenedMenus().get(player.getName());
        if (openMenu != null) {
            if (!openMenu.isAllowMoveInventory()) {

                if (player.getOpenInventory() != null && player.getOpenInventory().getTitle().equals(CC.translate(openMenu.getName(player)))) {
                    event.setCancelled(true);
                }
                if (event.getSlot() != event.getRawSlot()) {
                    if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                        event.setCancelled(true);
                    }
                    return;
                }
                if (openMenu.getMenuContent().containsKey(event.getSlot())) {
                    Button button = openMenu.getMenuContent().get(event.getSlot());
                    boolean cancel = button.shouldCancel(player, event.getClick());
                    if (!cancel && (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
                        event.setCancelled(true);
                        if (event.getCurrentItem() != null) {
                            player.getInventory().addItem(event.getCurrentItem());
                        }
                    } else {
                        event.setCancelled(cancel);
                    }
                    button.onClick(player, event.getClick());
                    button.onClick(player, event.getSlot(), event.getClick());
                    button.onClick(player, event.getSlot(), event.getClick(), event.getHotbarButton());
                    if (Menu.getCurrentlyOpenedMenus().containsKey(player.getName())) {
                        Menu newMenu = Menu.getCurrentlyOpenedMenus().get(player.getName());
                        if (newMenu == openMenu) {
                            boolean buttonUpdate = button.shouldUpdate(player, event.getClick());
                            if (buttonUpdate) {
                                openMenu.setClosedByMenu(true);
                                newMenu.openMenu(player);
                            }
                        }

                    } else if (button.shouldUpdate(player, event.getClick())) {
                        openMenu.setClosedByMenu(true);
                        openMenu.openMenu(player);
                    }
                    if (event.isCancelled()) {
                        Bukkit.getScheduler().runTaskLater(this.plugin, player::updateInventory, 1L);
                    }
                } else {
                    if ((event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || event.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD) || event.getAction().equals(InventoryAction.HOTBAR_SWAP))) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Menu openMenu = Menu.getCurrentlyOpenedMenus().get(player.getName());
        if (openMenu != null) {
            openMenu.onClose(player);
            Menu.getCurrentlyOpenedMenus().remove(player.getName());
        }
    }
}