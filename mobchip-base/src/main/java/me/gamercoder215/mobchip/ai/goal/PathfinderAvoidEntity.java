package me.gamercoder215.mobchip.ai.goal;

import java.lang.reflect.Field;

import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.ai.goal.target.Filtering;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

/**
 * Pathfinder to avoid a LivingEntity
 *
 * @param <T> Class of Entities to avoid
 */
public final class PathfinderAvoidEntity<T extends LivingEntity> extends Pathfinder implements Filtering<T>, SpeedModifier {
	
	private double speedModifier = DEFAULT_SPEED_MODIFIER;
	private float maxDistance;
	private double sprintModifier = DEFAULT_SPEED_MODIFIER;
	private Class<T> filter;

	/**
	 * Constructs a PathfinderAvoidEntity from a NMS Pathfinder Goal
	 * @param g Goal to use
	 */
	@SuppressWarnings("unchecked")
	public PathfinderAvoidEntity(AvoidEntityGoal<?> g) {
		super(Pathfinder.getEntity(g, "a"));
		
		this.speedModifier = Pathfinder.getDouble(g, "i");
		this.sprintModifier = Pathfinder.getDouble(g, "j");
		this.maxDistance = Pathfinder.getFloat(g, "c");

		try {
			Field d = AvoidEntityGoal.class.getDeclaredField("f");
			d.setAccessible(true);
			this.filter = (Class<T>) ChipConversions.toBukkitClass(LivingEntity.class, d.getType().asSubclass(net.minecraft.world.entity.Entity.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PathfinderAvoidEntity(@NotNull Creature entity, @NotNull Class<T> filter) {
		super(entity);
		this.filter = filter;
	}
	
	public PathfinderAvoidEntity(@NotNull Creature entity, @NotNull Class<T> filter, float dist, double walkMod, double sprintMod) {
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
		return new AvoidEntityGoal<net.minecraft.world.entity.LivingEntity>((PathfinderMob) nmsEntity, (Class<net.minecraft.world.entity.LivingEntity>) ChipConversions.toNMSClass(getFilter()), maxDistance, speedModifier, sprintModifier);
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
