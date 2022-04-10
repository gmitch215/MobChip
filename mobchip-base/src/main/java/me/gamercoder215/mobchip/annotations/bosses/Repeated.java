package me.gamercoder215.mobchip.annotations.bosses;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Represents a Boss's Repeated Attack.
 * <p>
 * A "Repeated" Attack means that a boss will execute this method every x Minecraft Ticks as soon as it is spawned (see {@link #startOnSpawn()}
 * <strong>There are no restrictions to this value; please use this wisely.</strong>
 * <p>
 * The attack will automatically end when the entity dies.
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Repeated {
	
	/**
	 * Whether or not this attack should start as soon as this mob spawns.
	 * <p>
	 * If false, the plugin will wait {@link #startInterval()} ticks for the attack to begin.
	 * @return true if start on spawn, else false
	 */
	boolean startSpawn() default true;
	
	/**
	 * The start interval of this repeated attack.
	 * <p>
	 * This will be ignored if {@link #startSpawn()} is set to true.
	 * @return amount of ticks to wait until repeated attack begins
	 * @see #startSpawn()
	 */
	long startInterval() default 0;
	
	/**
	 * How often this attack should be repeated.
	 * @return amount of ticks to wait inbetween attacks
	 */
	long value();
	
}
