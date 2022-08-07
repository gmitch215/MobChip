package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.schedule.Updatable;
import org.bukkit.entity.Creature;

class BukkitUpdatableCreatureBehavior extends BukkitCreatureBehavior implements Updatable {

    BukkitUpdatableCreatureBehavior(Creature c) {
        super(c);
    }

    @Override
    public void updateActivities() {

    }
}
