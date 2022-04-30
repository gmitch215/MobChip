package me.gamercoder215.mobchip.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Lifecycle;

import me.gamercoder215.mobchip.attributes.Attribute;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

/**
 * Represents the Registry for things like Attributes 
 */
public final class ChipRegistry {
	
	/**
	 * Represents a Key in the registry
	 */
	public static final class RegistryKey {
		
		private final String namespace;
		private final String value;
		
		/**
		 * Constructs a RegistryKey from a normal namespace and value.
		 * @param namespace String Namespace
		 * @param value String value
		 */
		public RegistryKey(@NotNull String namespace, @NotNull String value) {
			if (namespace == null) throw new IllegalArgumentException("Namespace is null");
			if (value == null) throw new IllegalArgumentException("Value is null");
			this.namespace = namespace;
			this.value = value;
		}
		
		/**
		 * Constructs a RegistryKey from a Plugin and a value.
		 * @param plugin Plugin to use 
		 * @param value Value of Key
		 */
		public RegistryKey(@NotNull Plugin plugin, @NotNull String value) {
			this(new NamespacedKey(plugin, value));
		}
		
		/**
		 * Constructs a RegistryKey from a Bukkit NamespacedKey.
		 * @param key NamespacedKey to construct
		 */
		public RegistryKey(@NotNull NamespacedKey key) {
			this(key.getNamespace(), key.getKey());
		}
		
		/**
		 * Converts this RegistryKey to a NMS ResourceLocation.
		 * @return Converted ResourceLocation
		 */
		@NotNull
		public ResourceLocation toResourceLocation() {
			return new ResourceLocation(namespace, value);
		}
		
		/**
		 * Get the Namespace of this RegistryKey.
		 * @return found namespace
		 */
		@NotNull
		public final String getNamespace() {
			return this.namespace;
		}
		
		/**
		 * Constructs a RegistryKey in the Minecraft Namespace.
		 * @param value Value to set
		 * @return Created RegistryKey
		 */
		@NotNull
		public static final RegistryKey minecraft(@NotNull String value) {
			return new RegistryKey("minecraft", value);
		}
		

		/**
		 * Get the Value of this RegistryKey.
		 * @return found value
		 */
		@NotNull
		public final String getValue() {
			return this.value;
		}
		
		public boolean equals(@Nullable Object o) {
			if (o == null) return false;
			if (!(o instanceof RegistryKey key)) return false;
			
			return key.namespace.equals(this.namespace) && key.value.equals(this.value);
		}
		
		public int hashCode() {
		    int hash = 7;
		    hash = 31 * hash + (namespace.hashCode());
		    hash = 31 * hash + (value.hashCode());
		    return hash;
		}
		
	}
	
	private ChipRegistry() {}
	
	private static List<Attribute> registered = new ArrayList<>();
	
	private static void changeRegistryLock(boolean isLocked) {
		DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        MappedRegistry<net.minecraft.world.entity.ai.attributes.Attribute> materials = (MappedRegistry<net.minecraft.world.entity.ai.attributes.Attribute>) server.registryAccess().ownedRegistryOrThrow(Registry.ATTRIBUTE_REGISTRY);
        try {
	        Field isFrozen = materials.getClass().getDeclaredField("bL");
	        isFrozen.setAccessible(true);
	        isFrozen.set(materials, isLocked);  
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	private static <T> WritableRegistry<T> getRegistry(ResourceKey<Registry<T>> key) {
		return (WritableRegistry<T>) ((CraftServer) Bukkit.getServer()).getServer().registryAccess().ownedRegistryOrThrow(key);
	}
	
	/**
	 * Registeres an Attribute to the Registry.
	 * @param a Attribute to register.
	 * @throws IllegalArgumentException if Attribute is null
	 * @throws UnsupportedOperationException if Attribute already exists
	 */
	public static void registerAttribute(@NotNull Attribute a) throws IllegalArgumentException, UnsupportedOperationException {
		if (a == null) throw new IllegalArgumentException("Null attribute");
		
		if (registered.contains(a)) throw new UnsupportedOperationException("Attribute already exists");
		
		for (Attribute att : registered) {
			if (att.getId().equals(a.getId())) throw new UnsupportedOperationException("Attribute ID already exists");
		}
		
		// Registry
		changeRegistryLock(false);
		registered.add(a);
		
		WritableRegistry<net.minecraft.world.entity.ai.attributes.Attribute> registry = getRegistry(Registry.ATTRIBUTE_REGISTRY);
		
		RangedAttribute nmsAttribute = new RangedAttribute("attribute.name." + a.getId().value, a.getDefaultValue(), a.getMinValue(), a.getMaxValue());
		nmsAttribute.setSyncable(true);
		
		registry.register(ResourceKey.create(Registry.ATTRIBUTE_REGISTRY, a.getId().toResourceLocation()), nmsAttribute, Lifecycle.stable());
		
		changeRegistryLock(true);
	}
	
}
