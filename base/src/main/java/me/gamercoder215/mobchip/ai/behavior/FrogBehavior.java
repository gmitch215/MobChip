package me.gamercoder215.mobchip.ai.behavior;

import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

/**
 * Represents Behaviors for a Frog
 */
public interface FrogBehavior extends CreatureBehavior {

    /**
     * Makes this frog shoot its tongue.
     * @param tongueSound Sound to make when tongue is shot
     * @param eatSound Sound to make when a frog eats something
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult shootTongue(Sound tongueSound, Sound eatSound);

    /**
     * Makes this frog Croak.
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult croak();
}
