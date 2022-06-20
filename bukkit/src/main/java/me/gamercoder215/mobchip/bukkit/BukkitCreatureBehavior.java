package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.CreatureBehavior;
import me.gamercoder215.mobchip.util.MobChipUtil;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.TryFindWater;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

class BukkitCreatureBehavior extends BukkitEntityBehavior implements CreatureBehavior {

    final PathfinderMob nmsCreature;

    BukkitCreatureBehavior(PathfinderMob nmsMob) {
        super(nmsMob);
        this.nmsCreature = nmsMob;
    }

    @Override
    public @NotNull BehaviorResult panic(float speedMod) {
        return new BukkitBehaviorResult(new AnimalPanic(speedMod), level, nmsCreature);
    }

    @Override
    public @NotNull BehaviorResult followTemptation(@NotNull Function<LivingEntity, Float> speedModifier) {
        Validate.notNull(speedModifier, "Speed Modifier cannot be null");
        return new BukkitBehaviorResult(new FollowTemptation(f -> speedModifier.apply(MobChipUtil.convert(f))), level, nmsCreature);
    }

    @Override
    public @NotNull BehaviorResult tryFindWater(int range, float speedMod) {
        return new BukkitBehaviorResult(new TryFindWater(range, speedMod), level, nmsCreature);
    }
}
