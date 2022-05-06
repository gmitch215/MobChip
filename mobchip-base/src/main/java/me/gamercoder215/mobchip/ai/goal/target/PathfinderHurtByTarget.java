package me.gamercoder215.mobchip.ai.goal.target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

/**
 * Represents a Pathfinder for any Creature to retaliate if they are damaged 
 */
public final class PathfinderHurtByTarget extends TargetPathfinder {
    
    private List<EntityType> ignoring;

    /**
     * Constructs a PathfinderHurtByTarget from a NMS HurtBytargetGoal.
     * @param g Goal to use
     */
    public PathfinderHurtByTarget(@NotNull HurtByTargetGoal g) {
        super(TargetPathfinder.getEntity(g, "e"));

        List<EntityType> t = new ArrayList<>();
        Class<?>[] nmsClasses = TargetPathfinder.getField(g, "i", Class[].class);
        for (Class<?> clazz : nmsClasses) {
            for (EntityType type : EntityType.values()) {
                try {
                    if (ChipConversions.toBukkitClass(Entity.class, clazz.asSubclass(net.minecraft.world.entity.Entity.class)).equals(type.getEntityClass())) t.add(type);
                } catch (ClassCastException e) { continue; }
            }
        }

        this.ignoring = t;
    }

    /**
     * Constructs a PathfinderHurtByTarget.
     * @param c Creature to use
     * @param ignore EntityTypes to ignore when hurt
     */
    public PathfinderHurtByTarget(@NotNull Creature c, EntityType... ignore) {
        super(c);
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
    public HurtByTargetGoal getHandle() {
        List<Class<?>> classes = new ArrayList<>();

        for (EntityType t : ignoring) classes.add(ChipConversions.toNMSClass(t.getEntityClass()));
        return new HurtByTargetGoal((PathfinderMob) nmsEntity, classes.toArray(new Class[0]));
    }

}
