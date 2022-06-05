package me.gamercoder215.mobchip.ai.goal;

import java.lang.reflect.Field;
import java.util.function.Predicate;

import org.bukkit.Sound;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.goal.UseItemGoal;

/**
 * Represents a Pathfinder for a Mob to use an Item
 */
public final class PathfinderUseItem extends Pathfinder implements Conditional<Mob> {
    
    private Sound finishSound;
    private ItemStack item;
    private Predicate<Mob> requirements;

    /**
     * Constructs a PathfinderUseItem from a NMS UseItemGoal.
     * @param g Goal to use
     */
    @SuppressWarnings("unchecked")
    public PathfinderUseItem(@NotNull UseItemGoal<?> g) {
        super(Pathfinder.getEntity(g, "a"));

        this.item = ChipConversions.convertType(Pathfinder.getField(g, "b", net.minecraft.world.item.ItemStack.class));
        this.finishSound = ChipConversions.convertType(Pathfinder.getField(g, "d", SoundEvent.class));

        try {
            Field a = UseItemGoal.class.getDeclaredField("c");
            a.setAccessible(true);
            Predicate<net.minecraft.world.entity.Mob> p = (Predicate<net.minecraft.world.entity.Mob>) a.get(g);
            this.requirements = m -> p.test(ChipConversions.convertType(m));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a PathfinderUseItem with {@link Sound#ENTITY_PLAYER_BURP}.
     * @param m Mob to use
     * @param item ItemStack to use
     * @param requirements Requirements to use the item
     * @throws IllegalArgumentException if requirements or item are null
     */
    public PathfinderUseItem(@NotNull Mob m, @NotNull ItemStack item, @NotNull Predicate<Mob> requirements) throws IllegalArgumentException {
        this(m, item, requirements, null);
    }

    /**
     * Constructs a PathfinderUseItem.
     * @param m Mob to use
     * @param item ItemStack to use
     * @param requirements Requirements to use the item
     * @param finishSound Sound to use when finished
     * @throws IllegalArgumentException if requirements or item are null
     */
    public PathfinderUseItem(@NotNull Mob m, @NotNull ItemStack item, @NotNull Predicate<Mob> requirements, @Nullable Sound finishSound) throws IllegalArgumentException {
        super(m);

        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        if (requirements == null) throw new IllegalArgumentException("Requirements cannot be null");

        this.item = item;
        this.requirements = requirements;
        this.finishSound = finishSound == null ? Sound.ENTITY_PLAYER_BURP : finishSound;
    }

    /**
     * Gets the current sound used when the item is finished.
     * @return Sound used
     */
    @NotNull
    public Sound getFinishSound() {
        return this.finishSound;
    }

    /**
     * Sets the current sound used when the item is finished.
     * @param s Sound to use
     */
    public void setFinishSound(@Nullable Sound s) {
        this.finishSound = s == null ? Sound.ENTITY_PLAYER_BURP : s;
    }

    /**
     * Gets the current item that will be used.
     * @return ItemStack used
     */
    @NotNull
    public ItemStack getItem() {
        return this.item;
    }

    /**
     * Sets the current item that will be used.
     * @param i ItemStack to use
     * @throws IllegalArgumentException if item is null
     */
    public void setItem(@NotNull ItemStack i) throws IllegalArgumentException {
        if (i == null) throw new IllegalArgumentException("Item cannot be null");
        this.item = i;
    }
    
    @NotNull
    public Predicate<Mob> getCondition() {
        return this.requirements;
    }

    /**
     * @throws IllegalArgumentException if requirements are null
     */
    public void setCondition(@NotNull Predicate<Mob> req) throws IllegalArgumentException {
        if (req == null) throw new IllegalArgumentException("Requirements cannot be null");
        this.requirements = req;
    }

    @Override
    public UseItemGoal<?> getHandle() {
        return new UseItemGoal<>(nmsEntity, ChipConversions.convertType(item), ChipConversions.convertType(finishSound), m -> requirements.test(ChipConversions.convertType(m)));
    }

}
