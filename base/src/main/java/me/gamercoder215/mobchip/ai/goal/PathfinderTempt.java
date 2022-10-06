package me.gamercoder215.mobchip.ai.goal;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.Creature;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Pathfinder for the logic of this Creature getting tempted to move to another entity, for when they hold a specific item.
 */
public final class PathfinderTempt extends Pathfinder implements SpeedModifier {
    
    private Set<ItemStack> items;
    private double speedMod;

    /**
     * Constructs a PathfinderTempt with the default speed modifier.
     * @param m Creature to use
     * @param items ItemStacks to be tempted by
     * @throws IllegalArgumentException if item is null
     */
    public PathfinderTempt(@NotNull Creature m, @NotNull ItemStack... items) throws IllegalArgumentException {
        this(m, DEFAULT_SPEED_MODIFIER, items);
    }

    /**
     * Constructs a PathfinderTempt with an array of Items.
     * @param m Creature to use
     * @param speedMod Speed Modifier while moving to target holding item
     * @param items Array of ItemStacks to be tempted by
     * @throws IllegalArgumentException if items are null or empty
     */
    public PathfinderTempt(@NotNull Creature m, double speedMod, @NotNull ItemStack... items) throws IllegalArgumentException {
        this(m, speedMod, Arrays.asList(items));
    }

    /**
     * Constructs a PathfinderTempt.
     * @param m Creature to use
     * @param speedMod Speed Modifier while moving to target holding item
     * @param items Collection of ItemStacks to be tempted by
     * @throws IllegalArgumentException if items are null or empty
     */
    public PathfinderTempt(@NotNull Creature m, double speedMod, @NotNull Iterable<? extends ItemStack> items) throws IllegalArgumentException {
        super(m);
        if(items == null) throw new IllegalArgumentException("items cannot be null");
        if (!items.iterator().hasNext()) throw new IllegalArgumentException("items cannot be empty");

        this.items = new HashSet<>(ImmutableList.copyOf(items));
        this.speedMod = speedMod;
    }

    /**
     * Gets the Set of ItemStack belonging to this PathfinderTempt.
     * @return Set of ItemStacks found
     */
    @NotNull
    public Set<ItemStack> getItems() {
        return this.items;
    }

    /**
     * Adds an Array of ItemStacks to this PathfinderTempt.
     * @param items Items to add
     * @throws IllegalArgumentException if items are null
     */
    public void addItems(@NotNull ItemStack... items) throws IllegalArgumentException {
        addItems(Arrays.asList(items));
    }
    
    /**
     * Adds a Collection of ItemStacks to this PathfinderTempt.
     * @param items Items to add
     * @throws IllegalArgumentException if items are null
     */
    public void addItems(@NotNull Iterable<? extends ItemStack> items) throws IllegalArgumentException {
        if (items == null) throw new IllegalArgumentException("items cannot be null");
        this.items.addAll(ImmutableList.copyOf(items));
    }

    /**
     * Removes an Array of ItemStacks from this PathfinderTempt.
     * @param items Items to remove
     * @throws IllegalArgumentException if items are null
     */
    public void removeItems(@NotNull ItemStack... items) throws IllegalArgumentException {
        removeItems(Arrays.asList(items));
    }

    /**
     * Removes a Collection of ItemStacks from this PathfinderTempt.
     * @param items Items to remove
     * @throws IllegalArgumentException if items are null
     */
    public void removeItems(@NotNull Iterable<? extends ItemStack> items) throws IllegalArgumentException {
        if (items == null) throw new IllegalArgumentException("items cannot be null");
        ImmutableList.copyOf(items).forEach(this.items::remove);
    }
    
    /**
     * Sets the ItemStacks for this PathfinderTempt.
     * @param items Collection of Items to use
     * @throws IllegalArgumentException if Items are null or empty
     */
    public void setItems(@NotNull Iterable<? extends ItemStack> items) throws IllegalArgumentException {
        if(items == null) throw new IllegalArgumentException("items cannot be null");
        if (!items.iterator().hasNext()) throw new IllegalArgumentException("items cannot be empty");
        this.items = new HashSet<>(ImmutableList.copyOf(items));
    }

    /**
     * Sets the ItemStacks for this PathfinderTempt/
     * @param items Array of Items to use
     * @throws IllegalArgumentException if Items are null or empty
     */
    public void setItems(@NotNull ItemStack... items) throws IllegalArgumentException
    { if (items == null) throw new IllegalArgumentException("Items cannot be null"); setItems(Arrays.asList(items)); }

    @Override
    public double getSpeedModifier() {
        return this.speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) {
        this.speedMod = mod;
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.LOOKING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalTempt";
    }
}
