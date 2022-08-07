package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.FrogBehavior;
import org.bukkit.Sound;
import org.bukkit.entity.Frog;
import org.jetbrains.annotations.NotNull;

class BukkitFrogBehavior extends BukkitUpdatableCreatureBehavior implements FrogBehavior {

    final Frog m;

    public BukkitFrogBehavior(Frog f) {
        super(f);
        this.m = f;
    }

    @Override
    public @NotNull BehaviorResult shootTongue(Sound tongueSound, Sound eatSound) {
        return run("ShootTongue");
    }

    @Override
    public @NotNull BehaviorResult croak() {
        return run("Croak");
    }
}
