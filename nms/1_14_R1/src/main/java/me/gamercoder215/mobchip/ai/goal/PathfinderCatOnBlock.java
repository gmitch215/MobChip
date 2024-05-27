package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.Cat;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Cat to sit on a block
 */
public final class PathfinderCatOnBlock extends Pathfinder implements SpeedModifier {

	private double speedMod;
	
	/**
	 * Constructs a PathfinderCatOnBlock.
	 * @param cat Cat to use
	 * @param speedMod Speed Modifier
	 */
	public PathfinderCatOnBlock(@NotNull Cat cat, double speedMod) {
		super(cat);
		
		this.speedMod = speedMod;
	}

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
		return new PathfinderFlag[0];
	}

	@Override
	public @NotNull Cat getEntity() { return (Cat) entity; }

	@Override
	public String getInternalName() { return "PathfinderGoalJumpOnBlock"; }
}
