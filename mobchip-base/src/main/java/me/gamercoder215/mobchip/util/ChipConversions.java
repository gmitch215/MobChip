package me.gamercoder215.mobchip.util;

import org.bukkit.entity.Entity;

import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * Utility class for Converting Bukkit Classes to NMS Classes and vice-versa
 */
public final class ChipConversions {

	private ChipConversions() {};
		
	/**
	 * Converts a Bukkit Class into a NMS Class.
	 * @param clazz Bukkit class
	 * @return Converted NMS Class
	 */
	public static Class<? extends net.minecraft.world.entity.Entity> toNMSClass(Class<? extends Entity> clazz) {
		try {
			return clazz.getDeclaredField("entity").getType().asSubclass(net.minecraft.world.entity.Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Converts a NMS Goal Class into a Mob Chip Pathfinder Class.
	 * @param g Goal Class to convert
	 * @return Converted Mob Chip Pathfinder Class
	 */
	public static Class<? extends Pathfinder> wrap(Class<? extends Goal> g) {
		if (g.getSimpleName().equals("Goal")) return Pathfinder.class;
		
		try {
			if (g.getPackageName().contains("ai.goal.target")) {
				return Class.forName("me.gamercoder215.ai.goal.target" + g.getSimpleName()).asSubclass(Pathfinder.class);
			} else {
				return Class.forName("me.gamercoder215.ai.goal" + g.getSimpleName()).asSubclass(Pathfinder.class);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
}
