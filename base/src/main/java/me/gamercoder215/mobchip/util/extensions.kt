package me.gamercoder215.mobchip.util

// Position

/**
 * Adds each value to this [Position] by the specified [Position].
 * @return Position Result
 */
operator fun Position.plus(other: Position) = clone().add(other)

/**
 * Subtracts each value from this [Position] by the specified [Position].
 * @return Position Result
 */
operator fun Position.minus(other: Position) = clone().remove(other)

/**
 * Adds each value to this [Position] by the specified [Position]
 * @return this Position
 */
operator fun Position.plusAssign(other: Position) { add(other) }

/**
 * Subtracts each value from this [Position] by the specified [Position]
 * @return this Position
 */
operator fun Position.minusAssign(other: Position) { remove(other) }

/**
 * Increments each value to this [Position] by 1.
 * @return this Position
 */
operator fun Position.inc() = add(1, 1, 1)

/**
 * Decrements each value from this [Position] by 1.
 * @return this Position
 */
operator fun Position.dec() = remove(1, 1, 1)

// PositionPath

/**
 * Fetches the [Position] of this [PositionPath] at the specified index.
 * @return Position
 * @see PositionPath.getPosition
 */
operator fun PositionPath.get(index: Int) = getPosition(index)