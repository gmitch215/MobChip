package me.gamercoder215.mobchip;

import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.behavior.Behavior;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.ai.sensing.Sensor;
import me.gamercoder215.mobchip.attributes.Attribute;
import me.gamercoder215.mobchip.attributes.ChipAttributeInstance;

public interface EntityBrain {
    
    /**
     * Get the Entity AI associated with this Brain.
     * @return Entity AI
     */
    EntityAI getGoalAI();

    /**
     * Get the Entity Target AI associated with this Brain.
     * @return Entity Target AI
     */
    EntityAI getTargetAI();

    /**
     * Adds a Temporary Behavior to this Brain.
     * @param b Behavior to add
     * @return true if addition was successful, else false
     */
    boolean addBehavior(@NotNull Behavior b);

    /**
     * Get the Entity Navigation associated with this Brain.
     * @return Entity Navigation
     */
    @NotNull
    EntityNavigation getNavigation();

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
     */
    void setMemory(@NotNull EntityMemory memory, @Nullable Object value) throws IllegalArgumentException;
    
    /**
     * Sets a temporary memory into this entity's brain.
     * <p>
     * Removing ANY memory should be using {@link #setMemory(EntityMemory, Object)} with null as the second parameter.
     * @param memory Memory to change
     * @param value Value of new memory
     * @param expire How many ticks until this memory will be forgotten/removed
     * @throws IllegalArgumentException if the value is not suitable for this memory / ticks amount is invalid
     */
    void setMemory(@NotNull EntityMemory memory, @NotNull Object value, long expire) throws IllegalArgumentException;

    /**
     * Sets multilple permanent memories into this Entity's Brain. 
     * @param map Map of Memories to their values
     */
    default void setMemories(@NotNull Map<EntityMemory, ? extends Object> map) {
        for (Map.Entry<EntityMemory, ? extends Object> entry : map.entrySet()) {
            setMemory(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Sets multilple temporary memories into this Entity's Brain. 
     * @param map Map of Memories to their values
     * @param expire How many ticks until this memory is forgotten/removed
     */
    default void setMemories(@NotNull Map<EntityMemory, ? extends Object> map, long expire) {
        for (Map.Entry<EntityMemory, ? extends Object> entry : map.entrySet()) {
            setMemory(entry.getKey(), entry.getValue(), expire);
        }
    }

    /**
     * Fetch the Memory that is stored in this Entity's Brain.
     * @param memory Memory to fetch
     * @return Found value as an object, null if not present
     */
    @Nullable
    Object getMemory(@NotNull EntityMemory memory);

    /**
     * Fetch the Memory that is stored in this Entity's Brain.
     * @param <T> Type of Object to cast to
     * @param memory Memory to fetch
     * @param clazz Class Object used to cast
     * @return Found value as the class represents, null if type is invalid or not present
     */
    @Nullable
    <T> T getMemory(@NotNull EntityMemory memory, @NotNull Class<T> clazz);

    /**
     * Get the expiration date of this Memory.
     * @param memory Memory to fetch
     * @return Found expiration date, or 0 if not found
     */
    long getExpiration(@NotNull EntityMemory memory);

    /**
     * Whether or not this Brain contains this memory.
     * @param memory Memory to fetch
     * @return true if contains, else false
     */
    boolean containsMemory(@NotNull EntityMemory memory);

    /**
     * Whether or not this Brain contains all of these memories.
     * @param memories Group of memories to query
     * @return true if they <strong>all</strong> are contained, else false
     */
    default boolean containsAllMemories(@NotNull EntityMemory... memories) {
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
     * Adds a Sensor to this Entity Brain.
     * @param s Sensor to use
     */
    void addSensor(@NotNull Sensor s);
    
    /**
     * Get the Attribute Instance associated with this Attribute.
     * @param a Attribute to get
     * @return Found Attribute Instance, can be null if not registered
     */
    @Nullable
    ChipAttributeInstance getAttribute(@NotNull Attribute a);
    
    
}
