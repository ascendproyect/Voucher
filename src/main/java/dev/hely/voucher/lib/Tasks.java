package dev.hely.voucher.lib;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import dev.hely.voucher.Voucher;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.ThreadFactory;

public class Tasks {
    public static ThreadFactory newThreadFactory(String name) {
        return new ThreadFactoryBuilder().setNameFormat(name).build();
    }
    public static void scheduleSyncDelayed(Callable callback) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(
                Voucher.INSTANCE,
                callback::call
        );
    }

    @Deprecated
    public static void scheduleAsyncDelayed(Callable callback) {
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(
                Voucher.INSTANCE,
                callback::call
        );
    }

    public static void scheduleSyncDelayed(Callable callback, long delay) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(
                Voucher.INSTANCE,
                callback::call,
                delay
        );
    }

    @Deprecated
    public static void scheduleAsyncDelayed(Callable callback, long delay) {
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(
                Voucher.INSTANCE,
                callback::call,
                delay
        );
    }

    public static void scheduleSyncRepeating(Callable callback, long start, long delay) {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(
                Voucher.INSTANCE,
                callback::call,
                start,
                delay
        );
    }

    @Deprecated
    public static void scheduleAsyncRepeatingTask(Callable callback, long start, long delay) {
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(
                Voucher.INSTANCE,
                callback::call,
                start,
                delay
        );
    }
    public static void run(Callable callable) {
        Voucher.INSTANCE.getServer().getScheduler().runTask(Voucher.INSTANCE, callable::call);
    }

    public static void runAsync(Callable callable) {
        Voucher.INSTANCE.getServer().getScheduler().runTaskAsynchronously(Voucher.INSTANCE, callable::call);
    }

    public static void runLater(Callable callable, long delay) {
        Voucher.INSTANCE.getServer().getScheduler().runTaskLater(Voucher.INSTANCE, callable::call, delay);
    }

    public static void runAsyncLater(Callable callable, long delay) {
        Voucher.INSTANCE.getServer().getScheduler().runTaskLaterAsynchronously(Voucher.INSTANCE, callable::call, delay);
    }

    public static void runTimer(Callable callable, long delay, long interval) {
        Voucher.INSTANCE.getServer().getScheduler().runTaskTimer(Voucher.INSTANCE, callable::call, delay, interval);
    }

    public static void runAsyncTimer(Callable callable, long delay, long interval) {
        Voucher.INSTANCE.getServer().getScheduler().runTaskTimerAsynchronously(Voucher.INSTANCE, callable::call, delay, interval);
    }
    public static BukkitTask asyncTimer(Callable callable, long delay, long value) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(Voucher.INSTANCE, callable::call, delay, value);
    }

    public static void sync(Callable callable) {
        Bukkit.getScheduler().runTask(Voucher.INSTANCE, callable::call);
    }

    public static BukkitTask syncLater(Callable callable, long delay) {
        return Bukkit.getScheduler().runTaskLater(Voucher.INSTANCE, callable::call, delay);
    }

    public static BukkitTask syncTimer(Callable callable, long delay, long value) {
        return Bukkit.getScheduler().runTaskTimer(Voucher.INSTANCE, callable::call, delay, value);
    }

    public static void async(Callable callable) {
        Bukkit.getScheduler().runTaskAsynchronously(Voucher.INSTANCE, callable::call);
    }

    public static BukkitTask asyncLater(Callable callable, long delay) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(Voucher.INSTANCE, callable::call, delay);
    }


    public interface Callable {
        void call();
    }
}
