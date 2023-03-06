package me.gamercoder215.mobchip.abstraction.v1_13_R2;

import me.gamercoder215.mobchip.ai.goal.CustomPathfinder;
import net.minecraft.server.v1_13_R2.PathfinderGoal;

final class CustomGoal1_13_R2 extends PathfinderGoal {

    private final CustomPathfinder p;

    public CustomGoal1_13_R2(CustomPathfinder p) {
        this.p = p;
    }

    @Override
    public boolean a() {
        return p.canStart();
    }
    @Override
    public boolean b() {
        return p.canContinueToUse();
    }

    @Override
    public void c() {
        p.start();
    }

    @Override
    public void e() {
        p.tick();
    }

    @Override
    public void d() { p.stop(); }

    public CustomPathfinder getPathfinder() {
        return p;
    }
}