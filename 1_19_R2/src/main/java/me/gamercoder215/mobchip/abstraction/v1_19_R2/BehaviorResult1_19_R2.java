package me.gamercoder215.mobchip.abstraction.v1_19_R2;

import me.gamercoder215.mobchip.abstraction.ChipUtil1_19_R2;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class BehaviorResult1_19_R2 extends BehaviorResult {
    private final Behavior<? super LivingEntity> b;
    private final net.minecraft.world.entity.Mob mob;
    private final ServerLevel l;

    public BehaviorResult1_19_R2(Behavior<? super LivingEntity> b, net.minecraft.world.entity.Mob mob) {
        this.b = b;
        this.mob = mob;
        this.l = ChipUtil1_19_R2.toNMS(Bukkit.getWorld(mob.level.getWorld().getUID()));

        b.tryStart(l, mob, 0);
    }

    @Override
    public @NotNull Status getStatus() {
        return ChipUtil1_19_R2.fromNMS(b.getStatus());
    }

    @Override
    public void stop() {
        b.doStop(l, mob, 0);
    }
}
