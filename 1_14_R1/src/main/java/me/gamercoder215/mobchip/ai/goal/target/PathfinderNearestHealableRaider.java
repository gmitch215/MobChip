package me.gamercoder215.mobchip.ai.goal.target;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Raider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * Represents a Pathfinder for Raiders to heal another Raider
 */
public final class PathfinderNearestHealableRaider<T extends LivingEntity> extends PathfinderNearestAttackableTarget<T> {

    /**
     * Constructs a PathfinderNearestHealableRaider with see set to true.
     * @param m Raider to use
     * @param filter Class of Entity to find
     * @throws IllegalArgumentException if filter is null
     */
    public PathfinderNearestHealableRaider(@NotNull Raider m, @NotNull Class<T> filter) throws IllegalArgumentException {
        this(m, filter, true);
    }

    /**
     * Constructs a PathfinderNearestHealableRaider with no conditions.
     * @param m Raider to use
     * @param filter Class of Entity to find
     * @param mustSee Whether the Raider must see the target
     * @throws IllegalArgumentException if filter is null
     */
    public PathfinderNearestHealableRaider(@NotNull Raider m, @NotNull Class<T> filter, boolean mustSee) throws IllegalArgumentException {
        this(m, filter, mustSee, null);
    }

    /**
     * Constructs a PathfinderNearestHealableRaider.
     * @param m Raider to use
     * @param filter Class of Entity to find
     * @param mustSee Whether the Raider must see the target
     * @param conditions Conditions needed to heal
     * @throws IllegalArgumentException if filter is null
     */
    public PathfinderNearestHealableRaider(@NotNull Raider m, @NotNull Class<T> filter, boolean mustSee, @Nullable Predicate<LivingEntity> conditions) throws IllegalArgumentException {
        super(m, filter, 500, mustSee, false, conditions);
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalNearestHealableRaider";
    }
}
