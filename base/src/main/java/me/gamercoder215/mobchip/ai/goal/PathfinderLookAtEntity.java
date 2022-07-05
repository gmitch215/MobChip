package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.Probable;
import me.gamercoder215.mobchip.ai.goal.target.Filtering;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for an Entity to look at another Entity
 * @param <T> Type of LivingEntity to look at
 */
public final class PathfinderLookAtEntity<T extends LivingEntity> extends Pathfinder implements Filtering<T>, Probable, Ranged {

    /**
     * Default Probability to look at an Entity (0.02F)
     */
    public static final float DEFAULT_PROBABILITY = 0.02F;

    private float lookRange;
    private float probability;
    private Class<T> filterClass;

    private boolean horizontal;

    /**
     * Constructs a PathfinderLookAtEntity with the default {@link Ranged#DEFAULT_LOOK_RANGE}.
     * @param m Mob to use
     * @param filter Filter class to look at
     */
    public PathfinderLookAtEntity(@NotNull Mob m, @NotNull Class<T> filter) {
        this(m, filter, DEFAULT_LOOK_RANGE);
    }

    /**
     * Constructs a PathfinderLookAtEntity with the default probability.
     * @param m Mob to use
     * @param filter Filter class to look at
     * @param lookRange Range of blocks to find selected class filter
     */
    public PathfinderLookAtEntity(@NotNull Mob m, @NotNull Class<T> filter, float lookRange) {
        this(m, filter, lookRange, DEFAULT_PROBABILITY);
    }

    /**
     * Constructs a PathfinderLookAtEntity with horizontal set to false.
     * @param m Mob to use
     * @param filter Filter class to look at
     * @param lookRange Range of blocks to find selected class filter
     * @param probability Probability (0.0 - 1.0) to look at something. <strong>Called every tick, recommended to be a low number. See {@link #DEFAULT_PROBABILITY}.</strong>
     */
    public PathfinderLookAtEntity(@NotNull Mob m, @NotNull Class<T> filter, float lookRange, float probability) {
        this(m, filter, lookRange, probability, false);
    }

    /**
     * Constructs a PathfinderLookAtEntity.
     * @param m Mob to use
     * @param filter Filter class to look at
     * @param lookRange Range of blocks to find selected class filter
     * @param probability Probability (0.0 - 1.0) to look at something. <strong>Called every tick, recommended to be a low number. See {@link #DEFAULT_PROBABILITY}.</strong>
     * @param horizontal Whether rotation should only be horizontal
     */
    public PathfinderLookAtEntity(@NotNull Mob m, @NotNull Class<T> filter, float lookRange, float probability, boolean horizontal) {
        super(m); 

        this.filterClass = filter;
        this.lookRange = lookRange;
        this.probability = probability;
        this.horizontal = horizontal;
    }

    @Override
    public float getRange() {
        return this.lookRange;
    }

    @Override
    public void setRange(float range) {
        this.lookRange = range;
    }

    @Override
    public float getProbability() {
        return this.probability;
    }

    @Override
    public void setProbability(float prob) {
        this.probability = prob;
    }

    @Override
    public void setFilter(@NotNull Class<T> clazz) {
        this.filterClass = clazz;
    }

    @Override
    public Class<T> getFilter() {
        return this.filterClass;
    }

    /**
     * Whether the Looking Rotation is only horizontal.
     * @return true if rotation is only horizontal, else false
     */
    public boolean isHorizontal() {
        return horizontal;
    }

    /**
     * Sets whether the Looking Rotation is only horizontal.
     * @param horizontal true if rotation is only horizontal, else false
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.LOOKING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalLookAtPlayer";
    }
}
