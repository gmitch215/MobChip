package me.gamercoder215.mobchip.entity.ai.goal;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftCreature;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

/**
 * Avoid Entity Goal
 */
public class GoalAvoidEntity extends ChipGoal implements MovementGoal, RangedGoal {
	
	private AvoidEntityGoal<? extends PathfinderMob> goal;
	
	private final float distance;
	private final double speedModifier;
	private final double sprintModifier;
	
	public GoalAvoidEntity(Creature en, EntityType toAvoid, float distance, double speedModifier) {
		this(en, toAvoid, distance, speedModifier, speedModifier * 1.5);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GoalAvoidEntity(Creature en, EntityType toAvoid, float distance, double speedModifier, double sprintModifier) {
		super(en);
		
		this.distance = distance;
		this.speedModifier = speedModifier;
		this.sprintModifier = sprintModifier;
		
		try {
			this.goal = new AvoidEntityGoal(((CraftCreature) en).getHandle(), ((net.minecraft.world.entity.EntityType<?>) net.minecraft.world.entity.EntityType.class.getDeclaredField(toAvoid.name()).get(null)).getBaseClass(), distance, speedModifier, sprintModifier);
		} catch (Exception e) {
			this.goal = null;
			e.printStackTrace();
		}
	}

	@Override
	public AvoidEntityGoal<?> getHandle() {
		return this.goal;
	}

	@Override
	public double getSpeedModifier() {
		return this.speedModifier;
	}
	
	/**
	 * Get the Sprint Modifier of this 
	 * @return
	 */
	public double getSprintModifier() {
		return this.sprintModifier;
	}

	@Override
	public float getRange() {
		return this.distance;
	}

}
