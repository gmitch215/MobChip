package me.gamercoder215.mobchip.abstraction.v1_19_R1;

import me.gamercoder215.mobchip.ai.goal.CustomPathfinder;
import net.minecraft.world.entity.ai.goal.Goal;

final class CustomGoal1_19_R1 extends Goal {

    private final CustomPathfinder p;

    public CustomGoal1_19_R1(CustomPathfinder p) {
        this.p = p;
    }

    @Override
    public boolean canUse() {
        return p.canStart();
    }
    @Override
    public boolean canContinueToUse() {
        return p.canContinueToUse();
    }
    @Override
    public boolean isInterruptable() {
        return p.canInterrupt();
    }

    @Override
    public void start() {
        p.start();
    }

    @Override
    public void tick() {
        p.tick();
    }

    @Override
    public void stop() {
        p.stop();
    }

    public CustomPathfinder getPathfinder() {
        return p;
    }
}
