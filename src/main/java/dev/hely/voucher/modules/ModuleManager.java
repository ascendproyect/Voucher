package dev.hely.voucher.modules;

import dev.hely.voucher.lib.manager.Manager;
import dev.hely.voucher.modules.item.ItemManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public enum ModuleManager implements Manager {

    INSTANCE;

    private ItemManager itemManager;

    @Override
    public void onEnable(JavaPlugin plugin) {
        itemManager = new ItemManager();
    }

    @Override
    public void onDisable(JavaPlugin plugin) {
        itemManager.onDisable();
    }
}
