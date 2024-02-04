package me.gamercoder215.mobchip.ai.attribute

/**
 * Fetches the MobChip Attribute counterpart to this Bukkit Attribute.
 * @return MobChip Counterpart, or Null if doesn't exist in Bukkit
 */
inline val org.bukkit.attribute.Attribute.mobChip: Attribute?
    get() = EntityAttribute.fromBukkit(this)