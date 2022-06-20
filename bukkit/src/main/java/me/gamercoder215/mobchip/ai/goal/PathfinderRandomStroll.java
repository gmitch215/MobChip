package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * Represents a Pathfinder for Creatures to randomly stroll
 */
public class PathfinderRandomStroll extends Pathfinder implements SpeedModifier {
    
    private double speedMod;

    /**
     * Constructs a PathfinderRandomStroll from a NMS RandomStrollGoal.
     * @param g NMS Goal to use
     */
    public PathfinderRandomStroll(@NotNull RandomStrollGoal g) {
        super(Pathfinder.getCreature(g, "b"));

        try {
            Field a = RandomStrollGoal.class.getDeclaredField("f");
            a.setAccessible(true);
            this.speedMod = a.getDouble(g);
        } catch (Exception e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }

    /**
     * Constructs a PathfinderRandomStroll with no speed modifier.
     * @param c Creature to use
     */
    public PathfinderRandomStroll(@NotNull Creature c) {
        this(c, 1);
    }

    /**
     * Constructs a PathfinderRandomStroll.
     * @param c Creature to use
     * @param speedMod Speed Modifier while strolling
     */
    public PathfinderRandomStroll(@NotNull Creature c, double speedMod) {
        super(c);

        this.speedMod = speedMod;
    }

    @Override
    public double getSpeedModifier() {
        return this.speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) {
        this.speedMod = mod;
    }

    @Override
    public RandomStrollGoal getHandle() {
        return new RandomStrollGoal((PathfinderMob) nmsEntity, speedMod);
    }

}
