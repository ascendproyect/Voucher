package dev.hely.voucher;

import com.google.common.collect.ImmutableList;
import dev.hely.voucher.lib.CC;
import dev.hely.voucher.lib.command.CommandManager;
import dev.hely.voucher.lib.configuration.Config;
import dev.hely.voucher.lib.manager.Manager;
import dev.hely.voucher.lib.menu.MenuManager;
import dev.hely.voucher.listeners.InteractItem;
import dev.hely.voucher.modules.ModuleManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;


public class Voucher extends JavaPlugin {

    public static Voucher INSTANCE;
    private List<Manager> managerList;
    @Getter private Config voucher;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.saveDefaultConfig();
        this.saveConfig();
        this.reloadConfig();
        this.registerConfigs();
        this.registerManagers();
        this.registerListeners();

        CC.logConsole("&7&m==================================================================================");
        CC.logConsole("");
        CC.logConsole("&9&lVoucher");
        CC.logConsole("");
        CC.logConsole("&9Author&7:&r Hely Development");
        CC.logConsole("&9Version&7:&r " + INSTANCE.getDescription().getVersion());
        CC.logConsole("&9Discord&7: https://discord.helydev.com");
        CC.logConsole("&7&m==================================================================================");
    }

    @Override
    public void onDisable() {
        if(managerList != null) managerList.forEach(manager -> manager.onDisable(this));
    }

    public void registerManagers(){
        managerList = ImmutableList.of(
                CommandManager.INSTANCE,
                MenuManager.INSTANCE,
                ModuleManager.INSTANCE
        );

        managerList.forEach(manager -> manager.onEnable(this));
        CC.logConsoleFormated("&aLoading " + this.managerList.size() + " managers..");
    }

    public void registerConfigs() {
        try {
            this.voucher = new Config(this, "vouchers.yml");
        } catch (IOException | InvalidConfigurationException e) {
            CC.logConsoleFormated("&4Configuration file ERROR");
            e.printStackTrace();
        }
    }

    public void registerListeners(){
        PluginManager manager = Bukkit.getServer().getPluginManager();

        manager.registerEvents(new InteractItem(), this);
        CC.logConsoleFormated("&aRegistering all listeners..");
    }
}
