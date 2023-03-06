package me.gamercoder215.mobchip.abstraction.v1_17_R1;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.Behavior;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

final class BehaviorResult1_17_R1 extends BehaviorResult {
    private final Behavior<? super EntityLiving> b;
    private final EntityInsentient mob;
    private final WorldServer l;

    public BehaviorResult1_17_R1(Behavior<? super EntityLiving> b, EntityInsentient mob) {
        this.b = b;
        this.mob = mob;
        this.l = ChipUtil1_17_R1.toNMS(Bukkit.getWorld(mob.t.getWorld().getUID()));

        b.e(l, mob, 0);
    }

    @Override
    public @NotNull Status getStatus() {
        return ChipUtil1_17_R1.fromNMS(b.a());
    }

    @Override
    public void stop() {
        b.g(l, mob, 0);
    }
}
