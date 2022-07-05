package me.gamercoder215.mobchip.ai.goal.target;

import me.gamercoder215.mobchip.ai.goal.CustomPathfinder;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder that involves targeting.
 * <p>
 * <strong>For custom Pathfinders, extend {@link CustomPathfinder}.</strong>
 */
public abstract class TargetPathfinder extends Pathfinder implements Targeting {

    private boolean mustSee;
    private boolean reach;

    /**
     * Constructs a TargetPathfinder with reach set to false.
     * @param m Mob to use
     * @param see Whether the mob has to see the target
     */
    protected TargetPathfinder(@NotNull Mob m, boolean see) {
        this(m, see, false);
    }

    /**
     * Constructs a TargetPathfinder.
     * @param m Mob to use
     * @param see Whether the mob has to see the target
     * @param reach Whether the mob has to reach the target
     */
    protected TargetPathfinder(@NotNull Mob m, boolean see, boolean reach) {
        super(m);

        this.mustSee = see;
        this.reach = reach;
    }

    @Override
    public boolean mustSee() { return this.mustSee; }

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

}
