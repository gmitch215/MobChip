package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.Sound;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * Represents a Pathfinder for a Mob to use an Item
 */
public final class PathfinderUseItem extends Pathfinder implements Conditional<Mob> {
    
    private Sound finishSound;
    private ItemStack item;
    private Predicate<Mob> requirements;

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
    
    @Override
    @NotNull
    public Predicate<Mob> getCondition() {
        return this.requirements;
    }

    /**
     * @throws IllegalArgumentException if requirements are null
     */
    @Override
    public void setCondition(@NotNull Predicate<Mob> req) throws IllegalArgumentException {
        if (req == null) throw new IllegalArgumentException("Requirements cannot be null");
        this.requirements = req;
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[0];
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalUseItem";
    }
}
