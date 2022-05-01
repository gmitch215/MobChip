package me.gamercoder215.mobchip.ai.goal;

import java.lang.reflect.Field;

import org.bukkit.entity.Wolf;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.ai.goal.BegGoal;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * Represents a Pathfinder for Wolf Begging
 */
public final class PathfinderBeg extends Pathfinder implements Ranged {
	
	private float lookRange = DEFAULT_LOOK_RANGE;
	
	@SuppressWarnings("unused")
	private PathfinderBeg(Goal goal) {
		this((BegGoal) goal);
	}
	
	/**
	 * Constructs a PathfinderBeg from a NMS Goal.
	 * @param goal NMS Goal
	 */
	public PathfinderBeg(@NotNull BegGoal goal) {
		super(Pathfinder.getEntity(goal, "a"));
		
		try {
			Field a = BegGoal.class.getDeclaredField("d");
			a.setAccessible(true);
			this.lookRange = a.getFloat(goal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	}
	
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
	
	/**
	 * Converts this PathfinderBeg into a NMS BegGoal
	 */
	@Override
	public BegGoal getHandle() {
		return new BegGoal((net.minecraft.world.entity.animal.Wolf) nmsEntity, lookRange);
	}

}
