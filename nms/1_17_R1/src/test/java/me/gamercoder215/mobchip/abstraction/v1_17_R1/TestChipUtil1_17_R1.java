package me.gamercoder215.mobchip.abstraction.v1_17_R1;

import me.gamercoder215.mobchip.ai.gossip.GossipType;
import net.minecraft.MinecraftVersion;
import net.minecraft.SharedConstants;
import net.minecraft.server.DispenserRegistry;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.gossip.ReputationType;
import org.bukkit.Difficulty;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TestChipUtil1_17_R1 {

    @BeforeAll
    public static void init() throws Exception {
        Constructor<MinecraftVersion> versionC = MinecraftVersion.class.getDeclaredConstructor();
        versionC.setAccessible(true);
        MinecraftVersion v = versionC.newInstance();
        SharedConstants.a(v);

        DispenserRegistry.init();
    }

    @Test
    @DisplayName("Test Bukkit-NMS Conversion")
    public void testNMSConversion() {
        // Classes
        List<EntityType> livingTypes = Arrays.stream(EntityType.values())
                .filter(EntityType::isAlive)
                .filter(e -> Mob.class.isAssignableFrom(e.getEntityClass()))
                .toList();

        for (EntityType t : livingTypes)
            Assertions.assertNotNull(ChipUtil1_17_R1.toNMS(t.getEntityClass().asSubclass(Mob.class)));

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
