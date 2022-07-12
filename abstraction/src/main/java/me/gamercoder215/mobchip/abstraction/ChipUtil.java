package me.gamercoder215.mobchip.abstraction;

import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.enderdragon.CustomPhase;
import me.gamercoder215.mobchip.ai.goal.CustomPathfinder;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.goal.WrappedPathfinder;
import me.gamercoder215.mobchip.ai.memories.Memory;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import me.gamercoder215.mobchip.ai.schedule.Schedule;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Represents an Abstract Wrapper
 */
public interface ChipUtil {

    void addCustomPathfinder(CustomPathfinder p, int priority, boolean target);

    Set<WrappedPathfinder> getGoals(Mob m, boolean target);

    Collection<WrappedPathfinder> getRunningGoals(Mob m, boolean target);

    void setFlag(Mob m, Pathfinder.PathfinderFlag flag, boolean target, boolean value);

    void addPathfinder(Pathfinder p, int priority, boolean target);

    void removePathfinder(Pathfinder p, boolean target);

    void clearPathfinders(Mob mob, boolean target);

    default void addPathfinders(Collection<? extends WrappedPathfinder> c, boolean target) {
        for (WrappedPathfinder p : c) addPathfinder(p.getPathfinder(), p.getPriority(), target);
    }

    BehaviorResult runBehavior(Mob m, String behaviorName, Object... args);

    BehaviorResult runBehavior(Mob m, String behaviorName, String packageName, Object... args);

    EntityController getController(Mob m);

    EntityNavigation getNavigation(Mob m);

    EntityBody getBody(Mob m);

    EntityScheduleManager getManager(Mob m);

    void setCustomPhase(EnderDragon a, CustomPhase c);

     <T>  void setMemory(Mob mob, Memory<T> m, T value);

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
