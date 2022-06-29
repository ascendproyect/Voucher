package dev.hely.voucher.lib.util;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: dev.hely.crates.lib.util
//   Date: Thursday, June 16, 2022 - 4:12 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

public class FakeGlowEnchantment extends EnchantmentWrapper {

    public FakeGlowEnchantment(int id) {
        super(id);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    @Override
    public boolean conflictsWith(Enchantment item) {
        return false;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public String getName() {
        return "FakeGlow";
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }
}

