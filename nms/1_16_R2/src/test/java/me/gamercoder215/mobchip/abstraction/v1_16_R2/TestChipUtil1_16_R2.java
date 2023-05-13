package me.gamercoder215.mobchip.abstraction.v1_16_R2;

import me.gamercoder215.mobchip.ai.gossip.GossipType;
import net.minecraft.server.v1_16_R2.EnumDifficulty;
import net.minecraft.server.v1_16_R2.PathfinderGoal;
import net.minecraft.server.v1_16_R2.ReputationType;
import org.bukkit.Difficulty;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class TestChipUtil1_16_R2 {

    @Test
    @DisplayName("Test Bukkit-NMS Conversion")
    public void testNMSConversion() {
        // Classes
        ChipUtil1_16_R2.toNMS(LivingEntity.class);
        ChipUtil1_16_R2.toNMS(Mob.class);
        ChipUtil1_16_R2.toNMS(AbstractVillager.class);
        ChipUtil1_16_R2.toNMS(AbstractHorse.class);

        ChipUtil1_16_R2.toNMS(IronGolem.class);
        ChipUtil1_16_R2.toNMS(Cow.class);

        ChipUtil1_16_R2.toNMS(Player.class);
        ChipUtil1_16_R2.toNMS(HumanEntity.class);

        // Other
        for (Difficulty d : Difficulty.values()) Assertions.assertNotNull(ChipUtil1_16_R2.toNMS(d));
        for (EntityDamageEvent.DamageCause c : EntityDamageEvent.DamageCause.values()) Assertions.assertNotNull(ChipUtil1_16_R2.toNMS(c));
        for (GossipType t : GossipType.values()) Assertions.assertNotNull(ChipUtil1_16_R2.toNMS(t));

        Assertions.assertNotNull(ChipUtil1_16_R2.toNMS(m -> m.damage(2)));
    }

    @Test
    @DisplayName("Test NMS-Bukkit Conversion")
    public void testBukkitConversion() {
        // Other
        for (EnumDifficulty d : EnumDifficulty.values()) Assertions.assertNotNull(ChipUtil1_16_R2.fromNMS(d));
        for (ReputationType t : ReputationType.values()) Assertions.assertNotNull(ChipUtil1_16_R2.fromNMS(t));
    }

    @Test
    @DisplayName("Test ChipUtil1_16_R2#getFlags")
    public void testGetFlags() {
        OptimizedSmallEnumSet1_16_R2<PathfinderGoal.Type> set = new OptimizedSmallEnumSet1_16_R2<>(PathfinderGoal.Type.class);
        set.addUnchecked(PathfinderGoal.Type.MOVE);
        set.addUnchecked(PathfinderGoal.Type.LOOK);

        Assertions.assertTrue(set.hasElement(PathfinderGoal.Type.MOVE));
        Assertions.assertTrue(set.hasElement(PathfinderGoal.Type.LOOK));

        Set<PathfinderGoal.Type> flags = ChipUtil1_16_R2.getFlags(set.getBackingSet());
        Assertions.assertTrue(flags.contains(PathfinderGoal.Type.MOVE));
        Assertions.assertTrue(flags.contains(PathfinderGoal.Type.LOOK));
    }
    
}
