package me.gamercoder215.mobchip.ai.schedule;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a Mob's Daily Routine
 */
public final class Schedule {

    private final Map<Integer, Activity> timeline;

    private Schedule(Map<Integer, Activity> timeline) {
        this.timeline = timeline;
    }

    /**
     * Fetches the Schedule builder.
     * @return Schedule Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Fetches the size of this Schedule.
     * @return Size of this Schedule
     */
    public int size() {
        return timeline.size();
    }

    /**
     * Whether this Schedule contains this timestamp.
     * @param key Timestamp
     * @return true if contains, else false
     */
    public boolean contains(int key) {
        return timeline.containsKey(key);
    }

    /**
     * Whether this Schedule contains this Activity.
     * @param value Activity
     * @return true if contains, else false
     */
    public boolean contains(@NotNull Activity value) {
        return timeline.containsValue(value);
    }

    /**
     * Fetches the Activity at this timestamp.
     * @param key Timestamp
     * @return Activity found, or null if not found
     */
    @Nullable
    public Activity get(int key) {
        return timeline.get(key);
    }

    /**
     * Fetches all timestamps in this Schedule.
     * @return Schedule's Timestamps
     */
    @NotNull
    public Set<Integer> keySet() {
        return timeline.keySet();
    }

    /**
     * Fetches all activities in this Schedule.
     * @return Schedule's Activities
     */
    @NotNull
    public Collection<Activity> values() {
        return timeline.values();
    }

    /**
     * Creates an entry set for the timestamps to Activities in this Schedule.
     * @return Entry set of timestamps to Activities
     */
    @NotNull
    public Set<Map.Entry<Integer, Activity>> entrySet() {
        return timeline.entrySet();
    }

    /**
     * Builder class for making a {@link Schedule}
     */
    public static final class Builder {

        private final Map<Integer, Activity> map;

        private Builder() {
            this.map = new HashMap<>();
        }

        /**
         * Adds an Activity to the Schedule.
         * @param time Timestamp of activity, in ticks
         * @param activity Activity to add
         * @throws IllegalArgumentException if time is not between 0 and 24000 or if activity is null
         * @return this builder, for chaining
         */
        public Builder addActivity(int time, @NotNull Activity activity) throws IllegalArgumentException {
            if (time < 0 || time > 24000) throw new IllegalArgumentException("Time must be between 0 and 24000");
            if (activity == null) throw new IllegalArgumentException("Activity cannot be null");

            map.put(time, activity);
            return this;
        }

        /**
         * Fetches the current Timeline as a map in this builder.
         * @return Current Timeline
         */
        @NotNull
        public Map<Integer, Activity> getTimeline() {
            return this.map;
        }

        /**
         * Builds the Schedule.
         * @return Built Schedule
         * @throws IllegalArgumentException if timeline is empty
         */
        @NotNull
        public Schedule build() throws IllegalArgumentException {
            if (map.isEmpty()) throw new IllegalArgumentException("Timeline cannot be empty");

            return new Schedule(map);
        }


    }

}
