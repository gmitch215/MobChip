package me.gamercoder215.mobchip.ai.goal;

import java.lang.reflect.Field;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.Probable;
import me.gamercoder215.mobchip.ai.goal.target.Filtering;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

/**
 * Represents a Pathfinder for an Entity to look at another Entity
 * @param <T> Type of LivingEntity to look at
 */
public final class PathfinderLookAtEntity<T extends LivingEntity> extends Pathfinder implements Filtering<T>, Probable, Ranged {

    /**
     * Default Probability to look at an Entity (0.02F)
     */
    public static final float DEFAULT_PROBABILITY = 0.02F;

    private float lookRange;
    private float probability;
    private Class<T> filterClass;

    /**
     * Constructs a PathfinderLookAtEntity from a NMS LookAtPlayerGoal.
     * @param g Goal to use
     */
    @SuppressWarnings("unchecked")
    public PathfinderLookAtEntity(@NotNull LookAtPlayerGoal g) {
        super(Pathfinder.getEntity(g, "b"));

        try {
            Field a = LookAtPlayerGoal.class.getDeclaredField("d");
            a.setAccessible(true);
            this.lookRange = a.getFloat(g);

            Field b = LookAtPlayerGoal.class.getDeclaredField("e");
            b.setAccessible(true);
            this.probability = b.getFloat(g);

            Field c = LookAtPlayerGoal.class.getDeclaredField("f");
            c.setAccessible(true);
            this.filterClass = (Class<T>) ChipConversions.toBukkitClass(LivingEntity.class, (Class<? extends net.minecraft.world.entity.LivingEntity>) c.get(g));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a PathfinderLookAtEntity with the default {@link Ranged#DEFAULT_LOOK_RANGE}.
     * @param m Mob to use
     * @param filter Filter class to look at
     */
    public PathfinderLookAtEntity(@NotNull Mob m, @NotNull Class<T> filter) {
        this(m, filter, DEFAULT_LOOK_RANGE);
    }

    /**
     * Constructs a PathfinderLookAtEntity with the default probability.
     * @param m Mob to use
     * @param filter Filter class to look at
     * @param lookRange Range of blocks to find selected class filter
     */
    public PathfinderLookAtEntity(@NotNull Mob m, @NotNull Class<T> filter, float lookRange) {
        this(m, filter, lookRange, DEFAULT_PROBABILITY);
    }

    /**
     * Constructs a PathfinderLookAtEntity.
     * @param m Mob to use
     * @param filter Filter class to look at
     * @param lookRange Range of blocks to find selected class filter
     * @param probability Probability (0.0 - 1.0) to look at something. <strong>Called every tick, recommended to be a low number. See {@link #DEFAULT_PROBABILITY}.</strong>
     */
    public PathfinderLookAtEntity(@NotNull Mob m, @NotNull Class<T> filter, float lookRange, float probability) {
        super(m); 

        this.filterClass = filter;
        this.lookRange = lookRange;
        this.probability = probability;
    }

    @Override
    public float getRange() {
        return this.lookRange;
    }

    @Override
    public void setRange(float range) {
        this.lookRange = range;
    }

    @Override
    public float getProbability() {
        return this.probability;
    }

    @Override
    public void setProbability(float prob) {
        this.probability = prob;
    }

    @Override
    public void setFilter(@NotNull Class<T> clazz) {
        this.filterClass = clazz;
    }

    @Override
    public Class<T> getFilter() {
        return this.filterClass;
    }

    @Override
    public LookAtPlayerGoal getHandle() {
        return new LookAtPlayerGoal(nmsEntity, ChipConversions.toNMSClass(filterClass).asSubclass(net.minecraft.world.entity.LivingEntity.class), lookRange, probability, false);
    }
    


}
