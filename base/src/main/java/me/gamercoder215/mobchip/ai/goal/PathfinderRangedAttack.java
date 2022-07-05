package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.Mob;
import org.bukkit.projectiles.ProjectileSource;
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
     * Constructs a PathfinderRangedAttack with the default attack range.
     * @param m ProjectileSource to use
     * @param speedMod Speed Modifier while attacking
     */
    public PathfinderRangedAttack(@NotNull ProjectileSource m, double speedMod) {
        this(m, speedMod, DEFAULT_ATTACK_RANGE);
    }

    /**
     * Constructs a PathfinderRangedAttack with the default interval.
     * @param m Skeleton to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     */
    public PathfinderRangedAttack(@NotNull ProjectileSource m, double speedMod, float range) {
        this(m, speedMod, range, DEFAULT_ATTACK_INTERVAL);
    }

    /**
     * Constructs a PathfinderRangedAttack with the max and min intervals the same.
     * @param m ProjectileSource to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param interval Interval of attack, in ticks
     */
    public PathfinderRangedAttack(@NotNull ProjectileSource m, double speedMod, float range, int interval) {
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
    public PathfinderRangedAttack(@NotNull ProjectileSource m, double speedMod, float range, int attackMin, int attackMax) {
        super(m instanceof Mob ? (Mob) m : null);

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
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.LOOKING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalArrowAttack";
    }
}
