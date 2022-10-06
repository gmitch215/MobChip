package me.gamercoder215.mobchip.abstraction.v1_16_R1;

import me.gamercoder215.mobchip.abstraction.ChipUtil1_16_R1;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import net.minecraft.server.v1_16_R1.Behavior;
import net.minecraft.server.v1_16_R1.EntityInsentient;
import net.minecraft.server.v1_16_R1.EntityLiving;
import net.minecraft.server.v1_16_R1.WorldServer;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class BehaviorResult1_16_R1 extends BehaviorResult {
    private final Behavior<? super EntityLiving> b;
    private final EntityInsentient mob;
    private final WorldServer l;

    public BehaviorResult1_16_R1(Behavior<? super EntityLiving> b, EntityInsentient mob) {
        this.b = b;
        this.mob = mob;
        this.l = ChipUtil1_16_R1.toNMS(Bukkit.getWorld(mob.world.getWorld().getUID()));

        b.e(l, mob, 0);
    }

    @Override
    public @NotNull Status getStatus() {
        return ChipUtil1_16_R1.fromNMS(b.a());
    }

    @Override
    public void stop() {
        b.g(l, mob, 0);
    }
}
