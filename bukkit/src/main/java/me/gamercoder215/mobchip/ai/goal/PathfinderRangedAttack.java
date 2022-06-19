package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Stray;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a ranged entity to shoot ranged projectiles
 */
public final class PathfinderRangedAttack extends Pathfinder implements SpeedModifier, Ranged {

    private double speedMod;
    private float range;
    private int aMin;
    private int aMax;

    /**
     * Constructs a PathfinderRangedAttack from a NMS RangedAttackGoal.
     * @param g Goal to use
     */
    public PathfinderRangedAttack(@NotNull RangedAttackGoal g) {
        super(Pathfinder.getEntity(g, "a"));

        this.speedMod = Pathfinder.getDouble(g, "e");
        this.range = Pathfinder.getFloat(g, "i");
        this.aMin = Pathfinder.getInt(g, "g");
        this.aMax = Pathfinder.getInt(g, "h");
    }

    /**
     * Constructs a PathfinderRangedAttack with the default attack range.
     * @param m Skeleton to use
     * @param speedMod Speed Modifier while attacking
     */
    public PathfinderRangedAttack(@NotNull Skeleton m, double speedMod) {
        this(m, speedMod, DEFAULT_ATTACK_RANGE);
    }

    /**
     * Constructs a PathfinderRangedAttack with the default attack range.
     * @param m Stray to use
     * @param speedMod Speed Modifier while attacking
     */
    public PathfinderRangedAttack(@NotNull Stray m, double speedMod) {
        this(m, speedMod, DEFAULT_ATTACK_RANGE);
    }

    /**
     * Constructs a PathfinderRangedAttack with the default attack range.
     * @param m SnowGolem to use
     * @param speedMod Speed Modifier while attacking
     */
    public PathfinderRangedAttack(@NotNull Snowman m, double speedMod) {
        this(m, speedMod, DEFAULT_ATTACK_RANGE);
    }

    /**
     * Constructs a PathfinderRangedAttack with the default attack range.
     * @param m Pillager to use
     * @param speedMod Speed Modifier while attacking
     */
    public PathfinderRangedAttack(@NotNull Pillager m, double speedMod) {
        this(m, speedMod, DEFAULT_ATTACK_RANGE);
    }

    /**
     * Constructs a PathfinderRangedAttack with the default interval.
     * @param m Skeleton to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     */
    public PathfinderRangedAttack(@NotNull Skeleton m, double speedMod, float range) {
        this(m, speedMod, range, DEFAULT_ATTACK_INTERVAL);
    }

    /**
     * Constructs a PathfinderRangedAttack with the default interval.
     * @param m Stray to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     */
    public PathfinderRangedAttack(@NotNull Stray m, double speedMod, float range) {
        this(m, speedMod, range, DEFAULT_ATTACK_INTERVAL);
    }

    /**
     * Constructs a PathfinderRangedAttack with the default interval.
     * @param m SnowGolem to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     */
    public PathfinderRangedAttack(@NotNull Snowman m, double speedMod, float range) {
        this(m, speedMod, range, DEFAULT_ATTACK_INTERVAL);
    }

    /**
     * Constructs a PathfinderRangedAttack with the default interval.
     * @param m Pillager to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     */
    public PathfinderRangedAttack(@NotNull Pillager m, double speedMod, float range) {
        this(m, speedMod, range, DEFAULT_ATTACK_INTERVAL);
    }
    
    /**
     * Constructs a PathfinderRangedAttack with the max and min intervals the same.
     * @param m Skeleton to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param interval Interval of attack, in ticks
     */
    public PathfinderRangedAttack(@NotNull Skeleton m, double speedMod, float range, int interval) {
        this(m, speedMod, range, interval, interval);
    }

    /**
     * Constructs a PathfinderRangedAttack with the max and min intervals the same.
     * @param m Stray to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param interval Interval of attack, in ticks
     */
    public PathfinderRangedAttack(@NotNull Stray m, double speedMod, float range, int interval) {
        this(m, speedMod, range, interval, interval);
    }

    /**
     * Constructs a PathfinderRangedAttack with the max and min intervals the same.
     * @param m Pillager to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param interval Interval of attack, in ticks
     */
    public PathfinderRangedAttack(@NotNull Pillager m, double speedMod, float range, int interval) {
        this(m, speedMod, range, interval, interval);
    }

    /**
     * Constructs a PathfinderRangedAttack with the max and min intervals the same.
     * @param m SnowGolem to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param interval Interval of attack, in ticks
     */
    public PathfinderRangedAttack(@NotNull Snowman m, double speedMod, float range, int interval) {
        this(m, speedMod, range, interval, interval);
    }

    /**
     * Constructs a PathfinderRangedAttack.
     * @param m Skeleton to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param attackMin Minimum Interval of attack, in ticks
     * @param attackMax Maximum Interval of attack, in ticks
     */
    public PathfinderRangedAttack(@NotNull Skeleton m, double speedMod, float range, int attackMin, int attackMax) {
        super(m);

        this.speedMod = speedMod;
        this.range = range;
        this.aMax = attackMax;
        this.aMin = attackMin;
    }

    /**
     * Constructs a PathfinderRangedAttack.
     * @param m Stray to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param attackMin Minimum Interval of attack, in ticks
     * @param attackMax Maximum Interval of attack, in ticks
     */
    public PathfinderRangedAttack(@NotNull Stray m, double speedMod, float range, int attackMin, int attackMax) {
        super(m);

        this.speedMod = speedMod;
        this.range = range;
        this.aMax = attackMax;
        this.aMin = attackMin;
    }

    /**
     * Constructs a PathfinderRangedAttack.
     * @param m SnowGolem to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param attackMin Minimum Interval of attack, in ticks
     * @param attackMax Maximum Interval of attack, in ticks
     */
    public PathfinderRangedAttack(@NotNull Snowman m, double speedMod, float range, int attackMin, int attackMax) {
        super(m);

        this.speedMod = speedMod;
        this.range = range;
        this.aMax = attackMax;
        this.aMin = attackMin;
    }

    /**
     * Constructs a PathfinderRangedAttack.
     * @param m Pillager to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param attackMin Minimum Interval of attack, in ticks
     * @param attackMax Maximum Interval of attack, in ticks
     */
    public PathfinderRangedAttack(@NotNull Pillager m, double speedMod, float range, int attackMin, int attackMax) {
        super(m);

        this.speedMod = speedMod;
        this.range = range;
        this.aMax = attackMax;
        this.aMin = attackMin;
    }

    /**
     * Fetches the minimum attack interval, in ticks.
     * @return Minimum Attack Interval
     */
    public int getMinAttackInterval() {
        return this.aMin;
    }

    /**
     * Fetches the maximum attack interval, in ticks.
     * @return Maximum Attack Interval
     */
    public int getMaxAttackInterval() {
        return this.aMax;
    }

    /**
     * Sets the minimum attack interval, in ticks.
     * @param min minimum attack interval
     */
    public void setMinAttackInterval(int min) {
        this.aMin = min;
    }

    /**
     * Sets the maximum attack interval, in ticks.
     * @param max maximum attack interval
     */
    public void setMaxAttackInterval(int max) {
        this.aMax = max;
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

    @Override
    public RangedAttackGoal getHandle() {
        return new RangedAttackGoal((RangedAttackMob) nmsEntity, speedMod, aMin, aMax, range);
    }

}
