package me.gamercoder215.mobchip.nbt;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the Entity's NBT Wrapper
 */
public interface EntityNBT extends NBTSection {

    /**
     * Fetches the Entity that this NBT Wrapper is for
     * @return Entity that this NBT Wrapper belongs to
     */
    @NotNull
    Mob getEntity();
}
