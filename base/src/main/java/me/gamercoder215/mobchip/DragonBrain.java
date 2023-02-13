package me.gamercoder215.mobchip;

import me.gamercoder215.mobchip.ai.enderdragon.CustomPhase;
import me.gamercoder215.mobchip.ai.enderdragon.DragonPhase;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a special EntityBrain for Ender Dragons.
 */
public interface DragonBrain extends EntityBrain {

    /**
     * Sets the current custom phase of the Ender Dragon.
     * @param phase Custom Phase to use.
     * @throws IllegalArgumentException if phase is null
     */
    void setCustomPhase(@NotNull CustomPhase phase) throws IllegalArgumentException;

    /**
     * Fetches the nearest ender crystal that is currently healing this Ender Dragon.
     * @return EnderCrystal healing this EnderDragon, may be null
     */
    @Nullable
    EnderCrystal getNearestCrystal();

    /**
     * Fetches the current DragonPhase of the Ender Dragon.
     * @return Current DragonPhase of the Ender Dragon.
     */
    @NotNull
    DragonPhase getCurrentPhase();

}
