package me.gamercoder215.mobchip.abstraction;

import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.ai.attribute.Attribute;
import me.gamercoder215.mobchip.ai.attribute.AttributeInstance;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.enderdragon.CustomPhase;
import me.gamercoder215.mobchip.ai.enderdragon.DragonPhase;
import me.gamercoder215.mobchip.ai.goal.CustomPathfinder;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.goal.WrappedPathfinder;
import me.gamercoder215.mobchip.ai.gossip.EntityGossipContainer;
import me.gamercoder215.mobchip.ai.memories.Memory;
import me.gamercoder215.mobchip.ai.memories.MemoryStatus;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import me.gamercoder215.mobchip.ai.schedule.Schedule;
import me.gamercoder215.mobchip.ai.sensing.EntitySenses;
import me.gamercoder215.mobchip.ai.sensing.Sensor;
import me.gamercoder215.mobchip.combat.EntityCombatTracker;
import me.gamercoder215.mobchip.nbt.EntityNBT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents an Abstract Wrapper
 */
public interface ChipUtil {

    String CLASS_TAG = "==";

    void addCustomPathfinder(CustomPathfinder p, int priority, boolean target);

    Set<WrappedPathfinder> getGoals(Mob m, boolean target);

    Collection<WrappedPathfinder> getRunningGoals(Mob m, boolean target);

    void setFlag(Mob m, Pathfinder.PathfinderFlag flag, boolean target, boolean value);

    void addPathfinder(Pathfinder p, int priority, boolean target);

    void removePathfinder(Pathfinder p, boolean target);

    default void clearPathfinders(Mob mob, boolean target) {
        getGoals(mob, target).forEach(w -> removePathfinder(w.getPathfinder(), target));
    }

    default void addPathfinders(Collection<? extends WrappedPathfinder> c, boolean target) {
        for (WrappedPathfinder p : c) addPathfinder(p.getPathfinder(), p.getPriority(), target);
    }

    BehaviorResult runBehavior(Mob m, String behaviorName, Object... args);

    BehaviorResult runBehavior(Mob m, String behaviorName, String packageName, Object... args);

    EntityController getController(Mob m);

    EntityNavigation getNavigation(Mob m);

    EntityBody getBody(Mob m);

    EntityScheduleManager getManager(Mob m);

    EntityGossipContainer getGossipContainer(Villager v);

    EntityCombatTracker getCombatTracker(Mob m);

    void setCustomPhase(EnderDragon a, CustomPhase c);

    void knockback(EnderDragon a, List<Entity> list);

    <T> void setMemory(Mob mob, Memory<T> m, T value);

    void setMemory(Mob mob, String memoryKey, Object value);

    <T> void setMemory(Mob mob, Memory<T> m, T value, long durationTicks);

    <T> T getMemory(Mob mob, Memory<T> m);

    long getExpiry(Mob mob, Memory<?> m);

    boolean contains(Mob mob, Memory<?> m);

    void removeMemory(Mob mob, Memory<?> m);

    boolean isRestricted(Mob m);

    void clearRestriction(Mob m);

    void restrictTo(Mob m, double x, double y, double z, int radius);

    Location getRestriction(Mob m);

    int getRestrictionRadius(Mob m);

    boolean hasRestriction(Mob m);

    boolean canSee(Mob m, Entity en);

    Schedule getDefaultSchedule(String key);

    Attribute registerAttribute(NamespacedKey key, double defaultV, double min, double max, boolean client);

    boolean existsAttribute(NamespacedKey key);

    Attribute getAttribute(NamespacedKey key);

    Attribute getDefaultAttribute(String s);

    AttributeInstance getAttributeInstance(Mob m, Attribute a);

    DragonPhase fromBukkit(EnderDragon dragon, EnderDragon.Phase phase);

    DragonPhase getCurrentPhase(EnderDragon dragon);

    void registerMemory(Memory<?> m);

    boolean existsMemory(Memory<?> m);

    EntityNBT getNBTEditor(Mob m);

    void registerSensor(Sensor<?> s);

    boolean existsSensor(NamespacedKey key);

    Sensor<?> getSensor(NamespacedKey key);

    EntitySenses getSenses(Mob m);

    MemoryStatus getMemoryStatus(Mob mob, Memory<?> m);

    EnderCrystal getNearestCrystal(EnderDragon d);

    default void updateActivities(Creature c) {}

    default BehaviorResult hearNoteblock(Creature c, Location loc) {
        return BehaviorResult.STOPPED;
    }

    default BehaviorResult setDisturbanceLocation(Creature c, Location loc) {
        return BehaviorResult.STOPPED;
    }

    default void updateGoals(Map<Integer, Pathfinder> goals, Map<Integer, Boolean> target) {
        try {
            for (Map.Entry<Integer, Pathfinder> e : goals.entrySet())
                addPathfinder(e.getValue(), e.getKey(), target.get(e.getKey()));
        } catch (ClassCastException e) {
            Bukkit.getLogger().severe("[MobChip] Invalid Projectile Source");
            printStackTrace(e);
        }
    }

    /**
     * Finds the classes of an array of objects, using unboxed classes where possible.
     * (ex. int.class rather than Integer.class)
     * @param args Objects
     * @return Classes of objects
     */
    static Class<?>[] getArgTypes(Object... args) {
        Class<?>[] types = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                types[i] = (Class<?>) args[i].getClass().getDeclaredField("TYPE").get(null);
            } catch (ReflectiveOperationException ignored) {
                types[i] = args[i].getClass();
            }
        }
        return types;
    }

    static String bukkitToCraftBukkkit() {
        String bukkit = Bukkit.getServer().getBukkitVersion().split("-")[0];
        switch (bukkit) {
            case "1.20.1": 
                return "1_20_R1";
            case "1.20.2": 
                return "1_20_R2";
            case "1.20.3":
            case "1.20.4":
                return "1_20_R3";
            case "1.20.5":
                return "1_20_R4";
            default:
                throw new AssertionError("Invalid Version: " + bukkit);
        }
    }

    static String getServerVersion() {
        try {
            return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        } catch (IndexOutOfBoundsException e) {
            // Using CraftBukkit Relocation
            return bukkitToCraftBukkkit();
        }
    }

    static ChipUtil getWrapper() {
        String pkg = ChipUtil.class.getPackage().getName() + ".v" + getServerVersion();
        try {
            Constructor<? extends ChipUtil> constr = Class.forName(pkg + ".ChipUtil" + getServerVersion())
                    .asSubclass(ChipUtil.class)
                    .getDeclaredConstructor();
            constr.setAccessible(true);
            return constr.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Invalid Version: " + getServerVersion() + " (Could not load " + pkg + ".ChipUtil" + getServerVersion() + ")", e);
        }
    }

    static void printStackTrace(Throwable e) {
        Bukkit.getLogger().severe(e.getClass().getName() + ": " + e.getMessage());
        for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe("    " + s.toString());
        if (e.getCause() != null) {
            Bukkit.getLogger().severe("Caused by:");
            printStackTrace(e.getCause());
        }
    }
}
