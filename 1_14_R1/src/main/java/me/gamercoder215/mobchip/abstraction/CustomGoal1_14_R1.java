package me.gamercoder215.mobchip.abstraction;

import me.gamercoder215.mobchip.ai.goal.CustomPathfinder;
import net.minecraft.server.v1_14_R1.PathfinderGoal;

public class CustomGoal1_14_R1 extends PathfinderGoal {

    private final CustomPathfinder p;

    public CustomGoal1_14_R1(CustomPathfinder p) {
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
    public boolean C_() {
        return p.canInterrupt();
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