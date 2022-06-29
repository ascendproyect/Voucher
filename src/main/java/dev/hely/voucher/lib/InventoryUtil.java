package dev.hely.voucher.lib;

import org.bukkit.enchantments.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.*;
import com.google.common.base.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.entity.*;

public class InventoryUtil
{
    public static int DEFAULT_INVENTORY_WIDTH;
    public static int MINIMUM_INVENTORY_HEIGHT;
    public static int MINIMUM_INVENTORY_SIZE;
    public static int MAXIMUM_INVENTORY_HEIGHT;
    public static int MAXIMUM_INVENTORY_SIZE;
    public static int MAXIMUM_SINGLE_CHEST_SIZE;
    public static int MAXIMUM_DOUBLE_CHEST_SIZE;
    
    public static ItemStack[] fixInventoryOrder(final ItemStack[] source) {
        final ItemStack[] fixed = new ItemStack[36];
        System.arraycopy(source, 0, fixed, 27, 9);
        System.arraycopy(source, 9, fixed, 0, 27);
        return fixed;
    }
    
    public static String serializeInventory(final ItemStack[] source) {
        final StringBuilder builder = new StringBuilder();
        for (final ItemStack itemStack : source) {
            builder.append(serializeItemStack(itemStack));
            builder.append(";");
        }
        return builder.toString();
    }
    
    public static ItemStack[] deserializeInventory(final String source) {
        final List<ItemStack> items = new ArrayList<ItemStack>();
        final String[] split2;
        final String[] split = split2 = source.split(";");
        for (final String piece : split2) {
            items.add(deserializeItemStack(piece));
        }
        return items.toArray(new ItemStack[items.size()]);
    }
    
    public static String serializeItemStack(final ItemStack item) {
        final StringBuilder builder = new StringBuilder();
        if (item == null) {
            return "null";
        }
        final String isType = String.valueOf(item.getType().getId());
        builder.append("t@").append(isType);
        if (item.getDurability() != 0) {
            final String isDurability = String.valueOf(item.getDurability());
            builder.append(":d@").append(isDurability);
        }
        if (item.getAmount() != 1) {
            final String isAmount = String.valueOf(item.getAmount());
            builder.append(":a@").append(isAmount);
        }
        final Map<Enchantment, Integer> isEnch = (Map<Enchantment, Integer>)item.getEnchantments();
        if (isEnch.size() > 0) {
            for (final Map.Entry<Enchantment, Integer> ench : isEnch.entrySet()) {
                builder.append(":e@").append(ench.getKey().getId()).append("@").append(ench.getValue());
            }
        }
        if (item.hasItemMeta()) {
            final ItemMeta imeta = item.getItemMeta();
            if (imeta.hasDisplayName()) {
                builder.append(":dn@").append(imeta.getDisplayName());
            }
            if (imeta.hasLore()) {
                builder.append(":l@").append(imeta.getLore());
            }
        }
        return builder.toString();
    }
    
    public static ItemStack deserializeItemStack(final String in) {
        ItemStack item = null;
        ItemMeta meta = null;
        if (in.equals("null")) {
            return new ItemStack(Material.AIR);
        }
        final String[] split2;
        final String[] split = split2 = in.split(":");
        for (final String itemInfo : split2) {
            final String[] itemAttribute = itemInfo.split("@");
            final String s4;
            final String s2 = s4 = itemAttribute[0];
            switch (s4) {
                case "t": {
                    item = new ItemStack(Material.getMaterial((int)Integer.valueOf(itemAttribute[1])));
                    meta = item.getItemMeta();
                    break;
                }
                case "d": {
                    if (item != null) {
                        item.setDurability((short)Short.valueOf(itemAttribute[1]));
                        break;
                    }
                    break;
                }
                case "a": {
                    if (item != null) {
                        item.setAmount((int)Integer.valueOf(itemAttribute[1]));
                        break;
                    }
                    break;
                }
                case "e": {
                    if (item != null) {
                        item.addEnchantment(Enchantment.getById((int)Integer.valueOf(itemAttribute[1])), (int)Integer.valueOf(itemAttribute[2]));
                        break;
                    }
                    break;
                }
                case "dn": {
                    if (meta != null) {
                        meta.setDisplayName(itemAttribute[1]);
                        break;
                    }
                    break;
                }
                case "l": {
                    itemAttribute[1] = itemAttribute[1].replace("[", "");
                    itemAttribute[1] = itemAttribute[1].replace("]", "");
                    final List<String> lore = Arrays.asList(itemAttribute[1].split(","));
                    for (int x = 0; x < lore.size(); ++x) {
                        String s3 = lore.get(x);
                        if (s3 != null && s3.toCharArray().length != 0) {
                            if (s3.charAt(0) == ' ') {
                                s3 = s3.replaceFirst(" ", "");
                            }
                            lore.set(x, s3);
                        }
                    }
                    if (meta != null) {
                        meta.setLore((List)lore);
                        break;
                    }
                    break;
                }
            }
        }
        if (meta != null && (meta.hasDisplayName() || meta.hasLore())) {
            item.setItemMeta(meta);
        }
        return item;
    }
    
    public static ItemStack[] deepClone(final ItemStack[] origin) {
        Preconditions.checkNotNull((Object)origin, (Object)"Origin cannot be null");
        final ItemStack[] cloned = new ItemStack[origin.length];
        for (int i = 0; i < origin.length; ++i) {
            final ItemStack next = origin[i];
            cloned[i] = ((next == null) ? null : next.clone());
        }
        return cloned;
    }
    
    public static int getSafestInventorySize(final int initialSize) {
        return (initialSize + 8) / 9 * 9;
    }
    
    public static void removeItem(final Inventory inventory, final Material type, final short data, final int quantity) {
        final ItemStack[] contents = inventory.getContents();
        final boolean compareDamage = type.getMaxDurability() == 0;
        for (int i = quantity; i > 0; --i) {
            final ItemStack[] array = contents;
            final int length = array.length;
            int j = 0;
            while (j < length) {
                final ItemStack content = array[j];
                if (content != null && content.getType() == type && (!compareDamage || content.getData().getData() == data)) {
                    if (content.getAmount() <= 1) {
                        inventory.removeItem(new ItemStack[] { content });
                        break;
                    }
                    content.setAmount(content.getAmount() - 1);
                    break;
                }
                else {
                    ++j;
                }
            }
        }
    }
    
    public static int countAmount(final Inventory inventory, final Material type, final short data) {
        final ItemStack[] contents = inventory.getContents();
        final boolean compareDamage = type.getMaxDurability() == 0;
        int counter = 0;
        for (final ItemStack item : contents) {
            if (item != null && item.getType() == type && (!compareDamage || item.getData().getData() == data)) {
                counter += item.getAmount();
            }
        }
        return counter;
    }
    
    public static boolean isEmpty(final Inventory inventory) {
        return isEmpty(inventory, true);
    }
    
    public static boolean isEmpty(final Inventory inventory, final boolean checkArmour) {
        boolean result = true;
        ItemStack[] contents3;
        final ItemStack[] array;
        final ItemStack[] contents2 = array = (contents3 = inventory.getContents());
        for (final ItemStack content : array) {
            if (content != null && content.getType() != Material.AIR) {
                result = false;
                break;
            }
        }
        if (!result) {
            return false;
        }
        if (checkArmour && inventory instanceof PlayerInventory) {
            final ItemStack[] array2;
            final ItemStack[] armorContents = array2 = (contents3 = ((PlayerInventory)inventory).getArmorContents());
            for (final ItemStack content2 : array2) {
                if (content2 != null && content2.getType() != Material.AIR) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
    
    public static boolean clickedTopInventory(final InventoryDragEvent event) {
        final InventoryView view = event.getView();
        final Inventory topInventory = view.getTopInventory();
        if (topInventory == null) {
            return false;
        }
        boolean result = false;
        final Set<Map.Entry<Integer, ItemStack>> entrySet = event.getNewItems().entrySet();
        final int size = topInventory.getSize();
        for (final Map.Entry<Integer, ItemStack> entry : entrySet) {
            if (entry.getKey() < size) {
                result = true;
                break;
            }
        }
        return result;
    }
    
    public static boolean isFull(final Player player) {
        return player.getInventory().firstEmpty() == -1;
    }
    
    static {
        InventoryUtil.DEFAULT_INVENTORY_WIDTH = 9;
        InventoryUtil.MINIMUM_INVENTORY_HEIGHT = 1;
        InventoryUtil.MINIMUM_INVENTORY_SIZE = 9;
        InventoryUtil.MAXIMUM_INVENTORY_HEIGHT = 6;
        InventoryUtil.MAXIMUM_INVENTORY_SIZE = 54;
        InventoryUtil.MAXIMUM_SINGLE_CHEST_SIZE = 27;
        InventoryUtil.MAXIMUM_DOUBLE_CHEST_SIZE = 54;
    }
}
