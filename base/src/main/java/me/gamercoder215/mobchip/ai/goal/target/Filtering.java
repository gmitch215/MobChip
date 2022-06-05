package me.gamercoder215.mobchip.ai.goal.target;

import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder that filters in an entity class
 * @param <T> Type of Entity to filter
 */
public interface Filtering<T extends LivingEntity> {
    
    /**
     * Set the Filter that this Pathfinder is looking for. 
     * @param clazz Filter to set
     * @throws IllegalArgumentException if clazz is null
     */
    void setFilter(@NotNull Class<T> clazz) throws IllegalArgumentException ;

    /**
     * Get the filter that this Pathfinder is looking for.
     * @return Filter found
     */
    Class<T> getFilter();

}
