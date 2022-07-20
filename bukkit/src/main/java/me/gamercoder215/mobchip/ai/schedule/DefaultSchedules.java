package me.gamercoder215.mobchip.ai.schedule;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents Built-In MC Schedules
 */
public final class DefaultSchedules {

    private DefaultSchedules() {}

    private static final ChipUtil wrapper = ChipUtil.getWrapper();

    /**
     * Represents an Empty Schedule
     */
    public static final Schedule EMPTY = wrapper.getDefaultSchedule("empty");
    /**
     * Represents a Simple Schedule with working at 5,000 ticks and resting at 11,000 ticks.
     */
    public static final Schedule SIMPLE = wrapper.getDefaultSchedule("simple");
    /**
     * Represents an Adult Villager's default schedule.
     */
    public static final Schedule VILLAGER = wrapper.getDefaultSchedule("villager_default");
    /**
     * Represents a Baby Villager's default schedule.
     */
    public static final Schedule BABY_VILLAGER = wrapper.getDefaultSchedule("villager_baby");

    /**
     * Fetches a Schedule from the Minecraft registrar.
     * @param id ID of the Schedule
     * @return Schedule found, or {@link #EMPTY} if not found
     */
    @NotNull
    public static Schedule get(@Nullable String id) {
        if (id == null) return EMPTY;
        Schedule s = wrapper.getDefaultSchedule(id);
        if (s == null) return EMPTY; else return s;
    }

}
