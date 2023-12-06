package me.gamercoder215.mobchip.bukkit;

import org.bukkit.entity.Breeze;

import me.gamercoder215.mobchip.ai.behavior.BreezeBehavior;

class BukkitBreezeBehavior extends BukkitUpdatableCreatureBehavior implements BreezeBehavior {
    
    final Breeze m;

    public BukkitBreezeBehavior(Breeze m) {
        super(m);
        this.m = m;
    }

    @Override
    public void longJump() {
        wrapper.runBehavior(m, "LongJump", "net.minecraft.world.entity.monster.breeze");
    }

    @Override
    public void shoot() {
        wrapper.runBehavior(m, "Shoot", "net.minecraft.world.entity.monster.breeze");
    }

    @Override
    public void slide() {
        wrapper.runBehavior(m, "Slide", "net.minecraft.world.entity.monster.breeze");
    }

}
