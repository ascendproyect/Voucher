package dev.hely.voucher.lib;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.util.*;

public class LocationUtil
{
    public static String getString(final Location loc) {
        final StringBuilder builder = new StringBuilder();
        if (loc == null) {
            return "unset";
        }
        builder.append(loc.getX()).append("|");
        builder.append(loc.getY()).append("|");
        builder.append(loc.getZ()).append("|");
        builder.append(loc.getWorld().getName()).append("|");
        builder.append(loc.getYaw()).append("|");
        builder.append(loc.getPitch());
        return builder.toString();
    }
    
    public static Location getLocation(final String s) {
        if (s == null || s.equals("unset") || s.equals("")) {
            return null;
        }
        final String[] data = s.split("\\|");
        final double x = Double.parseDouble(data[0]);
        final double y = Double.parseDouble(data[1]);
        final double z = Double.parseDouble(data[2]);
        final World world = Bukkit.getWorld(data[3]);
        final Float yaw = Float.parseFloat(data[4]);
        final Float pitch = Float.parseFloat(data[5]);
        return new Location(world, x, y, z, (float)yaw, (float)pitch);
    }
    
    public static String getFormatted(final Location location, final boolean world) {
        return (world ? (location.getWorld().getName() + ", ") : "") + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }
    
    public static boolean isSameLocation(final Location loc1, final Location loc2) {
        return loc1 != null && loc2 != null && loc1.equals((Object)loc2);
    }
    
    public static void multiplyVelocity(final Player player, final Vector vector, final double multiply, final double addY) {
        vector.normalize();
        vector.multiply(multiply);
        vector.setY(vector.getY() + addY);
        player.setFallDistance(0.0f);
        player.setVelocity(vector);
    }
}
