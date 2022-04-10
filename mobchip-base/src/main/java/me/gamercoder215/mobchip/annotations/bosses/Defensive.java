package me.gamercoder215.mobchip.annotations.bosses;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Represents a Boss's Defensive Attack.
 * <p>
 * A "Defensive" attack is when a boss is damaged by an entity (see {@link #player()}).
 * This can be useful for thorns or potion effects.
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Defensive {
	
	/**
	 * Whether or not the boss needs to be damaged by a player in order to initiate the attack.
	 * <p>
	 * Default: true
	 * @return true if needed, else false
	 */
	boolean player() default true;
	
}
