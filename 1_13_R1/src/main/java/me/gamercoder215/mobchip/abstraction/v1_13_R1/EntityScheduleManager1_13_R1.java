package me.gamercoder215.mobchip.abstraction.v1_13_R1;

import me.gamercoder215.mobchip.abstraction.ChipUtil1_13_R1;
import me.gamercoder215.mobchip.ai.schedule.Activity;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import net.minecraft.server.v1_13_R1.EntityInsentient;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;

public final class EntityScheduleManager1_13_R1 implements EntityScheduleManager {

    private final EntityInsentient nmsMob;
    private final Mob m;

    public EntityScheduleManager1_13_R1(Mob m) {
        this.m = m;
        this.nmsMob = ChipUtil1_13_R1.toNMS(m);
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
