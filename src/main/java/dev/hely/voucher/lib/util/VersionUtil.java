package dev.hely.voucher.lib.util;

import dev.hely.voucher.lib.Assert;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;


/**
 * @author Maykol Morales Morante (zSirSpectro)
 * Sunday, July 18, 2021
 */

@UtilityClass
public class VersionUtil {

    public static final String PACKAGE = Bukkit.getServer().getClass().getPackage().getName();
    public static final String VERSION = PACKAGE.substring(PACKAGE.lastIndexOf(".") + 1);

    public static boolean v1_7_R4() {
        return VERSION.equalsIgnoreCase("v1_7_R4");
    }

    public static boolean v1_8_R3() {
        return VERSION.equalsIgnoreCase("v1_8_R3");
    }

    public static boolean v1_7_10(Player player) {
        return getVersionOfPlayer(player) == 5;
    }

    public static boolean v1_8_8(Player player) {
        return getVersionOfPlayer(player) == 47;
    }

    public static int getVersionOfPlayer(Player player) {
        Assert.assertNotNull(player);

        if (v1_7_R4()) {
            try {
                Class<?> aClass = Class.forName("org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer");

                Object craftPlayer = aClass.cast(player);
                Object getHandle = aClass.getMethod("getHandle").invoke(craftPlayer);
                Object playerConnection = getHandle.getClass().getField("playerConnection").get(getHandle);
                Object networkManager = getHandle.getClass().getField("networkManager").get(playerConnection);

                return (int) networkManager.getClass().getMethod("getVersion").invoke(networkManager);
            } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException | InvocationTargetException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        } else if (v1_8_R3()) {
            if (PluginUtil.isEnabled("ViaVersion")) {
            }
        }

        return -1;
    }
}
