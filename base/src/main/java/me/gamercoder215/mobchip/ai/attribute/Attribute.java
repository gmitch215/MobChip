package me.gamercoder215.mobchip.ai.attribute;

import org.bukkit.Keyed;

/**
 * Represents an ordinary Attribute of a Mob
 */
public interface Attribute extends Keyed {

    /**
     * Fetches the minimum value of this Attribute.
     * @return Minimum value
     */
    double getMinValue();

    /**
     * Fetches the maximum value of this Attribute
     * @return Maximum value
     */
    double getMaxValue();

    /**
     * Fetches the Default value of this Attribute
     * @return Default value
     */
    double getDefaultValue();

    /**
     * Whether this Attribute will sync and appear on the client's side.
     * @return true if also client side, else false
     */
    boolean isClientSide();

}
