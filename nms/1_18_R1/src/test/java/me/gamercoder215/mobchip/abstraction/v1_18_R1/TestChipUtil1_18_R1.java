package me.gamercoder215.mobchip.abstraction.v1_18_R1;

import me.gamercoder215.mobchip.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.Difficulty;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class TestChipUtil1_18_R1 {

    @Test
    @DisplayName("Test Bukkit-NMS Conversion")
    public void testNMSConversion() {
        // Classes
        ChipUtil1_18_R1.toNMS(LivingEntity.class);
        ChipUtil1_18_R1.toNMS(Mob.class);
        ChipUtil1_18_R1.toNMS(AbstractVillager.class);
        ChipUtil1_18_R1.toNMS(AbstractHorse.class);

        ChipUtil1_18_R1.toNMS(IronGolem.class);
        ChipUtil1_18_R1.toNMS(Chicken.class);
        ChipUtil1_18_R1.toNMS(Cow.class);

        ChipUtil1_18_R1.toNMS(Player.class);
        ChipUtil1_18_R1.toNMS(HumanEntity.class);

        // Other
        for (Difficulty d : Difficulty.values()) Assertions.assertNotNull(ChipUtil1_18_R1.toNMS(d));
        for (EntityDamageEvent.DamageCause c : EntityDamageEvent.DamageCause.values()) Assertions.assertNotNull(ChipUtil1_18_R1.toNMS(c));
        for (GossipType t : GossipType.values()) Assertions.assertNotNull(ChipUtil1_18_R1.toNMS(t));

        Assertions.assertNotNull(ChipUtil1_18_R1.toNMS(m -> m.damage(2)));
    }

    @Test
    @DisplayName("Test NMS-Bukkit Conversion")
    public void testBukkitConversion() {
        // Other
        for (net.minecraft.world.Difficulty d : net.minecraft.world.Difficulty.values()) Assertions.assertNotNull(ChipUtil1_18_R1.fromNMS(d));
        for (net.minecraft.world.entity.ai.gossip.GossipType t : net.minecraft.world.entity.ai.gossip.GossipType.values()) Assertions.assertNotNull(ChipUtil1_18_R1.fromNMS(t));
    }

    @Test
    @DisplayName("Test ChipUtil1_18_R1#getFlags")
    public void testGetFlags() {
        OptimizedSmallEnumSet1_18_R1<Goal.Flag> set = new OptimizedSmallEnumSet1_18_R1<>(Goal.Flag.class);
        set.addUnchecked(Goal.Flag.MOVE);
        set.addUnchecked(Goal.Flag.LOOK);

        Assertions.assertTrue(set.hasElement(Goal.Flag.MOVE));
        Assertions.assertTrue(set.hasElement(Goal.Flag.LOOK));

        Set<Goal.Flag> flags = ChipUtil1_18_R1.getFlags(set.getBackingSet());
        Assertions.assertTrue(flags.contains(Goal.Flag.MOVE));
        Assertions.assertTrue(flags.contains(Goal.Flag.LOOK));
    }
    
}