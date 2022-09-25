package me.gamercoder215.mobchip.ai.controller;

/**
 * Represents a Natural Moving Type.
 */
public enum NaturalMoveType {

    /**
     * A Natural Movement Type that represents the entity moving on its own.
     */
    SELF,
    /**
     * A Natural Movement Type that represents the entity moving when pushed by a player.
     */
    PLAYER,
    /**
     * A Natural Movement Type that represents the entity moving when pushed by a piston.
     */
    PISTON,
    /**
     * A Natural Movement Type that represents the entity moving when pushed by a shulker box.
     */
    SHULKER_BOX,
    /**
     * A Natural Movement Type that represents the entity moving when pushed with a Shulker Bullet.
     */
    SHULKER

}
