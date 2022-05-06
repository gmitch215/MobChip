package me.gamercoder215.mobchip.ai.goal;

import java.lang.reflect.Field;
import java.util.function.Predicate;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * Represents a Pathfinder for a Mob to follow another Mob
 */
public final class PathfinderFollowMob extends Pathfinder implements SpeedModifier, Ranged {

    private float range;
    private double speedMod;
    private float stopDistance;

    private Predicate<Mob> followPredicate;

    @SuppressWarnings("unused")
    private PathfinderFollowMob(Goal g) {
        this((FollowMobGoal) g);
    }

    /**
     * Constructs a PathfinderFollowMob from a NMS FollowMobGoal.
     * @param g Goal to use
     */
    @SuppressWarnings("unchecked")
    public PathfinderFollowMob(@NotNull FollowMobGoal g) {
        super(Pathfinder.getEntity(g, "a"));

        this.speedMod = Pathfinder.getDouble(g, "d");
        this.stopDistance = Pathfinder.getFloat(g, "g");
        this.range = Pathfinder.getFloat(g, "i");

        try {
            Field a = FollowMobGoal.class.getDeclaredField("b");
            a.setAccessible(true);
            Predicate<net.minecraft.world.entity.Mob> predicate = (Predicate<net.minecraft.world.entity.Mob>) a.get(g);
            this.followPredicate = m -> predicate.test(ChipConversions.convertType(m));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a PathfinderFollowMob with a stop distance of 1.
     * @param m Mob to use
     * @param follow Preidcate to use when detecting a mob to follow
     * @throws IllegalArgumentException if follow predicate is null
     */
    public PathfinderFollowMob(@NotNull Mob m, @NotNull Predicate<Mob> follow) throws IllegalArgumentException{
        this(m, 1, follow);
    }

    /**
     * Constructs a PathfinderFollowMob with {@link Ranged#DEFAULT_LOOK_RANGE}.
     * @param m Mob to use
     * @param stopDistance Distance from the mob to stop following
     * @param follow Predicate to use when detecting a mob to follow
     * @throws IllegalArgumentException if follow predicate is null
     */
    public PathfinderFollowMob(@NotNull Mob m, float stopDistance, @NotNull Predicate<Mob> follow) throws IllegalArgumentException {
        this(m, stopDistance, DEFAULT_LOOK_RANGE, follow);
    }

    /**
     * Constructs a PathfinderFollowMob with {@link SpeedModifier#DEFAULT_SPEED_MODIFIER}.
     * @param m Mob to use
     * @param stopDistance Distance from the mob to stop following
     * @param lookRange Range of looking for a mob to follow
     * @param follow Predicate to use when detecting a mob to follow
     * @throws IllegalArgumentException if follow predicate is null
     */
    public PathfinderFollowMob(@NotNull Mob m, float stopDistance, float lookRange, Predicate<Mob> follow) throws IllegalArgumentException{
        this(m, DEFAULT_SPEED_MODIFIER, stopDistance, lookRange, follow);
    }

    /**
     * Constructs a PathfinderFollowMob.
     * @param m Mob to use
     * @param speedMod Speed Modifier to use when following
     * @param stopDistance Distance from the mob to stop following
     * @param lookRange Range of looking for a mob to follow
     * @param followPredicate Predicate to use when detecting a mob to follow
     * @throws IllegalArgumentException if follow predicate is null
     */
    public PathfinderFollowMob(@NotNull Mob m, double speedMod, float stopDistance, float lookRange, Predicate<Mob> followPredicate) throws IllegalArgumentException {
        super(m);
        if (followPredicate == null) throw new IllegalArgumentException("Follow cannot be null");

        this.range = lookRange;
        this.speedMod = speedMod;
        this.stopDistance = stopDistance;
        this.followPredicate = followPredicate;
    }

    /**
     * Gets the Distance needed to stop following.
     * @return Distance to stop following.
     */
    public float getStopDistance() {
        return this.stopDistance;
    }

    /**
     * Sets the Distance needed to stop following.
     * @param stop distance to stop following an entity
     */
    public void setStopDistance(float stop) {
        this.stopDistance = stop;
    }
    
    @Override
    public float getRange() {
        return this.range;
    }

    @Override
    public void setRange(float range) {
        this.range = range;
    }

    @Override
    public double getSpeedModifier() {
        return this.speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) {
        this.speedMod = mod;
    }

    /**
     * Fetches the follow predicate used when looking for a Mob to follow.
     * @return Mob Predicate
     */
    public @NotNull Predicate<Mob> getFollowFilter() {
        return this.followPredicate;
    }

    /**
     * Sets the follow predicate used when looking for a Mob to follow.
     * @param follow Follow predicate to use
     * @throws IllegalArgumentException if follow predicate is null
     */
    public void setFolowFilter(@NotNull Predicate<Mob> follow) throws IllegalArgumentException {
        if (follow == null) throw new IllegalArgumentException("Follow cannot be null");

        this.followPredicate = follow;
    }

    @Override
    public FollowMobGoal getHandle() {
        FollowMobGoal g = new FollowMobGoal(nmsEntity, speedMod, stopDistance, range);

        Predicate<net.minecraft.world.entity.Mob> p = m -> followPredicate.test(ChipConversions.convertType(m));
        Pathfinder.setFinal(g, "b", p);
        
        return g;
    }


}
