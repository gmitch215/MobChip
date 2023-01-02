package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.schedule.Updatable;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Represents Behavior for an Allay
 */
public interface AllayBehavior extends CreatureBehavior, Updatable {

    /**
     * Makes this Allay listen to a Note Block.
     * <p>This behavior does not require any memories.</p>
     * @param loc Location of the Note Block
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult hearNoteblock(@NotNull Location loc);

}
