package me.gamercoder215.mobchip.util;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@SuppressWarnings("ALL")
public final class MobChipUtil {

	public static Entity getById(int id) {
		for (World w : Bukkit.getWorlds()) {
			for (Entity en : w.getEntities()) {
				if (en.getEntityId() == id) return en;
			}
		}
		return null;
	}

	private static String getServerVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
	}

	public static ChipUtil getWrapper() {
		try {
			String pkg = ChipUtil.class.getPackage().getName();
			return (ChipUtil)Class.forName(pkg + ".ChipUtil" + getServerVersion()).getConstructor().newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Invalid Version: " + getServerVersion());
		}
	}

	// Static

	public static ItemStack convert(net.minecraft.world.item.ItemStack item) {
		return getWrapper().convert(item);
	}

	public static net.minecraft.world.item.ItemStack convert(ItemStack item) {
		return getWrapper().convert(item);
	}

	public static SoundEvent convert(Sound s) {
		return getWrapper().convert(s);
	}

	public static ServerPlayer convert(Player p) {
		return getWrapper().convert(p);
	}

	public static net.minecraft.world.entity.monster.hoglin.Hoglin convert(Hoglin p) {
		return getWrapper().convert(p);
	}

	public static net.minecraft.world.entity.monster.piglin.Piglin convert(Piglin p) {
		return getWrapper().convert(p);
	}

	public static AgeableMob convert(Ageable a) {
		return getWrapper().convert(a);
	}

	public static ServerLevel convert(World w) {
		return getWrapper().convert(w);
	}

	public static List<LivingEntity> convert(List<org.bukkit.entity.LivingEntity> list) {
		return getWrapper().convert(list);
	}

	public static net.minecraft.world.entity.Entity convert(Entity en) {
		return getWrapper().convert(en);
	}

	public static Mob convert(org.bukkit.entity.Mob m) {
		return getWrapper().convert(m);
	}

	public static LivingEntity convert(org.bukkit.entity.LivingEntity en) {
		return getWrapper().convert(en);
	}

	public static Entity convert(net.minecraft.world.entity.Entity en) { return getWrapper().convert(en); }

	public static PathfinderMob convert(Creature c) {
		return getWrapper().convert(c);
	}

	public static ResourceLocation convert(NamespacedKey key) {
		return getWrapper().convert(key);
	}

	public static RangedAttribute convert(Attribute a) {
		return getWrapper().convert(a);
	}

	public static DamageSource convert(EntityDamageEvent.DamageCause c) {
		return getWrapper().convert(c);
	}

	public static org.bukkit.entity.LivingEntity convert(LivingEntity en) {
		return getWrapper().convert(en);
	}

	public static org.bukkit.entity.Mob convert(Mob mob) {
		return getWrapper().convert(mob);
	}

	public static Creature convert(PathfinderMob mob) {
		return getWrapper().convert(mob);
	}

	public static EntityType<?> convert(org.bukkit.entity.EntityType type) {
		return getWrapper().convert(type);
	}

	public static BlockPos convert(Location loc) {
		return getWrapper().convert(loc);
	}

	public static Difficulty convert(org.bukkit.Difficulty d) {
		return getWrapper().convert(d);
	}

	public static org.bukkit.Difficulty convert(Difficulty d) {
		return getWrapper().convert(d);
	}

	public static World convert(Level l) {
		return getWrapper().convert(l);
	}

	public static Location convert(World w, BlockPos pos) {
		return getWrapper().convert(w, pos);
	}

	public static <T extends Entity> Class<? extends T> toBukkitClass(Class<T> bukkit, Class<? extends net.minecraft.world.entity.Entity> clazz) {
		return getWrapper().toBukkitClass(bukkit, clazz);
	}

	public static Class<? extends net.minecraft.world.entity.Entity> toNMSClass(Class<? extends Entity> clazz) {
		return getWrapper().toNMSClass(clazz);
	}

	public static Class<? extends LivingEntity> toLivingNMSClass(Class<? extends org.bukkit.entity.LivingEntity> clazz) {
		return getWrapper().toLivingNMSClass(clazz);
	}

	public static Sound convert(SoundEvent s) {
		return getWrapper().convert(s);
	}

	public static Player convert(net.minecraft.world.entity.player.Player p) { return getWrapper().convert(p); }

	public static Ageable convert(AgeableMob mob) { return getWrapper().convert(mob); }

	public static EntityDamageEvent.DamageCause convert(DamageSource s) { return getWrapper().convert(s); }

	public static Hoglin convert(net.minecraft.world.entity.monster.hoglin.Hoglin h) { return getWrapper().convert(h); }

	public static Piglin convert(net.minecraft.world.entity.monster.piglin.AbstractPiglin p) { return getWrapper().convert(p); }

	public static Item convert(ItemEntity en) { return getWrapper().convert(en); }

	public static ItemEntity convert(Item i) { return getWrapper().convert(i); }
	public static boolean exists(NamespacedKey key, Registry<?> registry) { return getWrapper().exists(key, registry); }

}
