package me.gamercoder215.mobchip.util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

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
// TODO	
//	public static Class<? extends Entity> toBukkitClass(Class<? extends net.minecraft.world.entity.Entity> clazz) {
//		
//	}
	
	public static Entity getById(int id) {
		for (World w : Bukkit.getWorlds()) {
			for (Entity en : w.getEntities()) {
				if (en.getEntityId() == id) return en;
			}
		}
		
		return null;
	}
}
