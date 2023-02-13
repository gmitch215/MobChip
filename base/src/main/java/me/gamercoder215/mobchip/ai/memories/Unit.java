package me.gamercoder215.mobchip.ai.memories;

import me.gamercoder215.mobchip.EntityBrain;

/**
 * <p>Represents a Memory Unit.</p>
 * <p>For some memories that require active cooldowns, such as {@link EntityMemory#IS_SNIFFING}, the cooldown is read instead of the actual memory value.</p>
 *
 * Example:
 * <pre>{@code
 *   public void setMemories(Warden w) {
 *       EntityBrain brain = BukkitBrain.getBrain(w);
 *
 *       brain.setMemory(EntityMemory.IS_SNIFFING, Unit.INSTANCE, 500); // Sniffing for 500 ticks
 *   }
 * }</pre>
 *
 * @see EntityBrain#getExpiration(Memory)
 */
public enum Unit {

    /**
     * The only Unit Instance.
     */
    INSTANCE
}
