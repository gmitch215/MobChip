package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.util.MobChipUtil;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import org.bukkit.Difficulty;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	private Predicate<net.minecraft.world.Difficulty> validDiffs;
	
	/**
	 * Convert a NMS BreakDoorGoal to a PathfinderBreakDoor.
	 * @param g NMS Goal to use
	 */
	@SuppressWarnings("unchecked")
	public PathfinderBreakDoor(@NotNull BreakDoorGoal g) {
		super(Pathfinder.getEntity(g, "d"));
		try {
			Field a = BreakDoorGoal.class.getDeclaredField("a");
			a.setAccessible(true);
			this.breakTime = a.getInt(g);
			
			Field b = BreakDoorGoal.class.getDeclaredField("h");
			a.setAccessible(true);
			this.validDiffs = (Predicate<net.minecraft.world.Difficulty>) b.get(g);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
		super(m);
		if (breakTime <= 0) throw new IllegalArgumentException("Break Time must be greater than 0");
		this.breakTime = breakTime;
		
		List<net.minecraft.world.Difficulty> diffs = new ArrayList<>();
		for (Difficulty d : validDifficulties) diffs.add(MobChipUtil.convert(d));
		
		this.validDiffs = d -> Arrays.asList(validDifficulties).contains(MobChipUtil.convert(d));
	}	

	/**
	 * Gets the amount of ticks it takes to break a door.
	 * @return Amount of ticks it takes
	 */
	public int getBreakTime() {
		return this.breakTime;
	}
	
	@Override
	public @NotNull Predicate<Difficulty> getCondition() {
		return d -> validDiffs.test(MobChipUtil.convert(d));
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
	public BreakDoorGoal getHandle() {
		return new BreakDoorGoal(nmsEntity, breakTime, validDiffs);
	}

	@Override
	public void setCondition(@NotNull Predicate<Difficulty> condition) {
		this.validDiffs = d -> condition.test(MobChipUtil.convert(d));
	}

}
