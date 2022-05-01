package me.gamercoder215.mobchip.ai.goal;

import java.util.function.Predicate;

/**
 * Represents a Conditional State for this Pathfinder to work
 * @param <T>
 */
public interface Conditional<T> {
	
	/**
	 * Sets the Condition of this Conditional Pathfinder.
	 * @return Predicate of Condition
	 */
	Predicate<T> getCondition();
	
	/**
	 * Sets the Condition of this Conditional Pathfinder.
	 * @param condition Condition to set
	 */
	void setCondition(Predicate<T> condition);
	
}
