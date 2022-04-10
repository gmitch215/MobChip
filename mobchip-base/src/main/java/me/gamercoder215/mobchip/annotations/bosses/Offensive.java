package me.gamercoder215.mobchip.annotations.bosses;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Represents a Boss's Offensive Attack.
 * <p>
 * An "Offensive" attack means that a boss has damaged an entity (see {@link #player()}) to complete an attack.
 * This can help with attacks that involve potion effects or knockback.
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Offensive {
	
	/**
	 * Whether or not the boss needs to damage a player to initiate the attack.
	 * <p>
	 * Default: true
	 * @return true if needed, else false
	 */
	boolean player() default true;
	
}
