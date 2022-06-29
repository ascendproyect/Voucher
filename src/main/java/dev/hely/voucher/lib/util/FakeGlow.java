package dev.hely.voucher.lib.util;


import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

/**
 * Created By LeandroSSJ
 * Created on 28/11/2021
 */

public class FakeGlow {
    public static Enchantment FAKE_GLOW;

    static {
        FAKE_GLOW = new FakeGlowEnchantment(70);
    }
}