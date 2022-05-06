package me.gamercoder215.mobchip.ai.goal;

import java.util.EnumSet;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

/**
 * Represents an extendible Pathfinder for creating Custom Pathfinders.
 */
public abstract class CustomPathfinder extends Pathfinder {

    /**
     * Constructs a CustomPathfinder.
     * @param m Mob to use
     */
    protected CustomPathfinder(@NotNull Mob m) {
        super(m);
    }

    /**
     * Pathfinder Flags for this Custom Pathfinder
     */
    public static enum PathfinderFlag {
        MOVEMENT(Flag.MOVE),
        TARGETING(Flag.TARGET),
        LOOKING(Flag.LOOK),
        JUMPING(Flag.JUMP)
        ;

        private final Flag type;

        private PathfinderFlag(Flag type) {
            this.type = type;
        }  

        public final Flag getHandle() {
            return this.type;
        }
    }

    /**
     * The flags of this Custom Pathfinder. Can be null for no flags.
     * <p>
     * Example:
     * <pre>
     * // public class LavaFloat extends CustomPathfinder {
     * 
     * // This Pathfinder Goal will involve movement and jumping, so we have to add it as a flag
     * public PathfinderFlag[] getFlags() {
     *    return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.JUMPING; }
     * }
     * </pre>
     */
    public abstract PathfinderFlag[] getFlags();

    /**
     * Whether or not this Custom Pathfinder can start.
     * <p>
     * Any assignments to fields or checks should be in here. Return true for {@link #start()} to automatically run. 
     * <p>
     * Example:
     * <pre>
     * // public class LavaFloat extends CustomPathfinder {
     * import org.bukkit.Material;
     * 
     * public boolean canStart() {
     *     return this.entity.getLocation().getBlock().getType() == Material.LAVA; 
     * } 
     *
     * </pre>
     */
    public abstract boolean canStart();

    /**
     * Called when {@link #canStart()} returns true.
     * <p>
     * Any actions that this Pathfinder will call should be in here. This method will run automatically.
     * <p>
     * Example:
     * <pre>
     * // public class LavaFloat extends CustomPathfinder {
     * 
     * public void start() {
     *     // Get Entity Brain
     *     brain.getController().jump();
     *     brain.getController().strafe(1F, 0F);
     * }
     * </pre>
     */
    public abstract void start();

    /**
     * Called when {@link #canStart()} returns false.
     * <p>
     * Any actions that this Pathfinder will call <strong>when it can't run</strong> should be in here. This method will run automatically.
     * <p>
     * Example:
     * <pre>
     * // public class LavaFloat extends CustomPathfinder {
     * 
     * // No action for this is needed, so this can be blank
     * public void tick() {}
     * </pre>
     */
    public abstract void tick();

    /**
     * Whether or not this Pathfinder Goal is interruptable. Default: true
     * @return true if interruptable, else false
     */
    public boolean canInterrupt() { return true; };

    /**
     * Whether or not this Pathfinder Goal will update every tick. Default: false
     * <p>
     * The Goal will usually update every 2-5 ticks. Returning true is not recommended.
     * @return true if updating every tick, else false
     */
    public boolean updateEveryTick() { return false; };

    /**
     * Whether or not this Pathfinder Goal will continue to be used.
     * <p>
     * Override this method if you have additional checks after the Pathfinder has started. By default, it will call {@link #canStart()}.
     * @return true if can continue to use, else false
     */
    public boolean canContinueToUse() { return canStart(); };

    public final Goal getHandle() {
        CustomPathfinder inst = this;

        return new Goal() {
            {
                if (inst.getFlags() != null) {
                    EnumSet<Flag> set = EnumSet.noneOf(Flag.class);
                    for (PathfinderFlag f : inst.getFlags()) if (f != null) set.add(f.type);
                    this.setFlags(set);
                }                
            }

            @Override
            public boolean canUse() {
                return inst.canStart();
            }

            @Override
            public void tick() {
                inst.tick();
            }

            @Override
            public boolean requiresUpdateEveryTick() {
                return inst.updateEveryTick();
            }

            public boolean canContinueToUse() {
                return inst.canContinueToUse();
            }

            @Override
            public boolean isInterruptable() {
                return inst.canInterrupt();
            }

            @Override
            public void start() {
                inst.start();
            }            
        };
    }

}
