package me.gamercoder215.mobchip;

import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.enderdragon.CustomPhase;
import org.jetbrains.annotations.NotNull;

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

}
