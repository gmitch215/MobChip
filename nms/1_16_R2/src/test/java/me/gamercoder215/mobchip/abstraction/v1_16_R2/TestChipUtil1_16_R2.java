package me.gamercoder215.mobchip.abstraction.v1_16_R2;

import me.gamercoder215.mobchip.ai.gossip.GossipType;
import net.minecraft.server.v1_16_R2.DispenserRegistry;
import net.minecraft.server.v1_16_R2.EnumDifficulty;
import net.minecraft.server.v1_16_R2.PathfinderGoal;
import net.minecraft.server.v1_16_R2.ReputationType;
import org.bukkit.Difficulty;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityDamageEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestChipUtil1_16_R2 {

    @BeforeAll
    public static void init() {
        DispenserRegistry.init();
    }

    @Test
    @DisplayName("Test Bukkit-NMS Conversion")
    public void testNMSConversion() {
        // Classes
        List<EntityType> livingTypes = Arrays.stream(EntityType.values())
                .filter(EntityType::isAlive)
                .filter(e -> Mob.class.isAssignableFrom(e.getEntityClass()))
                .collect(Collectors.toList());

        for (EntityType t : livingTypes)
            Assertions.assertNotNull(ChipUtil1_16_R2.toNMS(t.getEntityClass().asSubclass(Mob.class)));

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
