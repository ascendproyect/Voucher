package dev.hely.voucher.modules.item;

import dev.hely.voucher.Voucher;
import dev.hely.voucher.lib.maker.ItemMaker;
import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    @Getter
    private final List<Item> itemList;

    public ItemManager(){
        itemList = new ArrayList<>();
        load();
    }

    public void  onDisable(){
        if(!itemList.isEmpty()) itemList.clear();
    }

    public void load(){
        if(!itemList.isEmpty()) itemList.clear();
        for(String itemname : Voucher.INSTANCE.getVoucher().getConfig().getConfigurationSection("voucher").getKeys(false)){
            ItemMaker item = ItemMaker.of(Material.getMaterial(Voucher.INSTANCE.getVoucher().getConfig().getInt("voucher." + itemname + ".material")),
                    1, Voucher.INSTANCE.getVoucher().getConfig().getInt("voucher." + itemname + ".data"))
                    .setDisplayName(Voucher.INSTANCE.getVoucher().getConfig().getString("voucher." + itemname + ".displayname"))
                    .setLore(Voucher.INSTANCE.getVoucher().getConfig().getStringList("voucher." + itemname + ".lore"));

            if(Voucher.INSTANCE.getVoucher().getConfig().getBoolean("voucher." + itemname + ".glow")) item.addFakeGlow();

            List<String> commands = Voucher.INSTANCE.getVoucher().getConfig().getStringList("voucher." + itemname + ".commands");
            Boolean soundEnabled = Voucher.INSTANCE.getVoucher().getConfig().getBoolean("voucher." + itemname + ".sound.enabled");
            String soundType = Voucher.INSTANCE.getVoucher().getConfig().getString("voucher." + itemname + ".sound.type");
            Boolean broadcastEnabled = Voucher.INSTANCE.getVoucher().getConfig().getBoolean("voucher." + itemname + ".open_broadcast.enabled");
            String broadcastMessage = Voucher.INSTANCE.getVoucher().getConfig().getString("voucher." + itemname + ".open_broadcast.message");
            itemList.add(new Item(itemname, item.build(), commands, soundEnabled, soundType,  broadcastEnabled, broadcastMessage));
        }
    }
}
