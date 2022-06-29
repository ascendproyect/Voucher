package dev.hely.voucher.lib;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: dev.hely.crates.lib
//   Date: Monday, June 13, 2022 - 4:28 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Messages {
    public static void sendMessage(String message) {
        if(message.isEmpty()) return;

        Bukkit.getConsoleSender().sendMessage(CC.translate(message));
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(CC.translate(message)));
    }

    public static void sendMessage(List<String> messages) {
        if(messages.isEmpty()) return;

        messages.forEach(message -> Bukkit.getConsoleSender().sendMessage(CC.translate(message)));
        Bukkit.getOnlinePlayers().forEach(player -> messages.forEach(message -> player.sendMessage(CC.translate(message))));
    }

    public static void sendMessage(String message, String permission) {
        if(message.isEmpty()) return;

        Bukkit.getConsoleSender().sendMessage(CC.translate(message));

        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(permission))
                .forEach(player -> player.sendMessage(CC.translate(message)));
    }

    public static void sendMessage(List<String> messages, String permission) {
        if(messages.isEmpty()) return;

        messages.forEach(message -> Bukkit.getConsoleSender().sendMessage(CC.translate(message)));

        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(permission))
                .forEach(player -> messages.forEach(message -> player.sendMessage(CC.translate(message))));
    }

    public static void sendMessage(Player player, String message) {
        if(message.isEmpty()) return;

        player.sendMessage(CC.translate(message));
    }

    public static void sendMessage(Player player, List<String> messages) {
        if(messages.isEmpty()) return;

        messages.forEach(message -> player.sendMessage(CC.translate(message)));
    }
}
