package me.gamercoder215.mobchip.ai.goal.target;

import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a Pathfinder for any Creature to retaliate if they are damaged 
 */
public final class PathfinderHurtByTarget extends TargetPathfinder {
    
    private final List<EntityType> ignoring;

    /**
     * Constructs a PathfinderHurtByTarget.
     * @param c Creature to use
     * @param ignore EntityTypes to ignore when hurt
     */
    public PathfinderHurtByTarget(@NotNull Creature c, EntityType... ignore) {
        super(c, true, false);
        this.ignoring = new ArrayList<>(Arrays.asList(ignore));
    }

    /**
     * Gets the current list of EntityTypes to ignore when hurt.
     * @return List of Entity Types to ignore when hurt.
     */
    @NotNull
    public List<EntityType> getIgnoring() {
        return this.ignoring;
    }

    /**
     * Sets the current list of EntityTypes to ignore when hurt.
     * @param ignoring Array of EntityTypes to ignore when hurt.
     */
    public void setIgnoring(@NotNull EntityType... ignoring) {
        this.ignoring.addAll(Arrays.asList(ignoring));
    }

    /**
     * Adds an EntityType to the List of EntityTypes to ignore when hurt.
     * @param t EntityType to add
     */
    public void addIgnore(@NotNull EntityType t) {
        this.ignoring.add(t);
    }

    /**
     * Removes an EntityType from the List of EntityTypes to ignore when hurt.
     * @param t EntityType to remove
     */
    public void removeIgnore(@NotNull EntityType t) {
        this.ignoring.remove(t);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.TARGETING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalHurtByTarget";
    }
}
