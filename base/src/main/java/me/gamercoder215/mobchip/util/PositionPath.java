package me.gamercoder215.mobchip.util;

import com.google.common.collect.ImmutableList;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a Path, or a set of {@link Position} objects to get to a final destination.
 */
public final class PositionPath {

    private final List<Position> positions = new ArrayList<>();
    private final Location targetLocation;

    private int nextPositionIndex;

    /**
     * Creates a new Path.
     * @param positions The positions to get to the target position.
     * @param targetLocation Final Target Position
     * @throws IllegalArgumentException if targetLocation is null
     */
    public PositionPath(@Nullable Iterable<? extends Position> positions, @NotNull Location targetLocation) throws IllegalArgumentException {
        if (targetLocation == null) throw new IllegalArgumentException("Target Position cannot be null!");

        if (positions != null)
            this.positions.addAll(ImmutableList.copyOf(positions)
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));

        this.targetLocation = targetLocation;
    }

    /**
     * Fetches the world this Path takes place in.
     * @return World this Path takes place in
     */
    @Nullable
    public World getWorld() {
        return targetLocation.getWorld();
    }

    /**
     * Fetches the index of the next position.
     * @return Index of the next position
     */
    public int getNextPositionIndex() {
        return nextPositionIndex;
    }

    /**
     * Advances this Path to the next Position.
     * @return Next Position
     * @throws IllegalStateException if there are no more positions
     */
    public Position advance() throws IllegalStateException {
        if (isReached()) throw new IllegalStateException("Path has no more positions!");

        nextPositionIndex++;
        return positions.get(nextPositionIndex);
    }

    /**
     * Adds a position to the end of this Path.
     * @param position Position to add
     * @throws IllegalArgumentException if position is null
     */
    public void addPosition(@NotNull Position position) throws IllegalArgumentException {
        if (position == null) throw new IllegalArgumentException("Position cannot be null!");

        positions.add(position);
    }

    /**
     * Adds a position.
     * @param position Position to add
     * @param index Index to add position at
     * @throws IllegalArgumentException if position is null or index is negative
     */
    public void addPosition(@NotNull Position position, int index) throws IllegalArgumentException {
        if (index < 0) throw new IllegalArgumentException("Index cannot be negative!");
        if (position == null) throw new IllegalArgumentException("Position cannot be null!");

        positions.add(index, position);
    }

    /**
     * Fetches the Position at the end of the path, right before {@link #getTargetLocation()}.
     * @return End Position
     */
    @Nullable
    public Position getEndPosition() {
        return positions.get(positions.size() - 1);
    }

    /**
     * Fetches the position at this Path's current index.
     * @param index Index to get
     * @return Position at index
     */
    @Nullable
    public Position getPosition(int index) {
        return positions.get(index);
    }

    /**
     * Fetches the current position at this Path's current index.
     * @return Current Position
     */
    @NotNull
    public Position getCurrentPosition() {
        return positions.get(nextPositionIndex);
    }

    /**
     * Fetches all the positions in this Path.
     * @return Path Positions
     */
    @NotNull
    public List<Position> getPositions() {
        return ImmutableList.copyOf(positions);
    }

    /**
     * Fetches the final target position for this Path.
     * @return Target Position
     */
    @NotNull
    public Location getTargetLocation() {
        return targetLocation;
    }

    /**
     * Whether this Path has reached its end position.
     * @return true if reached, false otherwise
     */
    public boolean isReached() {
        return nextPositionIndex >= positions.size();
    }

    /**
     * Whether this Path has not started ({@link #getNextPositionIndex()} is 0).
     * @return true if not started, false otherwise
     */
    public boolean notStarted() {
        return nextPositionIndex == 0;
    }
}
