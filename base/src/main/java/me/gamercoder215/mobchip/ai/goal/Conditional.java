package me.gamercoder215.mobchip.ai.goal;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Represents a Conditional State for this Pathfinder to work
 * @param <T> Type of Condition to resolve
 */
public interface Conditional<T> {
	
	/**
	 * Sets the Condition of this Conditional Pathfinder.
	 * @return Predicate of Condition
	 */
	@NotNull
	Predicate<T> getCondition();
	
	/**
	 * Sets the Condition of this Conditional Pathfinder.
	 * @param condition Condition to set
	 */
	void setCondition(@NotNull Predicate<T> condition);
	
}
