package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.VillagerBehavior;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.npc.Villager;
import org.jetbrains.annotations.NotNull;

class BukkitVillagerBehavior extends BukkitCreatureBehavior implements VillagerBehavior {

    final Villager nmsMob;

    BukkitVillagerBehavior(Villager nmsMob) {
        super(nmsMob);
        this.nmsMob = nmsMob;
    }

    @Override
    public @NotNull BehaviorResult harvestFarmland() {
        return new BukkitBehaviorResult(new HarvestFarmland(), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult showTrades(int minDuration, int maxDuration) {
        return new BukkitBehaviorResult(new ShowTradesToPlayer(minDuration, maxDuration), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult resetProfession() {
        return new BukkitBehaviorResult(new ResetProfession(), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult giftHero(int duration) {
        return new BukkitBehaviorResult(new GiveGiftToHero(duration), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult celebrateSurvivedRaid(int minDuration, int maxDuration) {
        return new BukkitBehaviorResult(new CelebrateVillagersSurvivedRaid(minDuration, maxDuration), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult findJobSite(float speedMod) {
        return new BukkitBehaviorResult(new GoToPotentialJobSite(speedMod), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult findNearestVillage(int minDistance, float speedMod) {
        return new BukkitBehaviorResult(new GoToClosestVillage(speedMod, minDistance), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult workAtJob() {
        return new BukkitBehaviorResult(new WorkAtPoi(), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult useBonemeal() {
        return new BukkitBehaviorResult(new UseBonemeal(), level, nmsMob);
    }
}
