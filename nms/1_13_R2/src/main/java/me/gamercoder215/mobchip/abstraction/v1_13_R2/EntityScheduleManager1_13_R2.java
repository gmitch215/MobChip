package me.gamercoder215.mobchip.abstraction.v1_13_R2;

import me.gamercoder215.mobchip.ai.schedule.Activity;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;

final class EntityScheduleManager1_13_R2 implements EntityScheduleManager {

    public EntityScheduleManager1_13_R2(Mob m) {
        // doesn't exist
    }


    @Override
    public @Nullable me.gamercoder215.mobchip.ai.schedule.Schedule getCurrentSchedule() {
        return null; // doesn't exist
    }

    @Override
    public void setSchedule(@NotNull me.gamercoder215.mobchip.ai.schedule.Schedule s) {
        // doesn't exist
    }

    @Override
    public @NotNull Set<Activity> getActiveActivities() {
        return Collections.emptySet(); // doesn't exist
    }

    @Override
    public void setDefaultActivity(@NotNull Activity a) {
        // doesn't exist
    }

    @Override
    public void useDefaultActivity() {
        // doesn't exist
    }

    @Override
    public void setRunningActivity(@NotNull Activity a) {
        // doesn't exist
    }

    @Override
    public @Nullable Activity getRunningActivity() {
        return null; // doesn't exist
    }

    @Override
    public boolean isRunning(@NotNull Activity a) {
        return false; // doesn't exist
    }

    @Override
    public int size() {
        return 0; // doesn't exist
    }

    @Override
    public boolean isEmpty() {
        return true; // doesn't exist
    }

    @Nullable
    @Override
    public Consumer<Mob> put(@NotNull Activity key, Consumer<Mob> value) {
        return value; // doesn't exist
    }

    @Override
    public void clear() {
        // doesn't exist
    }

}
