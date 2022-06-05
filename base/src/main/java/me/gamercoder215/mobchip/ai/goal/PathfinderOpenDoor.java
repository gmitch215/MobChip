package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.ai.goal.OpenDoorGoal;

/**
 * Represents a Pathfinder for an entity to open a door
 */
public final class PathfinderOpenDoor extends Pathfinder {
    
    private boolean close;

    /**
     * Constructs a PathfinderOpenDoor from a NMS OpenDoorGoal.
     * @param g Goal to use
     */
    public PathfinderOpenDoor(@NotNull OpenDoorGoal g) {
        super(Pathfinder.getEntity(g, "d"));

        this.close = Pathfinder.getBoolean(g, "a");
    }

    /**
     * Constructs a PathfinderOpenDoor that allows closing doors.
     * @param m Mob to use
     */
    public PathfinderOpenDoor(@NotNull Mob m) {
        this(m, true);
    }
    
    /**
     * Constructs a PathfinderOpenDoor.
     * @param m Mob to use
     * @param close Whether this entity should close the door
     */
    public PathfinderOpenDoor(@NotNull Mob m, boolean close) {
        super(m);
        
        this.close = close;
    }

    /**
     * Whether this entity should close the door.
     * @return true if close, else false
     */
    public boolean mustClose() {
        return this.close;
    }

    /**
     * Sets whether this entity should close the door.
     * @param close true if close, else false
     */
    public void setMustClose(boolean close) {
        this.close = close;
    }
    
    @Override
    public OpenDoorGoal getHandle() {
        return new OpenDoorGoal(nmsEntity, close);
    }

    

}
