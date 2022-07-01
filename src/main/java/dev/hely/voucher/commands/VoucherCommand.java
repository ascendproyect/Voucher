package dev.hely.voucher.commands;

import dev.hely.voucher.Voucher;
import dev.hely.voucher.lib.CC;
import dev.hely.voucher.lib.command.BaseCommand;
import dev.hely.voucher.modules.ModuleManager;
import dev.hely.voucher.modules.item.Item;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VoucherCommand extends BaseCommand {


    public VoucherCommand() {
        super("voucher", "voucher.command");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 4 && args[0].equalsIgnoreCase("give")){

            Player player = Bukkit.getPlayer(args[1]);
            if(!checkPlayer(sender, player, args[1])) return;

            Item item = ModuleManager.INSTANCE.getItemManager().getItemList().stream().filter(i -> i.getName().equalsIgnoreCase(args[2])).findFirst().orElse(null);
            if(item == null) {
                sender.sendMessage(CC.translate("&cVoucher not found"));
            }else{
                if(!checkNumber(sender, args[3])) return;

                item.getItemstack().setAmount(Integer.parseInt(args[3]));
                player.getInventory().addItem(item.getItemstack());
                sender.sendMessage(CC.translate(Voucher.INSTANCE.getConfig().getString("given.to-player").replace("%player_name%", player.getName()).replace("%voucher_name%", args[2])));
                player.sendMessage(CC.translate(Voucher.INSTANCE.getConfig().getString("given.from-player").replace("%player_name%", sender.getName()).replace("%voucher_name%", args[2])));
            }
        }else if(args.length == 3 && args[0].equalsIgnoreCase("giveall")){
            Item item = ModuleManager.INSTANCE.getItemManager().getItemList().stream().filter(i -> i.getName().equalsIgnoreCase(args[1])).findFirst().orElse(null);
            if(item == null) {
                sender.sendMessage(CC.translate("&cVoucher not found"));
            }else{
                if(!checkNumber(sender, args[2])) return;
                item.getItemstack().setAmount(Integer.parseInt(args[2]));
                for (Player player : Bukkit.getServer().getOnlinePlayers()){
                    player.getInventory().addItem(item.getItemstack());
                    player.sendMessage(CC.translate(Voucher.INSTANCE.getConfig().getString("given.from-player").replace("%player_name%", sender.getName()).replace("%voucher_name%", args[1])));
                }
                sender.sendMessage(CC.translate(Voucher.INSTANCE.getConfig().getString("given.to-all-player").replace("%voucher_name%", args[1])));
            }
        }else if(args.length == 1 && (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("reload"))){
            if(args[0].equalsIgnoreCase("list")){
                sender.sendMessage(CC.translate("&7&l&m-----------------------------------------"));
                sender.sendMessage(CC.translate("&9&lVoucher list"));
                sender.sendMessage("");
                ModuleManager.INSTANCE.getItemManager().getItemList().forEach(item -> sender.sendMessage(CC.translate("&7-&9 " + item.getName())));
                sender.sendMessage(CC.translate("&7&l&m-----------------------------------------"));
            }else{
                long time = System.currentTimeMillis();

                Voucher.INSTANCE.reloadConfig();
                Voucher.INSTANCE.getVoucher().reload();
                ModuleManager.INSTANCE.getItemManager().load();

                sender.sendMessage(CC.translate("&9&lVoucher &ehas been reloaded successfully. &7(" + (System.currentTimeMillis() - time) + "ms)"));
            }
        }else{
            sender.sendMessage(CC.translate("&7&l&m-----------------------------------------"));
            sender.sendMessage(CC.translate("&9&lVoucher &7- &fHely Development"));
            sender.sendMessage("");
            sender.sendMessage(CC.translate("&7-&9 /voucher give <player> <voucher> <amount> &7- &fGives a player a voucher."));
            sender.sendMessage(CC.translate("&7-&9 /voucher giveall <voucher> <amount> &7- &fGive a voucher to all players."));
            sender.sendMessage(CC.translate("&7-&9 /voucher list &7- &fLists all voucher."));
            sender.sendMessage(CC.translate("&7-&9 /voucher reload &7- &fReload config files."));
            sender.sendMessage(CC.translate("&7&l&m-----------------------------------------"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> tab = new ArrayList<>();
        if(args.length == 1){
            tab.add("giveall");
            tab.add("give");
            tab.add("list");
            tab.add("reload");
        }else if(args.length == 2 && args[0].equalsIgnoreCase("giveall")){
            ModuleManager.INSTANCE.getItemManager().getItemList().forEach(item -> tab.add(item.getName()));
        }else if(args.length == 3 && args[0].equalsIgnoreCase("give")){
            ModuleManager.INSTANCE.getItemManager().getItemList().forEach(item -> tab.add(item.getName()));
        }
        return tab;
    }
}
