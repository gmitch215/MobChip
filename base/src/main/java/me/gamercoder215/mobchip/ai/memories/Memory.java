package me.gamercoder215.mobchip.ai.memories;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an Entity Memory
 * @param <T> Type of Memory
 */
public interface Memory<T> extends Keyed {

    /**
     * Fetches the Class of this Memory.
     * @return Bukkit Class
     */
    @NotNull
    Class<T> getBukkitClass();


    /**
     * Represents a Memory Type of Walking Target
     *
     */
    final class WalkingTarget implements SpeedModifier {

        private final Location loc;

        private float speedMod;
        private int distance;

        /**
         * Constructs a WalkingTarget.
         * @param loc Location of target
         * @param speedMod Speed Modifier
         * @param distance Distance needed to stop walking
         */
        public WalkingTarget(@NotNull Location loc, float speedMod, int distance) {
            this.loc = loc;
            this.speedMod = speedMod;
            this.distance = distance;
        }

        /**
         * Constructs a WalkingTarget with the default speed modifier.
         * @param loc Location of target
         * @param distance Distance needed to stop walking
         */
        public WalkingTarget(@NotNull Location loc, int distance) {
            this(loc, DEFAULT_SPEED_MODIFIER, distance);
        }

        /**
         * Get the Location that this WalkingTarget is looking for.
         * @return Location looking for
         */
        @NotNull
        public Location getLocation() {
            return this.loc;
        }

        @Override
        public double getSpeedModifier() {
            return this.speedMod;
        }

        /**
         * Fetches the current closest enough distance.
         * @return Closest Enough Distance
         */
        public int getDistance() {
            return this.distance;
        }

        /**
         * Sets the current closest enough distance.
         * @param distance Distance to set
         */
        public void setDistance(int distance) {
            this.distance = distance;
        }

        /**
         * @throws IllegalArgumentException if greater than {@link Float#MAX_VALUE}
         */
        @Override
        public void setSpeedModifier(double mod) throws IllegalArgumentException {
            if (mod > Float.MAX_VALUE) throw new IllegalArgumentException("Float Speed Modifier");
            this.speedMod = (float) mod;
        }
    }

}
