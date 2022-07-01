package dev.hely.voucher.modules.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Item {

    private final String name;
    private final ItemStack itemstack;
    private final List<String> commands;
    private final Boolean soundEnabled;
    private final String soundType;
    private final Boolean broadcastEnabled;
    private final String broadcastMessage;
}
