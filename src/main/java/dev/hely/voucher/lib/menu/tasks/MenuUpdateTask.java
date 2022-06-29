package dev.hely.voucher.lib.menu.tasks;

//============================================================
// This file was created by DevDipin! Skidder
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: dev.hely.crates.lib.menu.tasks
//   Date: Friday, June 17, 2022 - 9:42 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import dev.hely.voucher.lib.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ConcurrentModificationException;

public class MenuUpdateTask extends BukkitRunnable {
    public void run() {
        try {
            Menu.getCurrentlyOpenedMenus().forEach((key, value) -> {
                Player player = Bukkit.getPlayer(key);
                if (player != null && value.isAutoUpdate()) {
                    value.openMenu(player);
                }
            });
        } catch (ConcurrentModificationException ex) {
        }
    }
}
