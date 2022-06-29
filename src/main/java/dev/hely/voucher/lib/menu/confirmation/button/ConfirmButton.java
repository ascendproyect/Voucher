package dev.hely.voucher.lib.menu.confirmation.button;

import dev.hely.voucher.lib.maker.ItemMaker;
import dev.hely.voucher.lib.menu.button.Button;
import dev.hely.voucher.lib.menu.callback.MenuCallback;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ConfirmButton extends Button {
    private final boolean confirm;
    private final MenuCallback<Boolean> callback;


    public ConfirmButton(boolean confirm, MenuCallback<Boolean> callback) {
        this.confirm = confirm;
        this.callback = callback;
    }
    @Override
    public ItemStack getItemStack(Player player) {
        ItemMaker itemMaker = ItemMaker.of(Material.WOOL);
        itemMaker.setDisplayName(this.confirm ? "&aConfirm" : "&cCancel");
        itemMaker.setData((short) (this.confirm ? 5 : 14));
        return itemMaker.build();
    }
    @Override
    public void onClick(Player player, ClickType clickType) {
        if (this.confirm) {
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 20.0f, 0.1f);
        } else {
            player.playSound(player.getLocation(), Sound.DIG_GRAVEL, 20.0f, 0.1f);
        }

        player.closeInventory();
        this.callback.callback(this.confirm);
    }
}