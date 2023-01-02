package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import org.jetbrains.annotations.NotNull;

/**
 * Represents Behavior specific to Villagers
 */
public interface VillagerBehavior extends CreatureBehavior {

    /**
     * Makes this Villager harvest Farmland.
     * <p>This behavior requires {@link EntityMemory#LOOKING_TARGET} and {@link EntityMemory#WALKING_TARGET} to be absent, and {@link EntityMemory#SECONDARY_JOB_SITE} to be present.</p>
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult harvestFarmland();

    /**
     * Makes this Villager show trades to a Player.
     * <p>This behavior requires {@link EntityMemory#INTERACTION_TARGET} to be present.</p>
     * @param minDuration Minimum Duration, in ticks, of showing
     * @param maxDuration Maximum Duration, in ticks, of showing
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult showTrades(int minDuration, int maxDuration);

    /**
     * Makes this Village show trades to a Player.
     * <p>This behavior requires {@link EntityMemory#INTERACTION_TARGET} to be present.</p>
     * @param duration How long to show trades, in ticks
     * @return Result of Behavior
     */
    @NotNull
    default BehaviorResult showTrades(int duration) {
        return showTrades(duration, duration);
    }

    /**
     * Resets this Villager's profession.
     * <p>This method removes {@link EntityMemory#JOB_SITE} for you.</p>
     * @return Result of Behavior
     */
    @NotNull BehaviorResult resetProfession();

    /**
     * Makes this Villager gift the hero of the Raid.
     * <p>This behavior requires the follow memories to be registered:</p>
     * <ul>
     *     <li>{@link EntityMemory#WALKING_TARGET}</li>
     *     <li>{@link EntityMemory#LOOKING_TARGET}</li>
     *     <li>{@link EntityMemory#INTERACTION_TARGET}</li>
     * </ul>
     * <p>This behavior also requires {@link EntityMemory#NEAREST_VISIBLE_PLAYER} to be present.</p>
     * @param duration Duration, in ticks, of gifting
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult giftHero(int duration);

    /**
     * Makes this Villager celebrate that they survived the Raid.
     * <p>This behavior does not require any memories.</p>
     * @param minDuration Minimum Duration, in ticks, of celebrating
     * @param maxDuration Maximum Duration, in ticks, of celebrating
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult celebrateSurvivedRaid(int minDuration, int maxDuration);

    /**
     * Makes this Villager celebrate that they survived the Raid.
     * <p>This behavior does not require any memories.</p>
     * @param duration Duration, in ticks, of celebrating
     * @return Result of Behavior
     */
    @NotNull
    default BehaviorResult celebrateSurvivedRaid(int duration) { return celebrateSurvivedRaid(duration, duration); }

    /**
     * Makes this Villager find a potential Job Site.
     * <p>This behavior requires {@link EntityMemory#JOB_SITE} to be present.</p>
     * @param speedMod Speed Modifier while looking
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult findJobSite(float speedMod);

    /**
     * Makes this Villager find a potential Job Site with the default Speed Modifier.
     * <p>This behavior requires {@link EntityMemory#JOB_SITE} to be present.</p>
     * @return Result of Behavior
     */
    default BehaviorResult findJobSite() {
        return findJobSite(SpeedModifier.DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Makes this Villager find the nearest Village.
     * <p>This method removes {@link EntityMemory#WALKING_TARGET} for you.</p>
     * @param minDistance Minimum Distance from Village
     * @param speedMod Speed Modifier while looking
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult findNearestVillage(int minDistance, float speedMod);

    /**
     * Makes this Villager find the nearest Village, with no speed modifier.
     * <p>This method removes {@link EntityMemory#WALKING_TARGET} for you.</p>
     * @param minDistance Minimum Distance from Village
     * @return Result of Behavior
     */
    @NotNull
    default BehaviorResult findNearestVillage(int minDistance) { return findNearestVillage(minDistance, 1); }

    /**
     * Makes this Villager work at its Job Site.
     * <p>This behavior requires {@link EntityMemory#JOB_SITE} to be present and {@link EntityMemory#LOOKING_TARGET} to be registered.</p>
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult workAtJob();

    /**
     * Makes this Villager use bonemeal.
     * <p>This method removes {@link EntityMemory#LOOKING_TARGET} and {@link EntityMemory#WALKING_TARGET} for you.</p>
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult useBonemeal();

}
