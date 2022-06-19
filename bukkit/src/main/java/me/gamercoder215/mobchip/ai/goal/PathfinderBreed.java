package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.entity.Animals;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder that causes Animals to breed
 */
public final class PathfinderBreed extends Pathfinder implements SpeedModifier {
	
	private double speedMod;

	/**
	 * Constructs a PathfinderBreed from a NMS BreedGoal.
	 * @param g Goal to use
	 */
	public PathfinderBreed(@NotNull BreedGoal g) {
		super(getEntity(g, "a"));

		this.speedMod = getDouble(g, "g");
	}

	/**
	 * Constructs a PathfinderBreed with no speed modifier.
	 * @param m Animal to use
	 */
	public PathfinderBreed(@NotNull Animals m) {
		this(m, 1);
	}

	/**
	 * Constructs a PathfinderBreed.
	 * @param m Animal to use
	 * @param speedMod Speed Modifier while breeding
	 */
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
	public BreedGoal getHandle() {
		return new BreedGoal((Animal) nmsEntity, speedMod);
	}

}
