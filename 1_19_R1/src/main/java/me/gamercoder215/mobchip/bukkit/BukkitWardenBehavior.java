package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.WardenBehavior;
import org.bukkit.Location;
import org.bukkit.entity.Warden;
import org.jetbrains.annotations.NotNull;

class BukkitWardenBehavior extends BukkitUpdatableCreatureBehavior implements WardenBehavior {

    final Warden m;

    public BukkitWardenBehavior(Warden m) {
        super(m);
        this.m = m;
    }

    @Override
    public @NotNull BehaviorResult setDisturbanceLocation(@NotNull Location loc) {
        return wrapper.setDisturbanceLocation(m, loc);
    }

    @Override
    public @NotNull BehaviorResult dig(int duration) {
        return run("Digging", duration);
    }

    @Override
    public @NotNull BehaviorResult emerge(int duration) {
        return run("Emerging", duration);
    }

    @Override
    public @NotNull BehaviorResult roar() {
        return run("Roar");
    }

    @Override
    public @NotNull BehaviorResult sonicBoom() {
        return run("SonicBoom");
    }

    @Override
    public @NotNull BehaviorResult sniff(int duration) {
        run("TryToSniff");
        return run("Sniffing", duration);
    }
}
