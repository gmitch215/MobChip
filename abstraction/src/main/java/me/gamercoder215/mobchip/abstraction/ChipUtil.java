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
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import me.gamercoder215.mobchip.ai.schedule.Schedule;
import me.gamercoder215.mobchip.combat.EntityCombatTracker;
import me.gamercoder215.mobchip.nbt.EntityNBT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;

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

    default void updateActivities(Creature c) {}

    default BehaviorResult hearNoteblock(Creature c, Location loc) {
        return new BehaviorResult() {
            @Override
            public @NotNull Status getStatus() {
                return Status.STOPPED;
            }

            @Override
            public void stop() {}
        };
    }

    default BehaviorResult setDisturbanceLocation(Creature c, Location loc) {
        return new BehaviorResult() {
            @Override
            public @NotNull Status getStatus() {
                return Status.STOPPED;
            }
            @Override
            public void stop() {}
        };
    }

    default void updateGoals(Map<Integer, Pathfinder> goals, Map<Integer, Boolean> target) {
        try {
            for (Map.Entry<Integer, Pathfinder> e : goals.entrySet())
                addPathfinder(e.getValue(), e.getKey(), target.get(e.getKey()));
        } catch (ClassCastException e) {
            Bukkit.getLogger().severe("[MobChip] Invalid Projectile Source: " + e.getMessage());
            for (StackTraceElement s : e.getStackTrace())
                Bukkit.getLogger().severe("[MobChip] " + s.toString());
        }
    }

    /**
     * Finds the classes of an array of objects, using unboxed classes where possible.
     * (ex. int.class rather than Integer.class)
     * @param args Objects
     * @return Classes of objects
     */
    static Class<?>[] getArgTypes(Object... args) {
        Class<?> types[] = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                types[i] = (Class<?>) args[i].getClass().getDeclaredField("TYPE").get(null);
            } catch (ReflectiveOperationException ignored) {
                types[i] = args[i].getClass();
            }
        }
        return types;
    }

    static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
    }

    static ChipUtil getWrapper() {
        try {
            String pkg = ChipUtil.class.getPackage().getName();
            return (ChipUtil)Class.forName(pkg + ".ChipUtil" + getServerVersion()).getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Invalid Version: " + getServerVersion());
        }
    }

}
