package dev.hely.voucher.lib.menu.button.impl;

import dev.hely.voucher.lib.maker.ItemMaker;
import dev.hely.voucher.lib.menu.Menu;
import dev.hely.voucher.lib.menu.button.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class BackButton extends Button {
    private final Menu back;

    public BackButton(Menu back) {
        this.back = back;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(Material.BED).setDisplayName("&c&lBack").setLore(Arrays.asList("&7Right-Click this item to return to the", "&7previous menu!")).build();
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        Button.playNeutral(player);
        this.back.openMenu(player);
    }
}
