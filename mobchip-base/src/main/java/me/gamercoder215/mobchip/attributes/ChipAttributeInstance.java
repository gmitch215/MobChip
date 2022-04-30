package me.gamercoder215.mobchip.attributes;

import org.bukkit.attribute.AttributeInstance;

/**
 * Represents a Slightly Modified AttributeInstance
 */
public interface ChipAttributeInstance extends AttributeInstance {
	
	/**
	 * @deprecated use {@link #getEntityAttribute()}
	 * @return null
	 */
	@Deprecated
	default org.bukkit.attribute.Attribute getAttribute() {
		return null;
	}
	
	/**
	 * Gets the Attribute of this Entity.
	 * @return Attribute belonging to this ChipAttributeInstance
	 */
	public Attribute getEntityAttribute();
	
}
