package dev.hely.voucher.lib.command;

import dev.hely.voucher.lib.CC;
import dev.hely.voucher.lib.manager.Manager;
import dev.hely.voucher.lib.wrapper.ClassWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public enum CommandManager implements Manager {
    INSTANCE;

    private CommandMap commandMap;
    private List<BaseCommand> commands;
    private String pluginName;


    @Override
    public void onEnable(JavaPlugin plugin) {
        commandMap = getCommandMap();
        commands = new ArrayList<>();
        pluginName = plugin.getDescription().getName();

        this.commands.forEach(this::registerCommand);
        CC.logConsoleFormated("&aLoading " + this.commands.size() + " commands..");
    }


    private CommandMap getCommandMap() {
        return (CommandMap) new ClassWrapper(Bukkit.getServer()).getField("commandMap").get();
    }

    public void registerCommand(BukkitCommand command) {
        this.commandMap.register(pluginName, command);
    }
}
