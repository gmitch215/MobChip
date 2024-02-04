package me.gamercoder215.mobchip.ai.enderdragon

import org.bukkit.entity.EnderDragon

/**
 * Fetches the Phases for this [EnderDragon].
 * @return Ender Dragon Phases
 */
inline val EnderDragon.phases: DragonPhases
    get() = DragonPhases(this)