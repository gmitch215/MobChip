package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.CamelBehavior;
import org.bukkit.entity.Camel;

class BukkitCamelBehavior extends BukkitUpdatableCreatureBehavior implements CamelBehavior {

    final Camel m;

    public BukkitCamelBehavior(Camel m) {
        super(m);
        this.m = m;
    }

    @Override
    public void sit(int minimalPoseTicks) {
        wrapper.runBehavior(m, "CamelAi$RandomSitting", "net.minecraft.world.entity.animal.camel", minimalPoseTicks);
    }
}
