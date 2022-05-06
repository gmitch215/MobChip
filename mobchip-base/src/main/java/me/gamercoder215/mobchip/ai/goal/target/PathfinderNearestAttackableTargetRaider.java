package me.gamercoder215.mobchip.ai.goal.target;

import java.util.function.Predicate;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Raider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableWitchTargetGoal;

/**
 * Represents a Pathfinder for a specific Raider's attack
 */
public final class PathfinderNearestAttackableTargetRaider<T extends LivingEntity> extends PathfinderNearestAttackableTarget<T> {

    /**
     * Constructs a PathfinderNearestAttackableTargetRaider from a NMS NearestAttackableWitchTargetGoal.
     * @param g Goal to use
     */
    public PathfinderNearestAttackableTargetRaider(@NotNull NearestAttackableWitchTargetGoal<?> g) {
        super(g);
    }

    /**
     * @see PathfinderNearestAttackableTarget#PathfinderNearestAttackableTarget(org.bukkit.entity.Mob, Class)
     */
    public PathfinderNearestAttackableTargetRaider(@NotNull Raider m, @NotNull Class<T> filter) {
        super(m, filter);
    }

    /**
     * @see PathfinderNearestAttackableTarget#PathfinderNearestAttackableTarget(org.bukkit.entity.Mob, Class, int)
     */
    public PathfinderNearestAttackableTargetRaider(@NotNull Raider m, @NotNull Class<T> filter, int interval) throws IllegalArgumentException {
        super(m, filter, interval, true, true);
    }

    /**
     * @see PathfinderNearestAttackableTarget#PathfinderNearestAttackableTarget(org.bukkit.entity.Mob, Class, int, boolean, boolean)
     */
    public PathfinderNearestAttackableTargetRaider(@NotNull Raider m, @NotNull Class<T> filter, int interval, boolean mustSee, boolean reach) throws IllegalArgumentException {
        super(m, filter, interval, mustSee, reach);
    }

    /**
     * @see PathfinderNearestAttackableTarget#PathfinderNearestAttackableTarget(org.bukkit.entity.Mob, Class, int, boolean, boolean, Predicate)
     */
    public PathfinderNearestAttackableTargetRaider(@NotNull Raider m, @NotNull Class<T> filter, int interval, boolean mustSee, boolean reach, @Nullable Predicate<LivingEntity> conditions) throws IllegalArgumentException {
        super(m, filter, interval, mustSee, reach, conditions);
    }

    public NearestAttackableWitchTargetGoal<?> getHandle() {
        return new NearestAttackableWitchTargetGoal<>((net.minecraft.world.entity.raid.Raider) nmsEntity, ChipConversions.toLivingNMSClass(this.getFilter()), this.getInterval(), this.mustSee(), this.mustReach(), m -> this.getCondition().test(ChipConversions.convertType(m)));
    }
    
}
