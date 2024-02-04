package me.gamercoder215.mobchip.ai.controller

import org.bukkit.util.Vector

// EntityController

/**
 * Modifies the delta movement of the entity.
 * @param delta The delta movement to apply to the entity.
 */
fun EntityController.deltaMovement(delta: (Vector) -> Unit) {
    val vector = deltaMovement
    delta(vector)

    deltaMovement = vector
}

/**
 * Looks at a specific location.
 * @param target The location to look at.
 */
fun EntityController.lookAt(target: Triple<Double, Double, Double>) {
    val (x, y, z) = target
    lookAt(x, y, z)
}

/**
 * Moves the entity to a specific location.
 * @param target The location to move to.
 * @param speedModifier The speed modifier to apply to the movement.
 */
fun EntityController.moveTo(target: Triple<Double, Double, Double>, speedModifier: Double = 1.0) {
    val (x, y, z) = target
    moveTo(x, y, z, speedModifier)
}

/**
 * Performs a natural entity movement.
 * @param target The location to move to relative to the entity's current position.
 * @param type The type of natural movement to apply to the entity.
 */
fun EntityController.naturalMoveTo(target: Triple<Double, Double, Double>, type: NaturalMoveType) {
    val (x, y, z) = target
    naturalMoveTo(x, y, z, type)
}

/**
 * Strafes the entity.
 * @param target The forward and right strafe values to apply to the entity.
 */
fun EntityController.strafe(target: Pair<Float, Float>) {
    val (fwd, right) = target
    strafe(fwd, right)
}