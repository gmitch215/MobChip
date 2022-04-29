package me.gamercoder215.mobchip.bosses.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents an Attack that will be Repeated until the entity dies.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Repeatable {
    
    /**
     * The delay of this Repeatable Attack. Default: 0
     * @return Delay of Attack
     */
    long delay() default 0;

    /**
     * The Name of the Plugin.
     * @return Name of Plugin
     */
    String plugin();

    /**
     * Interval of this Repeatable Attack.
     * @return Interval of Attack
     */
    long interval();

}
