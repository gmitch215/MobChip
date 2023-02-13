package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.enderdragon.CustomPhase;
import me.gamercoder215.mobchip.DragonBrain;
import me.gamercoder215.mobchip.ai.enderdragon.DragonPhase;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.jetbrains.annotations.NotNull;

class BukkitDragonBrain extends BukkitBrain implements DragonBrain {

    final EnderDragon m;

    BukkitDragonBrain(@NotNull EnderDragon dragon) {
        super(dragon);
        this.m = dragon;
    }

    @Override
    public void setCustomPhase(@NotNull CustomPhase phase) throws IllegalArgumentException {
        w.setCustomPhase(m, phase);
    }

    @Override
    public EnderCrystal getNearestCrystal() {
        return w.getNearestCrystal(m);
    }

    @Override
    public @NotNull DragonPhase getCurrentPhase() {
        return w.getCurrentPhase(m);
    }
}
