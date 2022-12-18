package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.DragonBehavior;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static me.gamercoder215.mobchip.abstraction.ChipUtil.getWrapper;

public class BukkitDragonBehavior extends BukkitEntityBehavior implements DragonBehavior {

    final EnderDragon m;

    private static final ChipUtil wrapper = getWrapper();

    public BukkitDragonBehavior(EnderDragon m) {
        super((Mob) m);
        this.m = m;
    }

    @Override
    public @NotNull BehaviorResult naturalKnockback(@Nullable List<Entity> entities) {
        if (entities == null) return BehaviorResult.STOPPED;
        wrapper.knockback(m, entities);
        return BehaviorResult.STOPPED;
    }
}
