package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Neutral Mob to reset its Universal Anger
 */
public final class PathfinderResetAnger extends Pathfinder {
    
    private boolean alertOthers;

    /**
     * Constructs a PathfinderResetAnger with alert others set to true
     * @param m Mob to use
     */
    public PathfinderResetAnger(@NotNull Mob m) {
        this(m, true);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[0];
    }

    /**
     * Constructs a PathfinderResetAnger.
     * @param m Mob to use
     * @param alertOthers Whether to alert other mobs of this type
     */
    public PathfinderResetAnger(@NotNull Mob m, boolean alertOthers) {
        super(m);

        this.alertOthers = alertOthers;
    }

    /**
     * Whether the Mob will alert other entities of this type.
     * @return true if alerting others, else false
     */
    public boolean isAlertingOthers() {
        return this.alertOthers;
    }

    /**
     * Sets whether the Mob will alert other entities of this type.
     * @param alert true if alerting others, else false
     */
    public void setAlertOthers(boolean alert) {
        this.alertOthers = alert;
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalUniversalAngerReset";
    }
}
