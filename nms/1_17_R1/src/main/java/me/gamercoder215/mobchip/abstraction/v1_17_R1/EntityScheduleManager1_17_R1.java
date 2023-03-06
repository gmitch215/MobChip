package me.gamercoder215.mobchip.abstraction.v1_17_R1;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.mobchip.ai.schedule.Activity;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import me.gamercoder215.mobchip.ai.schedule.Schedule;
import net.minecraft.world.entity.EntityInsentient;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

final class EntityScheduleManager1_17_R1 implements EntityScheduleManager {

    private final EntityInsentient nmsMob;
 
    public EntityScheduleManager1_17_R1(Mob m) {
        this.nmsMob = ChipUtil1_17_R1.toNMS(m);
    }


    @Override
    public @Nullable Schedule getCurrentSchedule() {
        return ChipUtil1_17_R1.fromNMS(nmsMob.getBehaviorController().getSchedule());
    }

    @Override
    public void setSchedule(@NotNull Schedule s) {
        nmsMob.getBehaviorController().setSchedule(ChipUtil1_17_R1.toNMS(s));
    }

    @Override
    public @NotNull Set<Activity> getActiveActivities() {
        return nmsMob.getBehaviorController().c().stream().map(ChipUtil1_17_R1::fromNMS).collect(Collectors.toSet());
    }

    @Override
    public void setDefaultActivity(@NotNull Activity a) {
        nmsMob.getBehaviorController().b(ChipUtil1_17_R1.toNMS(a));
    }

    @Override
    public void useDefaultActivity() {
        nmsMob.getBehaviorController().e();
    }

    @Override
    public void setRunningActivity(@NotNull Activity a) {
        nmsMob.getBehaviorController().a(ChipUtil1_17_R1.toNMS(a));
    }

    @Override
    public @Nullable Activity getRunningActivity() {
        return nmsMob.getBehaviorController().f().isPresent() ? ChipUtil1_17_R1.fromNMS(nmsMob.getBehaviorController().f().get()) : null;
    }

    @Override
    public boolean isRunning(@NotNull Activity a) {
        return nmsMob.getBehaviorController().c(ChipUtil1_17_R1.toNMS(a));
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
        nmsMob.getBehaviorController().a(ChipUtil1_17_R1.toNMS(key), 0, ImmutableList.of(ChipUtil1_17_R1.toNMS(value)));
        return value;
    }

    @Override
    public void clear() {
        nmsMob.getBehaviorController().g();
    }

}
