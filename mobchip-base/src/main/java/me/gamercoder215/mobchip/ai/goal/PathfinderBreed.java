package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Animals;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;

/**
 * Represents a Pathfinder that causes Animals to breed
 * @param <T> Type of Entity to breed with
 */
public final class PathfinderBreed<T extends Animals> extends Pathfinder implements SpeedModifier {
	
	private double speedMod = 0.5;
	
	public PathfinderBreed(@NotNull T animal) {
		super(animal);
	}
	
	public PathfinderBreed(@NotNull T animal, double speedMod) {
		super(animal);
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
