package me.gamercoder215.mobchip.bosses;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Represents a Boss Handler
 */
public final class BossHandler {
    
    private final Plugin plugin;
    private final Boss<?> boss;

    private final List<Boss<?>> bosses = new ArrayList<>();

    BossHandler(Boss<?> boss, Plugin p) {
        new BossEvents(this);
        this.plugin = p;
        this.boss = boss;

        this.bosses.add(boss);
    }

    /**
     * Get the Plugin associated with this BossHandler.
     * @return Plugin in this Handler
     */
    public Plugin getPlugin() {
        return this.plugin;
    }

    /**
     * Get the Boss of this Handler.
     * @return Boss Found
     */
    public Boss<?> getBoss() {
        return this.boss;
    }

    private static class BossEvents implements Listener {

        private BossHandler handler;

        private BossEvents(BossHandler handler) {
            this.handler = handler;
            Bukkit.getPluginManager().registerEvents(this, handler.plugin);
        }

        @EventHandler
        public void onDamageDefensive(EntityDamageEvent e) {
            if (!(e.getEntity() instanceof Mob m)) return;

            for (Boss<? extends Mob> boss : handler.bosses) {
                if (boss.getMob().getUniqueId().equals(m.getUniqueId())) {
                    boss.onDamageDefensive(e);
                }
            }
        }

        @EventHandler
        public void onDamageOffensive(EntityDamageByEntityEvent e) {
            if (!(e.getDamager() instanceof Mob m)) return;

            for (Boss<? extends Mob> boss : handler.bosses) {
                if (boss.getMob().getUniqueId().equals(m.getUniqueId())) {
                    boss.onDamageOffensive(e);
                }
            }
        }

        @EventHandler
        public void onDeath(EntityDeathEvent e) {
            if (!(e.getEntity() instanceof Mob m)) return;

            for (Boss<? extends Mob> boss : handler.bosses) {
                if (boss.getMob().getUniqueId().equals(m.getUniqueId())) {
                    boss.onDeath(e);
                    for (ItemStack i : boss.getDrops()) m.getWorld().dropItemNaturally(m.getLocation(), i);
                    if (boss.getDeathSound() != null) m.getWorld().playSound(m, boss.getDeathSound(), 3F, 1F);
                }
            }
        }

    }

}
