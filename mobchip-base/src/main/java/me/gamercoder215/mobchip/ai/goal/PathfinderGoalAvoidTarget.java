package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftCreature;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.goal.target.Filtering;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

/**
 * Pathfinder to avoid a LivingEntity
 *
 * @param <T> Class of Entities to avoid
 */
public class PathfinderGoalAvoidTarget<T extends LivingEntity> extends Pathfinder implements Filtering<T>, SpeedModifier {
	
	private double speedModifier = DEFAULT_SPEED_MODIFIER;
	private float maxDistance;
	private double sprintModifier = DEFAULT_SPEED_MODIFIER;
	private Class<T> filter;
	
	private PathfinderMob nmsEntity;
	
	public PathfinderGoalAvoidTarget(@NotNull Creature entity, @NotNull Class<T> filter) {
		super(entity);
		this.filter = filter;
		this.nmsEntity = ((CraftCreature) entity).getHandle();
	}
	
	public PathfinderGoalAvoidTarget(@NotNull Creature entity, @NotNull Class<T> filter, float dist, double walkMod, double sprintMod) {
		this(entity, filter);
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
	 * @param dist New distnace to set
	 */
	public void setMaxDistance(float dist) {
		this.maxDistance = dist;
	}

	@Override
	public double getSpeedModifier() {
		return this.speedModifier;
	}

	@Override
	public void setSpeedModifier(double mod) {
		this.speedModifier = mod;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AvoidEntityGoal<?> getHandle() {
		return new AvoidEntityGoal<net.minecraft.world.entity.LivingEntity>(nmsEntity, (Class<net.minecraft.world.entity.LivingEntity>) ChipConversions.toNMSClass(getFilter()), maxDistance, speedModifier, sprintModifier);
	}

	@Override
	public void setFilter(@NotNull Class<T> clazz) {
		this.filter = clazz;
	}

	@Override
	public @NotNull Class<T> getFilter() {
		return this.filter;
	}

}
