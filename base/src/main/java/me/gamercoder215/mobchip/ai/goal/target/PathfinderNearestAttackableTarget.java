package me.gamercoder215.mobchip.ai.goal.target;

import java.util.function.Predicate;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.gamercoder215.mobchip.ai.goal.Conditional;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;


/**
 * Represents a Pathfinder to target a specific type of Entity
 * @param <T> Type of Target to look for
 */
public class PathfinderNearestAttackableTarget<T extends LivingEntity> extends TargetPathfinder implements Targeting, Filtering<T>, Conditional<LivingEntity> {

    private Class<T> filter;
    private Predicate<LivingEntity> conditions;
    private boolean mustSee;
    private boolean reach;
    private int interval;

    /**
     * Constructs a PathfinderNearestAttackableTarget from a NMS NearestAttackableTargetGoal.
     * @param g Goal to use
     */
    @SuppressWarnings("unchecked")
    public PathfinderNearestAttackableTarget(@NotNull NearestAttackableTargetGoal<?> g) {
        super(TargetPathfinder.getEntity(g, "e"));

        this.mustSee = TargetPathfinder.getBoolean(TargetGoal.class.cast(g), "f");
        this.reach = TargetPathfinder.getBoolean(TargetGoal.class.cast(g), "d");
        this.interval = TargetPathfinder.getInt(NearestAttackableTargetGoal.class.cast(g), "b");
        this.conditions = m -> TargetPathfinder.getField(NearestAttackableTargetGoal.class.cast(g), "d", TargetingConditions.class).test(nmsEntity, ChipConversions.convertType(m));
        this.filter = (Class<T>) TargetPathfinder.getField(NearestAttackableTargetGoal.class.cast(g), "a", Class.class);
    }

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
        super(m);
        if (filter == null) throw new IllegalArgumentException("Filter cannot be null");

        this.filter = filter;
        this.interval = interval;
        this.mustSee = mustSee;
        this.reach = reach;
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

    @Override
    public boolean mustSee() {
        return this.mustSee;
    }

    @Override
    public void setSee(boolean see) {
        this.mustSee = see;
    }

    /**
     * Whether this entity must reach the target.
     * @return true if entity must reach target, else false
     */
    public boolean mustReach() {
        return this.reach;
    }

    /**
     * Sets whether this entity must reach the target.
     * @param reach true if entity must reach target, else false
     */
    public void setReach(boolean reach) {
        this.reach = reach;
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
    public NearestAttackableTargetGoal<?> getHandle() {
        return new NearestAttackableTargetGoal<>(nmsEntity, ChipConversions.toLivingNMSClass(filter), interval, mustSee, reach, m -> conditions.test(ChipConversions.convertType(m)));
    }

    @Override
    @NotNull
    public Predicate<LivingEntity> getCondition() {
        return this.conditions;
    }

    @Override
    public void setCondition(@NotNull Predicate<LivingEntity> condition) {
        this.conditions = condition;
    }
    
}
