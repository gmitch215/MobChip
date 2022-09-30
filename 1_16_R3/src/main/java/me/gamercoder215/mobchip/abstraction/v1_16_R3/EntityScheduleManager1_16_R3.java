package me.gamercoder215.mobchip.abstraction.v1_16_R3;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.mobchip.abstraction.ChipUtil1_16_R3;
import me.gamercoder215.mobchip.ai.schedule.Activity;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public final class EntityScheduleManager1_16_R3 implements EntityScheduleManager {

    private final EntityInsentient nmsMob;
    private final Mob m;

    public EntityScheduleManager1_16_R3(Mob m) {
        this.m = m;
        this.nmsMob = ChipUtil1_16_R3.toNMS(m);
    }


    @Override
    public @Nullable me.gamercoder215.mobchip.ai.schedule.Schedule getCurrentSchedule() {
        return ChipUtil1_16_R3.fromNMS(nmsMob.getBehaviorController().getSchedule());
    }

    @Override
    public void setSchedule(@NotNull me.gamercoder215.mobchip.ai.schedule.Schedule s) {
        nmsMob.getBehaviorController().setSchedule(ChipUtil1_16_R3.toNMS(s));
    }

    @Override
    public @NotNull Set<Activity> getActiveActivities() {
        return ChipUtil1_16_R3.getActiveActivities(m);
    }

    @Override
    public void setDefaultActivity(@NotNull Activity a) {
        nmsMob.getBehaviorController().b(ChipUtil1_16_R3.toNMS(a));
    }

    @Override
    public void useDefaultActivity() {
        nmsMob.getBehaviorController().e();
    }

    @Override
    public void setRunningActivity(@NotNull Activity a) {
        nmsMob.getBehaviorController().a(ChipUtil1_16_R3.toNMS(a));
    }

    @Override
    public @Nullable Activity getRunningActivity() {
        return nmsMob.getBehaviorController().f().isPresent() ? ChipUtil1_16_R3.fromNMS(nmsMob.getBehaviorController().f().get()) : null;
    }

    @Override
    public boolean isRunning(@NotNull Activity a) {
        return nmsMob.getBehaviorController().c(ChipUtil1_16_R3.toNMS(a));
    }

    @Override
    public int size() {
        return nmsMob.getBehaviorController().d().size();
    }

    @Override
    public boolean isEmpty() {
        return nmsMob.getBehaviorController().d().isEmpty();
    }

    @Nullable
    @Override
    public Consumer<Mob> put(@NotNull Activity key, Consumer<Mob> value) {
        nmsMob.getBehaviorController().a(ChipUtil1_16_R3.toNMS(key), 0, ImmutableList.of(ChipUtil1_16_R3.toNMS(value)));
        return value;
    }

    @Override
    public void clear() {
        // doesn't exist
    }

}
