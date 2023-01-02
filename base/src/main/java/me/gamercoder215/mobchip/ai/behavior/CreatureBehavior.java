package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Represents Entity Behaviors for Advanced Mobs (i.e. Creatures)
 */
public interface CreatureBehavior extends EntityBehavior {

    /**
     * Makes this Creature panic.
     * <p>This behavior only natively exists on 1.17+, meaning that on 1.17 and above, {@link EntityMemory#IS_PANICKING} needs to be registered, while on all versions, {@link EntityMemory#LAST_HURT_ENTITY} needs to be <strong>present</strong>.</p>
     * @param speedMod Speed Modifier while panicking
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult panic(float speedMod);

    /**
     * Makes this Creature panic with the default speed modifier.
     * <p>This behavior only natively exists on 1.17+, meaning that on 1.17 and above, {@link EntityMemory#IS_PANICKING} needs to be registered, while on all versions, {@link EntityMemory#LAST_HURT_ENTITY} needs to be <strong>present</strong>.</p>
     * @return Result of Behavior
     */
    @NotNull
    default BehaviorResult panic() {
        return panic(SpeedModifier.DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Follows the Entity's Temptation (e.g. target, tempting player).
     * <p>This behavior requires the following memories to be registered:</p>
     * <ul>
     *     <li>{@link EntityMemory#LOOKING_TARGET}</li>
     *     <li>{@link EntityMemory#WALKING_TARGET}</li>
     *     <li>{@link EntityMemory#IS_TEMPTED}</li>
     * </ul>
     * <p>This behavior also requires {@link EntityMemory#TEMPTING_PLAYER} memories to be present in the brain.</p>
     * <p>Finally, this behavior requires the following memories to be <strong>absent</strong> from the brain:</p>
     * <ul>
     *     <li>{@link EntityMemory#TEMPTING_COOLDOWN}</li>
     *     <li>{@link EntityMemory#BREEDING_TARGET}</li>
     *     <li>{@link EntityMemory#IS_PANICKING}</li>
     * </ul>
     * @param speedModifier Function that returns speed modifier
     * @return Result of Behavior
     * @throws IllegalArgumentException if speed modifier is null
     */
    @NotNull
    BehaviorResult followTemptation(@NotNull Function<LivingEntity, Float> speedModifier) throws IllegalArgumentException;

    /**
     * Follows the Entity's Temptation (e.g. target, tempting player).
     * <p>This behavior requires the following memories to be registered:</p>
     * <ul>
     *     <li>{@link EntityMemory#LOOKING_TARGET}</li>
     *     <li>{@link EntityMemory#WALKING_TARGET}</li>
     *     <li>{@link EntityMemory#IS_TEMPTED}</li>
     * </ul>
     * <p>This behavior also requires {@link EntityMemory#TEMPTING_PLAYER} memories to be present in the brain.</p>
     * <p>Finally, this behavior requires the following memories to be <strong>absent</strong> from the brain:</p>
     * <ul>
     *     <li>{@link EntityMemory#TEMPTING_COOLDOWN}</li>
     *     <li>{@link EntityMemory#BREEDING_TARGET}</li>
     *     <li>{@link EntityMemory#IS_PANICKING}</li>
     * </ul>
     * @param speedMod Speed Modifier while following
     * @return Result of Behavior
     */
    @NotNull
    default BehaviorResult followTemptation(float speedMod) {
        return followTemptation(c -> speedMod);
    }

    /**
     * Follows the Entity's Temptation (e.g. target, tempting player), with no Speed Modifier.
     * <p>This behavior requires the following memories to be registered:</p>
     * <ul>
     *     <li>{@link EntityMemory#LOOKING_TARGET}</li>
     *     <li>{@link EntityMemory#WALKING_TARGET}</li>
     *     <li>{@link EntityMemory#IS_TEMPTED}</li>
     * </ul>
     * <p>This behavior also requires {@link EntityMemory#TEMPTING_PLAYER} memories to be present in the brain.</p>
     * <p>Finally, this behavior requires the following memories to be <strong>absent</strong> from the brain:</p>
     * <ul>
     *     <li>{@link EntityMemory#TEMPTING_COOLDOWN}</li>
     *     <li>{@link EntityMemory#BREEDING_TARGET}</li>
     *     <li>{@link EntityMemory#IS_PANICKING}</li>
     * </ul>
     * @return Result of Behavior
     */
    @NotNull
    default BehaviorResult followTemptation() {
        return followTemptation(1);
    }

    /**
     * Makes this Creature attempt to find water.
     * <p>This behavior does not require any memories.</p>
     * @param range Radius of how far to look
     * @param speedMod Speed Modifier while looking
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult tryFindWater(int range, float speedMod);

    /**
     * Makes this Creature attempt to find water, with no speed modifier.
     * <p>This behavior does not require any memories.</p>
     * @param range Radius of how far to look
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult tryFindWater(int range) {
        return tryFindWater(range, 1);
    }

}
