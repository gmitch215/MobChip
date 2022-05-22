package me.gamercoder215.mobchip;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;

/**
 * Represents an Entire Entity Brain
 */
@SuppressWarnings({ "rawtypes", "unchecked"})
public interface EntityBrain {

    /**
     * Get the Entity that this Brain relates to.
     * @return Entity of this brain 
     */
    @NotNull
    Mob getEntity();

    /**
     * Get the Entity AI associated with this Brain.
     * @return Entity AI
     */
    @NotNull
    EntityAI getGoalAI();

    /**
     * Get the Entity Target AI associated with this Brain.
     * @return Entity Target AI
     */
    @NotNull
    EntityAI getTargetAI();

    /**
     * Creates new Entity Navigation associated with this Brain.
     * @return Created Entity Navigation
     */
    @NotNull
    EntityNavigation createNavigation();

    /**
     * Get the Entity Controller associated with this Brain.
     * @return Entity Controller
     */
    @NotNull
    EntityController getController();

    /**
     * Sets a permanent memory into this entity's brain.
     * @param memory Memory to change
     * @param value Value of new memory, null to remove
     * @throws IllegalArgumentException if the value is not suitable for this memory
     * @param <T> Memory Type
     */
    <T> void setMemory(@NotNull EntityMemory<T> memory, @Nullable T value) throws IllegalArgumentException;
    
    /**
     * Sets a temporary memory into this entity's brain.
     * <p>
     * Removing ANY memory should be using {@link #setMemory(EntityMemory, Object)} with null as the second parameter.
     * @param memory Memory to change
     * @param value Value of new memory
     * @param expire How many ticks until this memory will be forgotten/removed
     * @throws IllegalArgumentException if the value is not suitable for this memory / ticks amount is invalid
     * @param <T> Memory Type
     */
    <T> void setMemory(@NotNull EntityMemory<T> memory, @Nullable T value, long expire) throws IllegalArgumentException;

    /**
     * Sets multiple permanent memories into this Entity's Brain.
     * @param map Map of Memories to their values
     */
    default void setMemories(@NotNull Map<EntityMemory, ?> map) {
        for (Map.Entry<EntityMemory, ?> entry : map.entrySet()) {
            setMemory(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Sets multiple temporary memories into this Entity's Brain.
     * @param map Map of Memories to their values
     * @param expire How many ticks until this memory is forgotten/removed
     */
    default void setMemories(@NotNull Map<EntityMemory, ?> map, long expire) {
        for (Map.Entry<EntityMemory, ?> entry : map.entrySet()) {
            setMemory(entry.getKey(), entry.getValue(), expire);
        }
    }

    /**
     * Fetch the Memory that is stored in this Entity's Brain.
     * @param memory Memory to fetch
     * @return Found value as an object, null if not present
     * @param <T> Memory Type
     */
    @Nullable
    <T> T getMemory(@NotNull EntityMemory<T> memory);

    /**
     * Get the expiration date of this Memory.
     * @param memory Memory to fetch
     * @return Found expiration date, or 0 if no expiration or not found
     */
    long getExpiration(@NotNull EntityMemory<?> memory);

    /**
     * Whether this Brain contains this memory.
     * @param memory Memory to fetch
     * @return true if contains, else false
     */
    boolean containsMemory(@NotNull EntityMemory<?> memory);

    /**
     * Whether this Brain contains all of these memories.
     * @param memories Group of memories to query
     * @return true if they <strong>all</strong> are contained, else false
     */
    default boolean containsAllMemories(@NotNull EntityMemory<?>... memories) {
		boolean contains = true;

		for (EntityMemory m : memories) {
			if (!(containsMemory(m))) {
				contains = false;
				break;
			}
		}

		return contains;
    }

    /**
     * Whether this Entity is in its restriction area.
     * @return true if inside, else false
     */
    boolean isInRestriction();

    /**
     * Sets the Restriction Area for this Entity.
     * @param center Location center
     * @param radius Radius of restriction center
     */
    void setRestrictionArea(Location center, int radius);

    /**
     * Clears this Entity's Restriction Area.
     */
    void clearRestrictionArea();

    /**
     * Gets the current restriction area.
     * @return Restriction Area
     */
    Location getRestrictionArea();

    /**
     * Whether this entity has a restriction area.
     * @return true if it has a restriction area, else false
     */
    boolean hasRestriction();

    /**
     * Fetch the current restriction radius for this entity.
     * @return Restriction Radius
     */
    int getRestrictionRadius();

    /**
     * Whether this Mob can see another Entity.
     * @param en Entity to test
     * @return true if entity can see, else false
     */
    boolean canSee(@Nullable Entity en);

    /**
     * Whether this Mob can see another Entity.
     * @param brain EntityBrain to test
     * @return true if entity can see, else false
     */
    default boolean canSee(@Nullable EntityBrain brain) {
        if (brain == null) return false;
        return canSee(brain.getEntity());
    }
    
}
