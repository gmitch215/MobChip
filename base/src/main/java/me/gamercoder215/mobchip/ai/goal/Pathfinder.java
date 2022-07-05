package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder Goal of an Entity.
 * <p>
 * <strong>For custom pathfinders, extend {@link CustomPathfinder}.</strong>
 */
public abstract class Pathfinder implements PathfinderInfo {

	// Private Static Reflection Utils

	// Other

	/**
	 * This Entity.
	 */
	protected final Mob entity;

	/**
	 * Constructs a Pathfinder.
	 * @param entity Entity to use
	 * @throws IllegalArgumentException if entity is null
	 */
	protected Pathfinder(@NotNull Mob entity) throws IllegalArgumentException {
		if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
		this.entity = entity;
	}

	/**
	 * Get the entity involved in this Pathfinder.
	 * @return Creature involved
	 */
	@NotNull
	public Mob getEntity() {
		return this.entity;
	}

	/**
	 * Fetches a Set of Flags that this Pathfinder has.
	 * @return Set of Pathfinder Flags
	 */
	@NotNull
	public abstract PathfinderFlag[] getFlags();

    /**
     * Pathfinder Flags for a Pathfinder
     */
    public enum PathfinderFlag {
        /**
         * Flag representing that the Pathfinder will move the entity
         */
        MOVEMENT,
        /**
         * Flag representing that the Pathfinder will target something/someone
         */
        TARGETING,
        /**
         * Flag representing that the Pathfinder will make the entity look at something/someone
         */
        LOOKING,
        /**
         * Flag representing that the Pathfinder involves making the entity jump
         */
        JUMPING
    }
}
