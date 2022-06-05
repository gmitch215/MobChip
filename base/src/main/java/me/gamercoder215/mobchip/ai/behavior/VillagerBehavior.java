package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.jetbrains.annotations.NotNull;

/**
 * Represents Behavior specific to Villagers
 */
public interface VillagerBehavior extends CreatureBehavior {

    /**
     * Makes this Villager harvest Farmland.
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult harvestFarmland();

    /**
     * Makes this Villager show trades to a Player.
     * @param minDuration Minimum Duration, in ticks, of showing
     * @param maxDuration Maximum Duration, in ticks, of showing
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult showTrades(int minDuration, int maxDuration);

    /**
     * Makes this Village show trades to a Player.
     * @param duration How long to show trades, in ticks
     * @return Result of Behavior
     */
    @NotNull
    default BehaviorResult showTrades(int duration) {
        return showTrades(duration, duration);
    }

    /**
     * Resets this Villager's profession.
     * @return Result of Behavior
     */
    @NotNull BehaviorResult resetProfession();

    /**
     * Makes this Villager gift the hero of the Raid.
     * @param duration Duration, in ticks, of gifting
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult giftHero(int duration);

    /**
     * Makes this Villager celebrate that they survived the Raid.
     * @param minDuration Minimum Duration, in ticks, of celebrating
     * @param maxDuration Maximum Duration, in ticks, of celebrating
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult celebrateSurvivedRaid(int minDuration, int maxDuration);

    /**
     * Makes this Villager celebrate that they survived the Raid.
     * @param duration Duration, in ticks, of celebrating
     * @return Result of Behavior
     */
    @NotNull
    default BehaviorResult celebrateSurvivedRaid(int duration) { return celebrateSurvivedRaid(duration, duration); }

    /**
     * Makes this Villager find a potential Job Site.
     * @param speedMod Speed Modifier while looking
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult findJobSite(float speedMod);

    /**
     * Makes this Villager find a potential Job Site with the default Speed Modifier.
     * @return Result of Behavior
     */
    default BehaviorResult findJobSite() {
        return findJobSite(SpeedModifier.DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Makes this Villager find the nearest Village.
     * @param minDistance Minimum Distance from Village
     * @param speedMod Speed Modifier while looking
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult findNearestVillage(int minDistance, float speedMod);

    /**
     * Makes this Villager find the nearest Village, with no speed modifier.
     * @param minDistance Minimum Distance from Village
     * @return Result of Behavior
     */
    @NotNull
    default BehaviorResult findNearestVillage(int minDistance) { return findNearestVillage(minDistance, 1); }

    /**
     * Makes this Villager work at its Job Site.
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult workAtJob();

    /**
     * Makes this Villager use bonemeal.
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult useBonemeal();

}
