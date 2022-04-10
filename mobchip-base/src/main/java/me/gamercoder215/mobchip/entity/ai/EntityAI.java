package me.gamercoder215.mobchip.entity.ai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;

/**
 * A class representing all Entity AI
 */
public class EntityAI {
	
	private final net.minecraft.world.entity.PathfinderMob entity;
	
	private EntityAI(Creature en) {
		this.entity = ((CraftCreature) en).getHandle();
	}
	
	/**
	 * Get the EntityAI of this entity.
	 * @param en Creature to use
	 * @return EntityAI
	 */
	public static EntityAI from(Creature en) {
		return new EntityAI(en);
	}
	
	
	
	@NotNull
	public static List<? extends Goal> getNMSGoals(Creature en) {
		List<Goal> goals = new ArrayList<>();
		
		PathfinderMob mob = ((CraftCreature) en).getHandle();
		
		for (WrappedGoal w : mob.goalSelector.getAvailableGoals()) goals.add(w.getGoal());
		for (WrappedGoal w : mob.targetSelector.getAvailableGoals()) goals.add(w.getGoal());
		
		return goals;
	}
	
	public static Class<? extends net.minecraft.world.entity.Entity> getNMSClass(Entity bukkit) {
		return ((CraftEntity) bukkit).getHandle().getClass();
	}

}
