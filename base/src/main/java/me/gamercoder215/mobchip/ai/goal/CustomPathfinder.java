package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

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
     * public Set PathfinderFlag> getFlags() {
     *    return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.JUMPING; }
     * }
     * </pre>
     *
     * @return Flags
     */
    @Override
    public abstract @NotNull PathfinderFlag[] getFlags();

    /**
     * Whether this Custom Pathfinder can start.
     * <br><br>
     * Any assignments to fields or checks should be in here. Return true for {@link #start()} to automatically run. 
     * <br><br>
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
     * Called on the first tick this Pathfinder starts, when {@link #canStart()} returns true.
     * <br><br>
     * Any actions that this Pathfinder will call <strong>first</strong> should be in here. This method will run automatically.
     * <br><br>
     * Example:
     * <pre>
     * // public class LavaFloat extends CustomPathfinder {
     *
     * // Beginning Movements
     * public void start() {
     *     // Get Entity Brain
     *     brain.getController().jump();
     *     brain.getController().strafe(1F, 0F);
     * }
     * </pre>
     */
    public abstract void start();

    /**
     * Called in between {@link #start()} and {@link #stop()}.
     * <br><br>
     * Any actions that this Pathfinder will call in between should be in here. This method will run automatically.
     * <br><br>
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
     * Called when this Pathfinder stops being used.
     * <br><br>
     * Any actions that this Pathfinder will call <strong>when it stops</strong> should be in here. This method will run automatically.
     */
    public void stop() {}

    /**
     * Whether this Pathfinder Goal can be interrupted. Default: true
     * @return true if Pathfinder can be interrupted, else false
     */
    public boolean canInterrupt() { return true; }

    /**
     * Whether this Pathfinder Goal will continue to be used.
     * <p>
     * Override this method if you have additional checks after the Pathfinder has started. By default, it will call {@link #canStart()}.
     * @return true if Pathfinder can continue to use, else false
     */
    public boolean canContinueToUse() { return canStart(); }

    /**
     * @deprecated Internal Use only
     */
    @Deprecated
    public String getInternalName() {
        return "CustomPathfinder";
    }

}
