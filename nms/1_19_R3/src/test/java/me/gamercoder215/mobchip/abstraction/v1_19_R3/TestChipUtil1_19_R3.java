package me.gamercoder215.mobchip.abstraction.v1_19_R3;

import me.gamercoder215.mobchip.abstraction.v1_19_R3.ChipUtil1_19_R3;
import me.gamercoder215.mobchip.ai.gossip.GossipType;
import org.bukkit.Difficulty;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestChipUtil1_19_R3 {

    @Test
    @DisplayName("Test Bukkit-NMS Conversion")
    public void testNMSConversion() {
        // Classes
        ChipUtil1_19_R3.toNMS(LivingEntity.class);
        ChipUtil1_19_R3.toNMS(Mob.class);
        ChipUtil1_19_R3.toNMS(AbstractVillager.class);
        ChipUtil1_19_R3.toNMS(AbstractHorse.class);

        ChipUtil1_19_R3.toNMS(IronGolem.class);
        ChipUtil1_19_R3.toNMS(Chicken.class);
        ChipUtil1_19_R3.toNMS(Cow.class);

        ChipUtil1_19_R3.toNMS(Player.class);
        ChipUtil1_19_R3.toNMS(HumanEntity.class);

        // Other
        for (Difficulty d : Difficulty.values()) Assertions.assertNotNull(ChipUtil1_19_R3.toNMS(d));
        for (GossipType t : GossipType.values()) Assertions.assertNotNull(ChipUtil1_19_R3.toNMS(t));

        Assertions.assertNotNull(ChipUtil1_19_R3.toNMS(m -> m.damage(2)));
    }

    @Test
    @DisplayName("Test NMS-Bukkit Conversion")
    public void testBukkitConversion() {
        // Other
        for (net.minecraft.world.Difficulty d : net.minecraft.world.Difficulty.values()) Assertions.assertNotNull(ChipUtil1_19_R3.fromNMS(d));
        for (net.minecraft.world.entity.ai.gossip.GossipType t : net.minecraft.world.entity.ai.gossip.GossipType.values()) Assertions.assertNotNull(ChipUtil1_19_R3.fromNMS(t));
    }

}
