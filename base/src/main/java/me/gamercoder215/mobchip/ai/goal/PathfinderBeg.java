package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Wolf;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for Wolf Begging
 */
public final class PathfinderBeg extends Pathfinder implements Ranged {
	
	private float lookRange = DEFAULT_LOOK_RANGE;
	
	/**
	 * Constructs a PathfinderBeg with the default look range.
	 * @param w Wolf to use
	 */
	public PathfinderBeg(@NotNull Wolf w) {
		this(w, DEFAULT_LOOK_RANGE);
	}
	
	/**
	 * Constructs a PathfinderBeg.
	 * @param w Wolf to use
	 * @param lookRange looking range of the wolf
	 */
	public PathfinderBeg(@NotNull Wolf w, float lookRange) {
		super(w);
		this.lookRange = lookRange;
	}

	@Override
	public Wolf getEntity() { return (Wolf) entity; }

	/**
	 * Get the look range of this PathfinderBeg.
	 */
	@Override
	public float getRange() {
		return this.lookRange;
	}

	/**
	 * Sets the look range of this PathfinderBeg.
	 */
	@Override
	public void setRange(float range) {
		this.lookRange = range;
	}

	@Override
	public @NotNull PathfinderFlag[] getFlags() {
		return new PathfinderFlag[] { PathfinderFlag.LOOKING };
	}

	@Override
	public String getInternalName() { return "PathfinderGoalBeg"; }
}
