package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.Mob;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for Any Ranged Monster to shoot arrows from a bow
 */
public final class PathfinderRangedBowAttack extends Pathfinder implements SpeedModifier, Ranged {
    
    private double speedMod;
    private float range;
    private int aInv;

    /**
     * Constructs a PathfinderRangedBowAttack with the default attack range.
     * @param m Skeleton to use
     * @param speedMod Speed Modifier while attacking
     */
    public PathfinderRangedBowAttack(@NotNull ProjectileSource m, double speedMod) {
        this(m, speedMod, DEFAULT_ATTACK_RANGE);
    }
    /**
     * Constructs a PathfinderRangedBowAttack with the default interval.
     * @param m Skeleton to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     */
    public PathfinderRangedBowAttack(@NotNull ProjectileSource m, double speedMod, float range) {
        this(m, speedMod, range, DEFAULT_ATTACK_INTERVAL);
    }
    /**
     * Constructs a PathfinderRangedBowAttack.
     * @param m Skeleton to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param interval Interval of attack, in ticks
     */
    public PathfinderRangedBowAttack(@NotNull ProjectileSource m, double speedMod, float range, int interval) {
        super((Mob) m);

        this.speedMod = speedMod;
        this.range = range;
        this.aInv = interval;
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
     * Gets the current interval of attack, in ticks.
     * @return Interval of attack, in ticks
     */
    public int getInterval() {
        return this.aInv;
    }

    /**
     * Sets the current interval of attack, in ticks
     * @param interval Interval of attack
     */
    public void setInterval(int interval) {
        this.aInv = interval;
    }


    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.LOOKING, PathfinderFlag.MOVEMENT };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalBowShoot";
    }
}
