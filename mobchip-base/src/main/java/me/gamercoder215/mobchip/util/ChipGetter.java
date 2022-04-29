package me.gamercoder215.mobchip.util;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.jetbrains.annotations.Nullable;

import net.minecraft.server.level.ServerLevel;

public final class ChipGetter {
    
    private ChipGetter() {};

    public static ServerLevel getLevel(@Nullable World w) {
        if (w == null) return null;
        return ((CraftWorld) w).getHandle();
    }

}
