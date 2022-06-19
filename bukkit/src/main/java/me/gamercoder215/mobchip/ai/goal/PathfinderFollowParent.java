package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.entity.Animals;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a baby version of a mob to follow an adult version of a mob
 */
public final class PathfinderFollowParent extends Pathfinder implements SpeedModifier {

    private double speedMod;

    /**
     * Constructs a PathfinderFollowParent from a NMS FollowParentGoal.
     * @param g NMS Goal to use
     */
    public PathfinderFollowParent(@NotNull FollowParentGoal g) {
        super(Pathfinder.getEntity(g, "d"));

        this.speedMod = Pathfinder.getDouble(g, "f");
    }

    /**
     * Creates a PathfinderFollowParent with no speed modifier.
     * @param m Animal to use
     */
    public PathfinderFollowParent(@NotNull Animals m) {
        this(m, 1);
    }

    /**
     * Creates a PathfinderFollowParent.
     * @param m Animal to use
     * @param speedMod Speed Modifier while following
     */
    public PathfinderFollowParent(@NotNull Animals m, double speedMod) {
        super(m);

        this.speedMod = speedMod;
    }

    @Override
    public FollowParentGoal getHandle() {
        return new FollowParentGoal((Animal) nmsEntity, speedMod);
    }

    @Override
    public double getSpeedModifier() {
        return this.speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) {
        this.speedMod = mod;
    }
    
}