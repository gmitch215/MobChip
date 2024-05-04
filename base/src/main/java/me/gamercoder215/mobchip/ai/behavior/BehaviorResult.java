package me.gamercoder215.mobchip.ai.behavior;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a Behavior Result
 */
public abstract class BehaviorResult {

    /**
     * Represents a stopped BehaviorResult.
     */
    public static final BehaviorResult STOPPED = new BehaviorResult() {
        @Override
        public @NotNull Status getStatus() {
            return Status.STOPPED;
        }
        @Override
        public void stop() {
            // Do nothing
        }
    };

    /**
     * Constructs an Abstract Behavior Result.
     */
    protected BehaviorResult() {}

    /**
     * Fetches the Status of this BehaviorResult.
     * @return Behavior Status
     */
    @NotNull
    public abstract Status getStatus();

    /**
     * Forces this Behavior to stop.
     */
    public abstract void stop();

    /**
     * Represents a Behavior Status
     */
    public enum Status {
        /**
         * Represents that this Behavior has stopped running.
         */
        STOPPED,
        /**
         * Represents that this Behavior is running.
         */
        RUNNING;

        Status() {}
    }

}
