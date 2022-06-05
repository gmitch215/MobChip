package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Represents Entity Behaviors for Advanced Mobs (i.e. Creatures)
 */
public interface CreatureBehavior extends EntityBehavior {

    /**
     * Makes this Creature panic.
     * @param speedMod Speed Modifier while panicking
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult panic(float speedMod);

    /**
     * Makes this Creature panic with the default speed modifier
     * @return Result of Behavior
     */
    @NotNull
    default BehaviorResult panic() {
        return panic(SpeedModifier.DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Follows the Entity's Temptation (e.g. target, tempting player).
     * @param speedModifier Function that returns speed modifier
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult followTemptation(Function<LivingEntity, Float> speedModifier);

    /**
     * Follows the Entity's Temptation (e.g. target, tempting player).
     * @param speedMod Speed Modifier while following
     * @return Result of Behavior
     */
    @NotNull
    default BehaviorResult followTemptation(float speedMod) {
        return followTemptation(c -> speedMod);
    }

    /**
     * Follows the Entity's Temptation (e.g. target, tempting player), with no Speed Modifier.
     * @return Result of Behavior
     */
    @NotNull
    default BehaviorResult followTemptation() {
        return followTemptation(1);
    }

    /**
     * Makes this Creature attempt to find water.
     * @param range Radius of how far to look
     * @param speedMod Speed Modifier while looking
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult tryFindWater(int range, float speedMod);

    /**
     * Makes this Creature attempt to find water, with no speed modifier.
     * @param range Radius of how far to look
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult tryFindWater(int range) {
        return tryFindWater(range, 1);
    }

}
