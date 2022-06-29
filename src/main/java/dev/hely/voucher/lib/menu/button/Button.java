package dev.hely.voucher.lib.menu.button;

import dev.hely.voucher.lib.maker.ItemMaker;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class Button {
    public static Button placeholder(Material material, byte data, String... title) {
        return new Button() {
            @Override
            public ItemStack getItemStack(Player player) {
                ItemMaker itemMaker = ItemMaker.of(material);
                itemMaker.setDisplayName(StringUtils.join(title));
                itemMaker.setData(data);
                return itemMaker.build();
            }
        };
    }

    public static void playFail(Player player) {
        player.playSound(player.getLocation(), Sound.DIG_GRASS, 20.0f, 0.1f);
    }

    public static void playSuccess(Player player) {
        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 20.0f, 15.0f);
    }

    public static void playNeutral(Player player) {
        player.playSound(player.getLocation(), Sound.CLICK, 20.0f, 1.0f);
    }

    public abstract ItemStack getItemStack(Player player);

    public void onClick(Player player, ClickType clickType) {
    }

    public void onClick(Player player, int slot, ClickType clickType) {
    }

    public void onClick(Player player, int slot, ClickType clickType, int hotbarSlot) {
    }

    public boolean shouldCancel(Player player, ClickType clickType) {
        return true;
    }

    public boolean shouldUpdate(Player player, ClickType clickType) {
        return false;
    }
}
