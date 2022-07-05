package me.gamercoder215.mobchip.ai.goal.target;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Raider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * Represents a Pathfinder for a specific Raider's attack
 * @param <T> Type of Entity to Attack
 */
public final class PathfinderNearestAttackableTargetRaider<T extends LivingEntity> extends PathfinderNearestAttackableTarget<T> {

    /**
     * Constructs a PathfinderNearestAttackableTargetRaider.
     * @param m Raider to use
     * @param filter Filter of class
     * @see PathfinderNearestAttackableTarget#PathfinderNearestAttackableTarget(org.bukkit.entity.Mob, Class)
     * @throws IllegalArgumentException if filter is null
     */
    public PathfinderNearestAttackableTargetRaider(@NotNull Raider m, @NotNull Class<T> filter) throws IllegalArgumentException {
        super(m, filter);
    }

    /**
     * Constructs a PathfinderNearestAttackableTargetRaider.
     * @param m Raider to use
     * @param filter Filter of class
     * @param interval Interval of attack, in ticks
     * @see PathfinderNearestAttackableTarget#PathfinderNearestAttackableTarget(org.bukkit.entity.Mob, Class, int)
     * @throws IllegalArgumentException if filter is null
     */
    public PathfinderNearestAttackableTargetRaider(@NotNull Raider m, @NotNull Class<T> filter, int interval) throws IllegalArgumentException {
        super(m, filter, interval, true, true);
    }

    /**
     * Constructs a PathfinderNearestAttackableTargetRaider.
     * @param m Raider to use
     * @param filter Filter of class
     * @param interval Interval of attack, in ticks
     * @param mustSee Whether the Raider must see the target
     * @param reach Whether the Raider must reach the target
     * @see PathfinderNearestAttackableTarget#PathfinderNearestAttackableTarget(org.bukkit.entity.Mob, Class, int, boolean, boolean)
     * @throws IllegalArgumentException if filter is null
     */
    public PathfinderNearestAttackableTargetRaider(@NotNull Raider m, @NotNull Class<T> filter, int interval, boolean mustSee, boolean reach) throws IllegalArgumentException {
        super(m, filter, interval, mustSee, reach);
    }

    /**
     * Constructs a PathfinderNearestAttackableTargetRaider.
     * @param m Raider to use
     * @param filter Filter of class
     * @param interval Interval of attack, in ticks
     * @param mustSee Whether the Raider must see the target
     * @param reach Whether the Raider must reach the target
     * @param conditions Conditions needed to attack
     * @see PathfinderNearestAttackableTarget#PathfinderNearestAttackableTarget(org.bukkit.entity.Mob, Class, int, boolean, boolean, Predicate)
     * @throws IllegalArgumentException if filter is null
     */
    public PathfinderNearestAttackableTargetRaider(@NotNull Raider m, @NotNull Class<T> filter, int interval, boolean mustSee, boolean reach, @Nullable Predicate<LivingEntity> conditions) throws IllegalArgumentException {
        super(m, filter, interval, mustSee, reach, conditions);
    }

}
