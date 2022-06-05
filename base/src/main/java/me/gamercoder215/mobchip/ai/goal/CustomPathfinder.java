package me.gamercoder215.mobchip.ai.goal;

import java.util.EnumSet;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.ai.goal.Goal;

/**
 * Represents an extendable Pathfinder for creating Custom Pathfinders.
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
     * @return Flags
     */
    public abstract PathfinderFlag[] getFlags();

    /**
     * Whether this Custom Pathfinder can start.
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
     * @return true if Pathfinder can start, else false
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
     * Whether this Pathfinder Goal can be interrupted. Default: true
     * @return true if Pathfinder can be interrupted, else false
     */
    public boolean canInterrupt() { return true; }

    /**
     * Whether this Pathfinder Goal will update every tick. Default: false
     * <p>
     * The Goal will usually update every 2-5 ticks. Returning true is not recommended.
     * @return true if updating every tick, else false
     */
    public boolean updateEveryTick() { return false; }

    /**
     * Whether this Pathfinder Goal will continue to be used.
     * <p>
     * Override this method if you have additional checks after the Pathfinder has started. By default, it will call {@link #canStart()}.
     * @return true if Pathfinder can continue to use, else false
     */
    public boolean canContinueToUse() { return canStart(); }

    public final Goal getHandle() {
        CustomPathfinder inst = this;

        return new Goal() {
            {
                if (inst.getFlags() != null) {
                    EnumSet<Flag> set = EnumSet.noneOf(Flag.class);
                    for (PathfinderFlag f : inst.getFlags()) if (f != null) set.add(f.getHandle());
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
