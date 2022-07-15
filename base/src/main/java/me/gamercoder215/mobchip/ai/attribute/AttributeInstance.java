package me.gamercoder215.mobchip.ai.attribute;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a custom Attribute Instance of a Mob
 */
public interface AttributeInstance extends org.bukkit.attribute.AttributeInstance {

    /**
     * @deprecated Use {@link #getGenericAttribute()} to get the actual Attribute.
     * @return null
     */
    @Deprecated
    default org.bukkit.attribute.Attribute getAttribute() {
        return null;
    }

    /**
     * The attribute pertaining to this instance.
     * @return the attribute
     */
    @NotNull
    Attribute getGenericAttribute();

}
