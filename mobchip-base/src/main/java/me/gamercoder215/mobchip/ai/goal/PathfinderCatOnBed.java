package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Cat;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.ai.goal.CatLieOnBedGoal;

/**
 * Represents a Pathfinder for a Cat to sit on a Bed
 */
public final class PathfinderCatOnBed extends Pathfinder implements SpeedModifier, Ranged {

	private double speedMod;
	private int range;
	
	// TODO Create NMS Constructor
	
	/**
	 * Constructs a PathfinderCatOnBed with a range of 4.
	 * @param cat Cat to use
	 * @param speedMod Speed Modifier
	 */
	public PathfinderCatOnBed(@NotNull Cat cat, double speedMod) {
		this(cat, speedMod, 4);
	}
	
	/**
	 * Constructs a PathfinderCatOnBed.
	 * @param cat Cat to use
	 * @param speedMod Speed Modifier
	 * @param range Range to use
	 */
	public PathfinderCatOnBed(@NotNull Cat cat, double speedMod, int range) {
		super(cat);
		
		this.speedMod = speedMod;
		this.range = range;
	}
	
	@Override
	public float getRange() {
		return this.range;
	}

	@Override
	public void setRange(float range) throws IllegalArgumentException {
		if (range > Integer.MAX_VALUE) throw new IllegalArgumentException("Must be an integer");
		
		this.range = (int) Math.floor(range);
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
	public CatLieOnBedGoal getHandle() {
		return new CatLieOnBedGoal((net.minecraft.world.entity.animal.Cat) nmsEntity, speedMod, range);
	}

}
