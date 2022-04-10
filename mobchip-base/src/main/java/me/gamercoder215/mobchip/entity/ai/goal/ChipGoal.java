package me.gamercoder215.mobchip.entity.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.ai.goal.Goal;

public abstract class ChipGoal {
	
	protected final Creature entity;
	
	protected ChipGoal(@NotNull Creature en) {
		this.entity = en;
	}
	
	/**
	 * Get the NMS Version of this ChipGoal.
	 * @return NMS PathfinderGoal
	 */
	@NotNull
	public abstract Goal getHandle();
	
	@NotNull
	public final Creature getEntity() {
		return this.entity;
	}
	
}
