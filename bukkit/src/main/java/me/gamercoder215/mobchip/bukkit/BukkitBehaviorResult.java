package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"rawtypes", "unchecked"})
class BukkitBehaviorResult extends BehaviorResult {

    private final Behavior behavior;
    private final ServerLevel level;
    private final LivingEntity nmsMob;

    <T extends LivingEntity> BukkitBehaviorResult(Behavior<T> behavior, ServerLevel level, T nmsMob) {
        this.behavior = behavior;
        behavior.tryStart(level, nmsMob, 0);
        this.level = level;
        this.nmsMob = nmsMob;
    }

    @Override
    public @NotNull Status getStatus() {
        final Status stat;
        if (behavior.getStatus() == Behavior.Status.RUNNING) stat = Status.RUNNING;
        else stat = Status.STOPPED;

        return stat;
    }

    @Override
    public void stop() {
        behavior.doStop(level, nmsMob, 0);
    }
}
