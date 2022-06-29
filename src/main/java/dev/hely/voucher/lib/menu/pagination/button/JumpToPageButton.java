package dev.hely.voucher.lib.menu.pagination.button;

import dev.hely.voucher.lib.maker.ItemMaker;
import dev.hely.voucher.lib.menu.button.Button;
import dev.hely.voucher.lib.menu.pagination.PaginatedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class JumpToPageButton extends Button {
    private final int page;
    private final PaginatedMenu menu;
    private final boolean current;

    public JumpToPageButton(int page, PaginatedMenu menu, boolean current) {
        this.page = page;
        this.menu = menu;
        this.current = current;
    }
    @Override
    public ItemStack getItemStack(Player player) {
        ItemMaker itemMaker = ItemMaker.of(this.current ? Material.ENCHANTED_BOOK : Material.BOOK);
        itemMaker.setDisplayName("&aPage " + this.page);
        itemMaker.setAmount(this.page);
        if (this.current) {
            itemMaker.setLore("", "&eCurrent page", "");
        }
        return itemMaker.build();
    }
    @Override
    public void onClick(Player player, int i, ClickType clickType, int hb) {
        this.menu.modPage(player, this.page - this.menu.getPage());
        Button.playNeutral(player);
    }

    public int getPage() {
        return this.page;
    }
    public PaginatedMenu getMenu() {
        return this.menu;
    }
    public boolean isCurrent() {
        return this.current;
    }
}
