package dev.hely.voucher.lib.menu;

import dev.hely.voucher.Voucher;
import dev.hely.voucher.lib.manager.Manager;
import dev.hely.voucher.lib.menu.tasks.MenuUpdateTask;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By LeandroSSJ
 * Created on 28/11/2021
 */

@Getter
public enum MenuManager implements Manager {

    INSTANCE;

    private final Map<UUID, Menu> menuData = new ConcurrentHashMap<>();

    @Override
    public void onEnable(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new MenuListener(Voucher.INSTANCE), plugin);
        new MenuUpdateTask().runTaskTimerAsynchronously(Voucher.INSTANCE, 20L, 20L);
    }
}
