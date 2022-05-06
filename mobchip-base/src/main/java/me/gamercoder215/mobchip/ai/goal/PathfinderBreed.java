package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Animals;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;

/**
 * Represents a Pathfinder that causes Animals to breed
 */
public final class PathfinderBreed extends Pathfinder implements SpeedModifier {
	
	private double speedMod;

	public PathfinderBreed(@NotNull BreedGoal g) {
		super(Pathfinder.getEntity(g, "a"));

		this.speedMod = Pathfinder.getDouble(g, "g");
	}

	public PathfinderBreed(@NotNull Animals m) {
		super(m);
	}
	
	public PathfinderBreed(@NotNull Animals m, double speedMod) {
		super(m);
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
	public Goal getHandle() {
		return new BreedGoal((Animal) nmsEntity, speedMod);
	}

}
