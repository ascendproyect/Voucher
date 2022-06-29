package dev.hely.voucher.lib.menu.pagination.button;

import dev.hely.voucher.lib.maker.ItemMaker;
import dev.hely.voucher.lib.menu.button.Button;
import dev.hely.voucher.lib.menu.pagination.PaginatedMenu;
import dev.hely.voucher.lib.menu.pagination.ViewAllPagesMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;


public class PageButton extends Button {
    private final int mod;
    private final PaginatedMenu menu;

    public PageButton(int mod, PaginatedMenu menu) {
        this.mod = mod;
        this.menu = menu;
    }
    @Override
    public ItemStack getItemStack(Player player) {
        ItemMaker itemMaker = ItemMaker.of(Material.GOLD_NUGGET);
        if (this.hasNext(player)) {
            itemMaker.setDisplayName((this.mod > 0) ? "&aNext Page" : "&cPrevious Page");
        } else {
            itemMaker.setDisplayName((this.mod > 0) ? "&aLast Page" : "&aFirst Page");
        }
        itemMaker.setLore("", "&eRight click to", "&ejump to a page.", "");
        return itemMaker.build();
    }
    @Override
    public void onClick(Player player, int i, ClickType clickType) {
        if (clickType.equals(ClickType.RIGHT)) {
            if (!this.menu.isInfoButton()) {
                new ViewAllPagesMenu(this.menu).openMenu(player);
                Button.playNeutral(player);
            }
        } else if (this.hasNext(player)) {
            this.menu.modPage(player, this.mod);
            Button.playNeutral(player);
        } else {
            Button.playFail(player);
        }
    }
    private boolean hasNext(Player player) {
        int pg = this.menu.getPage() + this.mod;
        return pg > 0 && this.menu.getPages(player) >= pg;
    }
}
