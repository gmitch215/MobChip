package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.AllayBehavior;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import org.bukkit.Location;
import org.bukkit.entity.Allay;
import org.jetbrains.annotations.NotNull;

class BukkitAllayBehavior extends BukkitUpdatableCreatureBehavior implements AllayBehavior {

    final Allay m;

    BukkitAllayBehavior(Allay m) {
        super(m);
        this.m = m;
    }

    @Override
    public @NotNull BehaviorResult hearNoteblock(@NotNull Location loc) {
        return wrapper.hearNoteblock(m, loc);
    }
}
