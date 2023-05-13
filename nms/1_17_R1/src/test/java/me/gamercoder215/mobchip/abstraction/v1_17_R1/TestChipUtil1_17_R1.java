package me.gamercoder215.mobchip.abstraction.v1_17_R1;

import me.gamercoder215.mobchip.ai.gossip.GossipType;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.gossip.ReputationType;
import org.bukkit.Difficulty;
import org.bukkit.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class TestChipUtil1_17_R1 {

    @Test
    @DisplayName("Test Bukkit-NMS Conversion")
    public void testNMSConversion() {
        // Classes
        ChipUtil1_17_R1.toNMS(LivingEntity.class);
        ChipUtil1_17_R1.toNMS(Mob.class);
        ChipUtil1_17_R1.toNMS(AbstractVillager.class);
        ChipUtil1_17_R1.toNMS(AbstractHorse.class);

        ChipUtil1_17_R1.toNMS(IronGolem.class);
        ChipUtil1_17_R1.toNMS(Chicken.class);
        ChipUtil1_17_R1.toNMS(Cow.class);

        ChipUtil1_17_R1.toNMS(Player.class);
        ChipUtil1_17_R1.toNMS(HumanEntity.class);

        // Other
        for (Difficulty d : Difficulty.values()) Assertions.assertNotNull(ChipUtil1_17_R1.toNMS(d));
        for (GossipType t : GossipType.values()) Assertions.assertNotNull(ChipUtil1_17_R1.toNMS(t));

        Assertions.assertNotNull(ChipUtil1_17_R1.toNMS(m -> m.damage(2)));
    }

    @Test
    @DisplayName("Test NMS-Bukkit Conversion")
    public void testBukkitConversion() {
        // Other
        for (EnumDifficulty d : EnumDifficulty.values()) Assertions.assertNotNull(ChipUtil1_17_R1.fromNMS(d));
        for (ReputationType t : ReputationType.values()) Assertions.assertNotNull(ChipUtil1_17_R1.fromNMS(t));
    }

    @Test
    @DisplayName("Test ChipUtil1_17_R1#getFlags")
    public void testGetFlags() {
        OptimizedSmallEnumSet1_17_R1<PathfinderGoal.Type> set = new OptimizedSmallEnumSet1_17_R1<>(PathfinderGoal.Type.class);
        set.addUnchecked(PathfinderGoal.Type.a);
        set.addUnchecked(PathfinderGoal.Type.b);

        Assertions.assertTrue(set.hasElement(PathfinderGoal.Type.a));
        Assertions.assertTrue(set.hasElement(PathfinderGoal.Type.b));

        Set<PathfinderGoal.Type> flags = ChipUtil1_17_R1.getFlags(set.getBackingSet());
        Assertions.assertTrue(flags.contains(PathfinderGoal.Type.a));
        Assertions.assertTrue(flags.contains(PathfinderGoal.Type.b));
    }
    
}
