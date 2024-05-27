package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.Animals;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder that causes Animals to breed
 */
public final class PathfinderBreed extends Pathfinder implements SpeedModifier {
	
	private double speedMod;

	/**
	 * Constructs a PathfinderBreed with no speed modifier.
	 * @param m Animal to use
	 */
	public PathfinderBreed(@NotNull Animals m) {
		this(m, 1);
	}

	/**
	 * Constructs a PathfinderBreed.
	 * @param m Animal to use
	 * @param speedMod Speed Modifier while breeding
	 */
	public PathfinderBreed(@NotNull Animals m, double speedMod) {
		super(m);
		this.speedMod = speedMod;
	}

	@Override
	public @NotNull Animals getEntity() { return (Animals) entity; }

	@Override
	public double getSpeedModifier() {
		return this.speedMod;
	}

	@Override
	public void setSpeedModifier(double mod) {
		this.speedMod = mod;
	}

	@Override
	public @NotNull PathfinderFlag[] getFlags() {
		return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.LOOKING };
	}

	@Override
	public String getInternalName() { return "PathfinderGoalBreed"; }
}
