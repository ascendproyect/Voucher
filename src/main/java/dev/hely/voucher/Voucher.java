package dev.hely.voucher;

import com.google.common.collect.ImmutableList;
import dev.hely.voucher.lib.CC;
import dev.hely.voucher.lib.command.CommandManager;
import dev.hely.voucher.lib.manager.Manager;
import dev.hely.voucher.lib.menu.MenuManager;
import dev.hely.voucher.modules.ModuleManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public class Voucher extends JavaPlugin {

    @Getter
    public static Voucher INSTANCE;
    private ModuleManager modules;
    private List<Manager> managerList;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.saveDefaultConfig();
        this.saveConfig();
        this.reloadConfig();
        this.registerManagers();
        this.modules = new ModuleManager();

        CC.logConsole("&7&m==================================================================================");
        CC.logConsole("");
        CC.logConsole("&9&lVoucher");
        CC.logConsole("");
        CC.logConsole("&9Author&7:&r Hely Development");
        CC.logConsole("&9Version&7:&r " + INSTANCE.getDescription().getVersion());
        CC.logConsole("&7&m==================================================================================");
    }

    @Override
    public void onDisable() {
        managerList.forEach(manager -> manager.onDisable(this));
        modules.onDisable();
    }

    public void registerManagers(){
        managerList = ImmutableList.of(
                CommandManager.INSTANCE,
                MenuManager.INSTANCE
        );

        managerList.forEach(manager -> manager.onEnable(this));
        CC.logConsoleFormated("&aLoading " + this.managerList.size() + " managers..");
    }
}
