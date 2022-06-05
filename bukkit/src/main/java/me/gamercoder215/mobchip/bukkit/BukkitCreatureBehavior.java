package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.CreatureBehavior;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.TryFindWater;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

class BukkitCreatureBehavior extends BukkitEntityBehavior implements CreatureBehavior {

    final PathfinderMob nmsMob;

    BukkitCreatureBehavior(PathfinderMob nmsMob) {
        super(nmsMob);
        this.nmsMob = nmsMob;
    }

    @Override
    public @NotNull BehaviorResult panic(float speedMod) {
        return new BukkitBehaviorResult(new AnimalPanic(speedMod), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult followTemptation(Function<LivingEntity, Float> speedModifier) {
        return new BukkitBehaviorResult(new FollowTemptation(f -> speedModifier.apply(ChipConversions.convertType(f))), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult tryFindWater(int range, float speedMod) {
        return new BukkitBehaviorResult(new TryFindWater(range, speedMod), level, nmsMob);
    }
}
