package dev.hely.voucher.lib;

import com.google.common.collect.*;
import java.util.stream.*;
import org.bukkit.command.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.bukkit.event.entity.*;
import org.bukkit.projectiles.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.potion.*;
import org.bukkit.entity.*;
import java.util.*;
import com.google.common.base.*;

public class BukkitUtil {
    public static final String STRAIGHT_LINE_DEFAULT;
    private static final ImmutableMap<Object, Object> CHAT_DYE_COLOUR_MAP;
    private static final ImmutableSet<Object> DEBUFF_TYPES;
    private static final int DEFAULT_COMPLETION_LIMIT = 80;
    private static final String STRAIGHT_LINE_TEMPLATE;
    private static ArrayList<ChatColor> woolColors;
    
    public static ArrayList<Location> getCircle(final Location center, final double radius, final int amount) {
        final World world = center.getWorld();
        final double increment = 6.283185307179586 / amount;
        final ArrayList<Location> locations = new ArrayList<Location>();
        for (int i = 0; i < amount; ++i) {
            final double angle = i * increment;
            final double x = center.getX() + radius * Math.cos(angle);
            final double z = center.getZ() + radius * Math.sin(angle);
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }
    
    public static int countColoursUsed(final String id, final boolean ignoreDuplicates) {
        final ChatColor[] values = ChatColor.values();
        final List<Character> charList = new ArrayList<Character>(values.length);
        for (final ChatColor colour : values) {
            charList.add(colour.getChar());
        }
        int count = 0;
        final Set<ChatColor> found = new HashSet<ChatColor>();
        for (int i = 1; i < id.length(); ++i) {
            if (charList.contains(id.charAt(i)) && id.charAt(i - 1) == '&') {
                final ChatColor colour = ChatColor.getByChar(id.charAt(i));
                if (found.add(colour) || ignoreDuplicates) {
                    ++count;
                }
            }
        }
        return count;
    }
    
    public static List<Player> getOnlinePlayers() {
        final List<Player> players = Lists.newArrayList();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            players.add(player);
        }
        return players;
    }
    
    public static String getDisplayName(final CommandSender sender) {
        Preconditions.checkNotNull((Object)sender);
        return (sender instanceof Player) ? ((Player)sender).getDisplayName() : sender.getName();
    }
    
    public static DyeColor toDyeColor(final ChatColor colour) {
        return (DyeColor)BukkitUtil.CHAT_DYE_COLOUR_MAP.get((Object)colour);
    }
    
    public static boolean hasMetaData(final Metadatable metadatable, final String input, final Plugin plugin) {
        return getMetaData(metadatable, input, plugin) != null;
    }
    
    public static MetadataValue getMetaData(final Metadatable metadatable, final String input, final Plugin plugin) {
        return (MetadataValue)metadatable.getMetadata(input);
    }
    
    public static Player getFinalAttacker(final EntityDamageEvent ede, final boolean ignoreSelf) {
        Player attacker = null;
        if (ede instanceof EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)ede;
            final Entity damager = event.getDamager();
            if (event.getDamager() instanceof Player) {
                attacker = (Player)damager;
            }
            else if (event.getDamager() instanceof Projectile) {
                final Projectile projectile = (Projectile)damager;
                final ProjectileSource shooter = projectile.getShooter();
                if (shooter instanceof Player) {
                    attacker = (Player)shooter;
                }
            }
            if (attacker != null && ignoreSelf && event.getEntity().equals(attacker)) {
                attacker = null;
            }
        }
        return attacker;
    }
    
    public static boolean isWithinX(final Location location, final Location other, final double distance) {
        return location.getWorld().equals(other.getWorld()) && Math.abs(other.getX() - location.getX()) <= distance && Math.abs(other.getZ() - location.getZ()) <= distance;
    }
    
    public static Location getHighestLocation(final Location origin) {
        return getHighestLocation(origin, null);
    }
    
    public static Location getHighestLocation(final Location origin, final Location def) {
        Preconditions.checkNotNull((Object)origin, (Object)"The location cannot be null");
        final Location cloned = origin.clone();
        final World world = cloned.getWorld();
        final int x = cloned.getBlockX();
        int y = world.getMaxHeight();
        final int z = cloned.getBlockZ();
        while (y > origin.getBlockY()) {
            final Block block = world.getBlockAt(x, --y, z);
            if (!block.isEmpty()) {
                final Location next = block.getLocation();
                next.setPitch(origin.getPitch());
                next.setYaw(origin.getYaw());
                return next;
            }
        }
        return def;
    }
    
    public static boolean isDebuff(final PotionEffectType type) {
        return BukkitUtil.DEBUFF_TYPES.contains((Object)type);
    }
    
    public static boolean isDebuff(final PotionEffect potionEffect) {
        return isDebuff(potionEffect.getType());
    }
    
    public static boolean isDebuff(final ThrownPotion thrownPotion) {
        for (final PotionEffect effect : thrownPotion.getEffects()) {
            if (isDebuff(effect)) {
                return true;
            }
        }
        return false;
    }
    
    public static int getDefaultCompletionLimit() {
        return 80;
    }
    
    public static String getName(final PotionEffectType potionEffectType) {
        if (potionEffectType.getName().equalsIgnoreCase("fire_resistance")) {
            return "Fire Resistance";
        }
        if (potionEffectType.getName().equalsIgnoreCase("speed")) {
            return "Speed";
        }
        if (potionEffectType.getName().equalsIgnoreCase("weakness")) {
            return "Weakness";
        }
        if (potionEffectType.getName().equalsIgnoreCase("slowness")) {
            return "Slowness";
        }
        return "Unknown";
    }
    
    public static Player getDamager(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            return (Player)event.getDamager();
        }
        if (event.getDamager() instanceof Projectile && ((Projectile)event.getDamager()).getShooter() instanceof Player) {
            return (Player)((Projectile)event.getDamager()).getShooter();
        }
        return null;
    }
    
    public static int convertChatColorToWoolData(ChatColor color) {
        if (color == ChatColor.DARK_RED) {
            color = ChatColor.RED;
        }
        return BukkitUtil.woolColors.indexOf(color);
    }
    
    static {
        BukkitUtil.woolColors = new ArrayList<ChatColor>(Arrays.asList(ChatColor.WHITE, ChatColor.GOLD, ChatColor.LIGHT_PURPLE, ChatColor.AQUA, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.LIGHT_PURPLE, ChatColor.DARK_GRAY, ChatColor.GRAY, ChatColor.DARK_AQUA, ChatColor.DARK_PURPLE, ChatColor.BLUE, ChatColor.BLACK, ChatColor.DARK_GREEN, ChatColor.RED));
        STRAIGHT_LINE_TEMPLATE = ChatColor.STRIKETHROUGH.toString() + Strings.repeat("-", 256);
        STRAIGHT_LINE_DEFAULT = BukkitUtil.STRAIGHT_LINE_TEMPLATE.substring(0, 55);
        CHAT_DYE_COLOUR_MAP = ImmutableMap.builder().put((Object)ChatColor.AQUA, (Object)DyeColor.LIGHT_BLUE).put((Object)ChatColor.BLACK, (Object)DyeColor.BLACK).put((Object)ChatColor.BLUE, (Object)DyeColor.LIGHT_BLUE).put((Object)ChatColor.DARK_AQUA, (Object)DyeColor.CYAN).put((Object)ChatColor.DARK_BLUE, (Object)DyeColor.BLUE).put((Object)ChatColor.DARK_GRAY, (Object)DyeColor.GRAY).put((Object)ChatColor.DARK_GREEN, (Object)DyeColor.GREEN).put((Object)ChatColor.DARK_PURPLE, (Object)DyeColor.PURPLE).put((Object)ChatColor.DARK_RED, (Object)DyeColor.RED).put((Object)ChatColor.GOLD, (Object)DyeColor.ORANGE).put((Object)ChatColor.GRAY, (Object)DyeColor.SILVER).put((Object)ChatColor.GREEN, (Object)DyeColor.LIME).put((Object)ChatColor.LIGHT_PURPLE, (Object)DyeColor.MAGENTA).put((Object)ChatColor.RED, (Object)DyeColor.RED).put((Object)ChatColor.WHITE, (Object)DyeColor.WHITE).put((Object)ChatColor.YELLOW, (Object)DyeColor.YELLOW).build();
        DEBUFF_TYPES = ImmutableSet.builder().add((Object)PotionEffectType.BLINDNESS).add((Object)PotionEffectType.CONFUSION).add((Object)PotionEffectType.HARM).add((Object)PotionEffectType.HUNGER).add((Object)PotionEffectType.POISON).add((Object)PotionEffectType.SATURATION).add((Object)PotionEffectType.SLOW).add((Object)PotionEffectType.SLOW_DIGGING).add((Object)PotionEffectType.WEAKNESS).add((Object)PotionEffectType.WITHER).build();
    }
}
