package dev.hely.voucher.lib.menu.pagination;

import dev.hely.voucher.lib.menu.Menu;
import dev.hely.voucher.lib.menu.button.Button;
import dev.hely.voucher.lib.menu.button.impl.BackButton;
import dev.hely.voucher.lib.menu.pagination.button.JumpToPageButton;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ViewAllPagesMenu extends Menu {
    @NonNull
    public PaginatedMenu menu;

    public ViewAllPagesMenu(@NonNull PaginatedMenu menu) {
        this.menu = menu;
    }
    @Override
    public String getName(Player player) {
        return "Jump to page";
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();
        buttons.put(0, new BackButton(this.menu));
        int index = 10;
        for (int i = 1; i <= this.menu.getPages(player); ++i) {
            buttons.put(index++, new JumpToPageButton(i, this.menu, this.menu.getPage() == i));
            if ((index - 8) % 9 == 0) {
                index += 2;
            }
        }
        return buttons;
    }
    @Override
    public boolean isAutoUpdate() {
        return true;
    }

    @NonNull
    public PaginatedMenu getMenu() {
        return this.menu;
    }
}