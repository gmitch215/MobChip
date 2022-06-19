package me.gamercoder215.mobchip.abstraction;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_19_R1.CraftSound;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.attribute.CraftAttributeMap;
import org.bukkit.craftbukkit.v1_19_R1.entity.*;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftNamespacedKey;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ChipUtil1_19_R1 implements ChipUtil {

    @Override
    public ItemStack convert(net.minecraft.world.item.ItemStack item) {
        return CraftItemStack.asBukkitCopy(item);
    }

    @Override
    public net.minecraft.world.item.ItemStack convert(ItemStack item) {
        return CraftItemStack.asNMSCopy(item);
    }

    @Override
    public SoundEvent convert(Sound s) {
        return CraftSound.getSoundEffect(s);
    }

    @Override
    public Sound convert(SoundEvent s) {
        return CraftSound.getBukkit(s);
    }

    @Override
    public ServerPlayer convert(Player p) {
        return ((CraftPlayer) p).getHandle();
    }

    @Override
    public Hoglin convert(org.bukkit.entity.Hoglin p) {
        return ((CraftHoglin) p).getHandle();
    }

    @Override
    public Piglin convert(org.bukkit.entity.Piglin p) {
        return ((CraftPiglin) p).getHandle();
    }

    @Override
    public AgeableMob convert(Ageable a) {
        return ((CraftAgeable) a).getHandle();
    }

    @Override
    public ServerLevel convert(World w) {
        return ((CraftWorld) w).getHandle();
    }

    @Override
    public List<LivingEntity> convert(List<org.bukkit.entity.LivingEntity> list) {
        List<LivingEntity> l = new ArrayList<>();
        list.forEach(en -> l.add(convert(en)));
        return l;
    }

    @Override
    public Entity convert(org.bukkit.entity.Entity en) {
        return ((CraftEntity) en).getHandle();
    }

    @Override
    public Mob convert(org.bukkit.entity.Mob m) {
        return ((CraftMob) m).getHandle();
    }

    @Override
    public LivingEntity convert(org.bukkit.entity.LivingEntity en) {
        return ((CraftLivingEntity) en).getHandle();
    }

    @Override
    public PathfinderMob convert(Creature c) {
        return ((CraftCreature) c).getHandle();
    }

    @Override
    public ResourceLocation convert(NamespacedKey key) {
        return CraftNamespacedKey.toMinecraft(key);
    }

    @Override
    public RangedAttribute convert(Attribute a) {
        return CraftAttributeMap.toMinecraft(a) instanceof RangedAttribute att ? att : null;
    }
}
