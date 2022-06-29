package dev.hely.voucher.lib.menu.pagination.button;

import dev.hely.voucher.lib.maker.ItemMaker;
import dev.hely.voucher.lib.menu.button.Button;
import dev.hely.voucher.lib.menu.pagination.PaginatedMenu;
import dev.hely.voucher.lib.menu.pagination.ViewAllPagesMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;


public class PageInfoButton extends Button {
    private final PaginatedMenu menu;

    public PageInfoButton(PaginatedMenu menu) {
        this.menu = menu;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        int pages = this.menu.getPages(player);
        return ItemMaker.of(Material.BOOK).setDisplayName("&d&lPage Info").setLore("",
                "&eYou are viewing page &f#" + this.menu.getPage() + "&e.", "&e" +
                        ((pages == 1) ? "There is &f1 &epage." : ("There are &f" + pages + " &epages.")),
                "", "&eLeft click here to", "&eview all pages.", "").build();
    }
    @Override
    public void onClick(Player player, ClickType clickType) {
        if (clickType == ClickType.LEFT) {
            new ViewAllPagesMenu(this.menu).openMenu(player);
            Button.playNeutral(player);
        }
    }
}
