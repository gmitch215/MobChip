package me.gamercoder215.mobchip.ai.goal.target;

import me.gamercoder215.mobchip.ai.goal.Conditional;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;


/**
 * Represents a Pathfinder to target a specific type of Entity
 * @param <T> Type of Target to look for
 */
public class PathfinderNearestAttackableTarget<T extends LivingEntity> extends TargetPathfinder implements Filtering<T>, Conditional<LivingEntity> {

    private Class<T> filter;
    private Predicate<? super LivingEntity> conditions;
    private int interval;

    /**
     * Constructs a PathfinderNearestAttackableTarget with a default interval of 10.
     * @param m Mob to use
     * @param filter Class of entity to find
     * @throws IllegalArgumentException if class is null
     */
    public PathfinderNearestAttackableTarget(@NotNull Mob m, @NotNull Class<T> filter) throws IllegalArgumentException {
        this(m, filter, 10);
    }

    /**
     * Constructs a PathfinderNearestAttackableTarget with see and reach set to true.
     * @param m Mob to use
     * @param filter Class of entity to find
     * @param interval Interval of attack, in ticks
     * @throws IllegalArgumentException if class is null
     */
    public PathfinderNearestAttackableTarget(@NotNull Mob m, @NotNull Class<T> filter, int interval) throws IllegalArgumentException {
        this(m, filter, interval, true, true);
    }

    /**
     * Constructs a PathfinderNearestAttackableTarget with no conditions.
     * @param m Mob to use
     * @param filter Class of entity to find
     * @param interval Interval of attack, in ticks 
     * @param mustSee Whether entity must see target
     * @param reach Whether entity must reach target
     * @throws IllegalArgumentException if class is null
     */
    public PathfinderNearestAttackableTarget(@NotNull Mob m, @NotNull Class<T> filter, int interval, boolean mustSee, boolean reach) throws IllegalArgumentException {
        this(m, filter, interval, mustSee, reach, null);
    }

    /**
     * Constructs a PathfinderNearestAttackableTarget.
     * @param m Mob to use
     * @param filter Class of entity to find
     * @param interval Interval of attack, in ticks
     * @param mustSee Whether entity must see target
     * @param reach Whether entity must reach target
     * @param conditions Conditions needed to attack
     * @throws IllegalArgumentException if filter is null
     */
    public PathfinderNearestAttackableTarget(@NotNull Mob m, @NotNull Class<T> filter, int interval, boolean mustSee, boolean reach, @Nullable Predicate<LivingEntity> conditions) throws IllegalArgumentException {
        super(m, mustSee, reach);
        if (filter == null) throw new IllegalArgumentException("Filter cannot be null");

        this.filter = filter;
        this.interval = interval;
        this.conditions = conditions;
    }

    

    @Override
    public void setFilter(@NotNull Class<T> clazz) throws IllegalArgumentException {
        if (clazz == null) throw new IllegalArgumentException("Clazz cannot be null");
        this.filter = clazz;
    }

    @Override
    public Class<T> getFilter() {
        return this.filter;
    }

    /**
     * Gets the current attack interval, in ticks.
     * @return Attack Interval
     */
    public int getInterval() {
        return this.interval;
    }

    /**
     * Sets the current attack interval, in ticks.
     * @param interval Attack interval to set
     * @throws IllegalArgumentException if less than 0
     */
    public void setInterval(int interval) throws IllegalArgumentException {
        if (interval < 1) throw new IllegalArgumentException("Must be greater than 0");
        this.interval = interval;
    }

    @Override
    @NotNull
    public Predicate<? super LivingEntity> getCondition() {
        return this.conditions;
    }

    @Override
    public void setCondition(@NotNull Predicate<LivingEntity> condition) {
        this.conditions = condition;
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.TARGETING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalNearestAttackableTarget";
    }
}
