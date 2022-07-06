package me.gamercoder215.mobchip.ai.schedule;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Represents the Schedule Manager for this Entity.
 * <br><br>
 * A {@link Schedule} is a routine that the Mob performs between every MC Day and Night, starting at 0 ticks and ending at 24,000 ticks.
 * <br><br>
 * Activities can switch automatically depending on the current action (e.g. during a raid {@link Activity#RAID} will be used). Setting active activities that do not apply to the Mob or are set at a poor time will be ignored.
 */
public interface EntityScheduleManager extends Map<Activity, Consumer<? extends Mob>> {

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
     * Sets the current default Activity when no schedule is present.
     * @param activity Activity to set
     */
    void setDefaultActivity(@NotNull Activity activity);

    /**
     * Uses the current default activity, overriding the current schedule.
     */
    void useDefaultActivity();

    /**
     * Sets the Activity that is currently running.
     * @param activity Activity Running
     */
    void setRunningActivity(@NotNull Activity activity);

    /**
     * Whether this Activity is currently active and running.
     * @param activity Activity to check
     * @return true if active, else false
     */
    boolean isRunning(@NotNull Activity activity);



}
