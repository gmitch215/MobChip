package me.gamercoder215.mobchip.ai.schedule;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Represents the Schedule Manager for this Entity.
 * <br><br>
 * A {@link Schedule} is a routine that the Mob performs between every MC Day and Night, starting at 0 ticks and ending at 24,000 ticks.
 * <br><br>
 * Activities can switch automatically depending on the current action (e.g. during a raid {@link Activity#RAID} will be used). Setting active activities that do not apply to the Mob or are set at a poor time will be ignored.
 */
public interface EntityScheduleManager {

    /**
     * Fetches the current schedule that this Mob has.
     * @return Current Schedule, or null if none
     */
    @Nullable
    Schedule getCurrentSchedule();

    /**
     * Sets the current schedule.
     * @param schedule Schedule to set
     */
    void setSchedule(@NotNull Schedule schedule);

    /**
     * Fetches a set of all active activities.
     * @return Set of active activities
     */
    @NotNull
    Set<Activity> getActiveActivities();

    /**
     * Sets the current default Activity when no schedule is present.
     * @param activity Activity to set
     */
    void setDefaultActivity(@NotNull Activity activity);

    /**
     * Uses the current default activity, overriding the current schedule.
     */
    void useDefaultActivity();

    /**
     * Sets the Secondary Activity that is currently running.
     * <br><br>
     * This activity wil not override the Activities in the current schedule, but is useful when running two activities at once.
     * @param activity Activity Running
     */
    void setRunningActivity(@NotNull Activity activity);

    /**
     * Fetches the Secondary Activity that is currently running.
     * @return Secondary Activity Running, or null if none
     */
    @Nullable
    Activity getRunningActivity();

    /**
     * Whether this Activity is currently active and running.
     * @param activity Activity to check
     * @return true if active, else false
     */
    boolean isRunning(@NotNull Activity activity);

    /**
     * Associates an Activity with a function.
     * @param a Activity to add
     * @param func Function associated with this Activity
     * @return Function added
     */
    @Nullable
    Consumer<Mob> put(@NotNull Activity a, Consumer<Mob> func);

    /**
     * Whether the current running Activity Map is empty.
     * @return true if no activities are associated, else false
     */
    boolean isEmpty();

    /**
     * Fetches the current Activity Map size.
     * @return Activity Map Size
     */
    int size();

    /**
     * Associates all the activities with their respective functions.
     * @param map Activity Map to put
     */
    default void putAll(Map<Activity, Consumer<Mob>> map) {
        map.forEach(this::put);
    }

    /**
     * Removes all activities from the current Activity Map.
     */
    void clear();



}
