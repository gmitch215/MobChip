package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.AxolotlBehavior;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import org.bukkit.entity.Axolotl;
import org.jetbrains.annotations.NotNull;

class BukkitAxolotlBehavior extends BukkitUpdatableCreatureBehavior implements AxolotlBehavior {

    final Axolotl m;

    public BukkitAxolotlBehavior(Axolotl a) {
        super(a);
        this.m = a;
    }

    @Override
    public @NotNull BehaviorResult playDead() {
        return run("PlayDead");
    }
}
