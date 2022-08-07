package me.gamercoder215.mobchip.ai.goal;

/**
 * Represents a Pathfinder that has a repeating interval, in ticks
 */
public interface Repeated {

    /**
     * Gets the repeating interval, in ticks
     * @return Interval
     */
    int getInterval();

    /**
     * Sets the repeating interval, in ticks
     * @param interval Interval
     * @throws IllegalArgumentException if interval is less than 0
     */
    void setInterval(int interval) throws IllegalArgumentException;

}
