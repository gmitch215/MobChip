package me.gamercoder215.mobchip.ai.behavior;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents Entity Behaviors for an Ender Dragon
*/
public interface DragonBehavior extends EntityBehavior {

    /**
     * Makes this Ender Dragon perform a natural knockback with its wings when Entities get too close.
     * @param entities List of Entities to knock back
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult naturalKnockback(@Nullable List<Entity> entities);

}
