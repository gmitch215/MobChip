package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.enderdragon.CustomPhase;
import me.gamercoder215.mobchip.ai.enderdragon.DragonBrain;
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
        wrapper.setCustomPhase(m, phase);
    }
}
