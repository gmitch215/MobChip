package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.CreatureBehavior;
import org.bukkit.Bukkit;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Function;

class BukkitCreatureBehavior extends BukkitEntityBehavior implements CreatureBehavior {

    final Creature m;

    BukkitCreatureBehavior(Creature c) {
        super(c);
        this.m = c;
    }

    @Override
    public @NotNull BehaviorResult panic(float speedMod) {
        return run("AnimalPanic", speedMod);
    }

    @Override
    public @NotNull BehaviorResult followTemptation(@NotNull Function<LivingEntity, Float> speedModifier) {
        notNull(speedModifier, "Speed Modifier cannot be null");
        return run("FollowTemptation", (Function<UUID, Float>) f -> speedModifier.apply((LivingEntity) Bukkit.getEntity(f)));
    }

    @Override
    public @NotNull BehaviorResult tryFindWater(int range, float speedMod) {
        return run("TryFindWater", range, speedMod);
    }
}
