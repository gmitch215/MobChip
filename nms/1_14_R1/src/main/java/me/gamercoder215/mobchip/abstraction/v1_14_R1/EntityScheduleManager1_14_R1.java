package me.gamercoder215.mobchip.abstraction.v1_14_R1;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import me.gamercoder215.mobchip.abstraction.ChipUtil1_14_R1;
import me.gamercoder215.mobchip.ai.schedule.Activity;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import net.minecraft.server.v1_14_R1.EntityInsentient;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;

public final class EntityScheduleManager1_14_R1 implements EntityScheduleManager {

    private final EntityInsentient nmsMob;
    private final Mob m;

    public EntityScheduleManager1_14_R1(Mob m) {
        this.m = m;
        this.nmsMob = ChipUtil1_14_R1.toNMS(m);
    }


    @Override
    public @Nullable me.gamercoder215.mobchip.ai.schedule.Schedule getCurrentSchedule() {
        return ChipUtil1_14_R1.fromNMS(nmsMob.getBehaviorController().getSchedule());
    }

    @Override
    public void setSchedule(@NotNull me.gamercoder215.mobchip.ai.schedule.Schedule s) {
        nmsMob.getBehaviorController().setSchedule(ChipUtil1_14_R1.toNMS(s));
    }

    @Override
    public @NotNull Set<Activity> getActiveActivities() {
        return ChipUtil1_14_R1.getActiveActivities(m);
    }

    @Override
    public void setDefaultActivity(@NotNull Activity a) {
        nmsMob.getBehaviorController().b(ChipUtil1_14_R1.toNMS(a));
    }

    @Override
    public void useDefaultActivity() {
        // doesn't exist
    }

    @Override
    public void setRunningActivity(@NotNull Activity a) {
        nmsMob.getBehaviorController().a(ChipUtil1_14_R1.toNMS(a));
    }

    @Override
    public @Nullable Activity getRunningActivity() {
        return null; // doesn't exist
    }

    @Override
    public boolean isRunning(@NotNull Activity a) {
        return nmsMob.getBehaviorController().c(ChipUtil1_14_R1.toNMS(a));
    }

    @Override
    public int size() {
        return Math.abs((int) nmsMob.getBehaviorController().d().count());
    }

    @Override
    public boolean isEmpty() {
        return !nmsMob.getBehaviorController().d().findAny().isPresent();
    }

    @Nullable
    @Override
    public Consumer<Mob> put(@NotNull Activity key, Consumer<Mob> value) {
        nmsMob.getBehaviorController().a(ChipUtil1_14_R1.toNMS(key), ImmutableList.of(new Pair<>(0, ChipUtil1_14_R1.toNMS(value))));
        return value;
    }

    @Override
    public void clear() {
        // doesn't exist
    }

}
