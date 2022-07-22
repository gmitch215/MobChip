package me.gamercoder215.mobchip.ai.goal.target;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * Represents a Pathfinder when a Wild Animal {@link org.bukkit.entity.Tameable Mob but not tamed} targets another Entity.
 * @param <T> Type of Target to look for
 */
public final class PathfinderWildTarget<T extends LivingEntity> extends PathfinderNearestAttackableTarget<T> {

    /**
     * Constructs a PathfinderWildTarget with see set to true.
     * @param mob Tameable Mob to use
     * @param filter Class of entity to find
     * @throws IllegalArgumentException if filter is null
     */
    public PathfinderWildTarget(@NotNull Tameable mob, @NotNull Class<T> filter) throws IllegalArgumentException {
        this(mob, filter, true);
    }

    /**
     * Constructs a PathfinderWildTarget with no conditions.
     * @param mob Tameable Mob to use
     * @param filter Class of entity to find
     * @param see Whether entity must see target
     * @throws IllegalArgumentException if filter is null
     */
    public PathfinderWildTarget(@NotNull Tameable mob, @NotNull Class<T> filter, boolean see) throws IllegalArgumentException {
        this(mob, filter, see, null);
    }

    /**
     * Constructs a PathfinderWildTarget.
     * @param mob Tameable Mob to use
     * @param filter Class of entity to find
     * @param see Whether entity must see target
     * @param conditions Conditions that the target must match
     * @throws IllegalArgumentException if filter is null
     */
    public PathfinderWildTarget(@NotNull Tameable mob, @NotNull Class<T> filter, boolean see, @Nullable Predicate<LivingEntity> conditions) throws IllegalArgumentException {
        super((Mob & Tameable) mob, filter, 10, see, false, conditions);
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalRandomTargetNonTamed";
    }

}
