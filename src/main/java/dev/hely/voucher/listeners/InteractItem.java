package dev.hely.voucher.listeners;

import dev.hely.voucher.lib.CC;
import dev.hely.voucher.modules.ModuleManager;
import dev.hely.voucher.modules.item.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractItem implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event){
        if(!event.hasItem()) return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR ) return;

        ItemStack item = event.getItem();
        if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;

        Item voucherItem = ModuleManager.INSTANCE.getItemManager().getItemList().stream().filter(i -> i.getItemstack().getType().equals(item.getType())
                && i.getItemstack().getData().equals(item.getData())
                && i.getItemstack().getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())
                && i.getItemstack().getItemMeta().getLore().equals(item.getItemMeta().getLore())).findFirst().orElse(null);
        if(voucherItem == null) return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        if(player.getItemInHand().getAmount() <= 1) {
            player.setItemInHand(new ItemStack(Material.AIR));
        }else{
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        }

        voucherItem.getCommands().forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player_name%", player.getName())));

        if(voucherItem.getBroadcastEnabled()){
            Bukkit.getServer().broadcastMessage(CC.translate(voucherItem.getBroadcastMessage().replace("%player_name%", player.getName()).replace("%voucher_name%", voucherItem.getName())));
        }

        if(voucherItem.getSoundEnabled()){
            try {
                player.playSound(player.getLocation(),  Sound.valueOf(voucherItem.getSoundType()), 1L, 1F);

            } catch (Exception exception) {
                CC.logConsoleFormated("&cInvalid sound name: " + voucherItem.getSoundType().toUpperCase());
            }
        }
    }
}
