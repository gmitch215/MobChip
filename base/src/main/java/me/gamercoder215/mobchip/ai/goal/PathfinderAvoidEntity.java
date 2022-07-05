package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.ai.goal.target.Filtering;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder to avoid a LivingEntity
 * @param <T> Class of Entities to avoid
 */
public final class PathfinderAvoidEntity<T extends LivingEntity> extends Pathfinder implements Filtering<T>, SpeedModifier {

	private double speedModifier;
	private float maxDistance;
	private double sprintModifier;
	private Class<T> filter;

	/**
	 * Constructs a PathfinderAvoidEntity with a max distance of 5 and using the default speed modifiers.
	 * @param m Creature to use
	 * @param filter Filter of entity to avoid
	 * @throws IllegalArgumentException if filter is null
	 */
	public PathfinderAvoidEntity(@NotNull Creature m, @NotNull Class<T> filter) throws IllegalArgumentException {
		this(m, filter, 5, DEFAULT_SPEED_MODIFIER);
	}

	/**
	 * Constructs a PathfinderAvoidEntity with both modifiers the same.
	 * @param m Creature to use
	 * @param filter Filter of entity to avoid
	 * @param dist Maximum Distance away to stop fleeing
	 * @param mod Sprinting/Walking away modifier
	 * @throws IllegalArgumentException if filter is null
	 */
	public PathfinderAvoidEntity(@NotNull Creature m, @NotNull Class<T> filter, float dist, double mod) throws IllegalArgumentException {
		this(m, filter, dist, mod, mod);
	}

	/**
	 * Constructs a PathfinderAvoidEntity.
	 * @param m Creature to use
	 * @param filter Filter of entity to avoid
	 * @param dist Maximum Distance away to stop fleeing
	 * @param walkMod Walking away modifier
	 * @param sprintMod Sprinting away modifier
	 * @throws IllegalArgumentException if filter is null
	 */
	public PathfinderAvoidEntity(@NotNull Creature m, @NotNull Class<T> filter, float dist, double walkMod, double sprintMod) throws IllegalArgumentException {
		super(m);
		this.filter = filter;
		this.speedModifier = walkMod;
		this.maxDistance = dist;
		this.sprintModifier = sprintMod;
	}
	
	/**
	 * Get the maximum distance needed to stop avoiding the target, in blocks (meters).
	 * @return Max distance to stop avoiding
	 */
	public float getMaxDistance() {
		return this.maxDistance;
	}
	
	/**
	 * Sets the maximum distance needed to stop avoiding the target, in blocks (meters).
	 * @param dist New distance to set
	 */
	public void setMaxDistance(float dist) {
		this.maxDistance = dist;
	}

	@Override
	public double getSpeedModifier() {
		return this.speedModifier;
	}

	/**
	 * Gets the current sprint modifier.
	 * @return Sprint Modifier
	 */
	public double getSprintModifier() {
		return this.sprintModifier;
	}

	/**
	 * Sets the current Sprint Modifier.
	 * @param mod Sprint Modifier
	 */
	public void setSprintModifier(double mod) {
		this.sprintModifier = mod;
	}

	@Override
	public void setSpeedModifier(double mod) {
		this.speedModifier = mod;
	}

	@Override
	public void setFilter(@NotNull Class<T> clazz) {
		this.filter = clazz;
	}

	@Override
	public @NotNull Class<T> getFilter() {
		return this.filter;
	}

	@Override
	public Creature getEntity() { return (Creature) entity; }

	@Override
	public @NotNull PathfinderFlag[] getFlags() {
		return new PathfinderFlag[] { PathfinderFlag.MOVEMENT };
	}

	@Override
	public String getInternalName() { return "PathfinderGoalAvoidTarget"; }
}
