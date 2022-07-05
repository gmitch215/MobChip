package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.Difficulty;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Represents a Pathfinder that has a Mob break a door
 */
public final class PathfinderBreakDoor extends Pathfinder implements Conditional<Difficulty> {
	
	/**
	 * Default amount of ticks it takes to break a door
	 */
	public static final int DEFAULT_DOOR_BREAK_TIME = 240;
	
	private int breakTime;
	private Predicate<? super Difficulty> validDiffs;
	
	/**
	 * Constructs a PathfinderBreakDoor with the default  break time.
	 * @param m Mob to use
     * @param validDifficulties Difficulties that this Pathfinder will work on
	 */
	public PathfinderBreakDoor(@NotNull Mob m, Difficulty... validDifficulties) {
		this(m, DEFAULT_DOOR_BREAK_TIME, validDifficulties);
	}
	
	/**
	 * Constructs a PathfinderBreakDoor.
	 * @param m Mob to use
	 * @param breakTime How many ticks it takes to break a door
	 * @param validDifficulties Difficulties that this Pathfinder will work on
     * @throws IllegalArgumentException if time is less than or equal to 0
	 */
	public PathfinderBreakDoor(@NotNull Mob m, int breakTime, Difficulty... validDifficulties) throws IllegalArgumentException {
		this(m, breakTime, d -> Arrays.asList(validDifficulties).contains(d));
	}

	/**
	 * Constructs a PathfinderBreakDoor with a predicate.
	 * @param m Mob to use
	 * @param breakTime How many ticks it takes to break a door
	 * @param validDiffs Predicate that determines if the Pathfinder should work on a given difficulty
	 * @throws IllegalArgumentException if time is less than or equal to 0
	 */
	public PathfinderBreakDoor(@NotNull Mob m, int breakTime, Predicate<? super Difficulty> validDiffs) throws IllegalArgumentException {
		super(m);
		if (breakTime <= 0) throw new IllegalArgumentException("Break Time must be greater than 0");

		this.breakTime = breakTime;
		this.validDiffs = validDiffs;
	}

	/**
	 * Gets the amount of ticks it takes to break a door.
	 * @return Amount of ticks it takes
	 */
	public int getBreakTime() {
		return this.breakTime;
	}
	
	@Override
	public @NotNull Predicate<? super Difficulty> getCondition() {
		return this.validDiffs;
	}
	
	/**
	 * Sets the amount of ticks it takes to break a door.
	 * @param time Amount of ticks to set
	 * @throws IllegalArgumentException if time is less than or equal to 0
	 */
	public void setBreakTime(int time) throws IllegalArgumentException {
		if (time <= 0) throw new IllegalArgumentException("Break Time must be greater than 0");
		this.breakTime = time;
	}

	@Override
	public void setCondition(@NotNull Predicate<Difficulty> condition) {
		this.validDiffs = condition;
	}

	@Override
	public @NotNull PathfinderFlag[] getFlags() {
		return new PathfinderFlag[0];
	}

	@Override
	public String getInternalName() {
		return "PathfinderGoalBreakDoor";
	}
}
