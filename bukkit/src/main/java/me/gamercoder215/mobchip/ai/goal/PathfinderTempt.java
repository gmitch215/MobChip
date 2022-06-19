package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.util.MobChipUtil;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;
import org.bukkit.entity.Creature;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for the logic of this Creature getting tempted to move to another entity, for when they hold a specific item.
 */
public final class PathfinderTempt extends Pathfinder implements SpeedModifier {
    
    private ItemStack item;
    private double speedMod;

    /**
     * Constructs a PathfinderTempt from a NMS TemptGoal.
     * @param g Goal to use
     */
    public PathfinderTempt(@NotNull TemptGoal g) {
        super(getEntity(g, "a"));

        this.speedMod = getDouble(g, "e");
        Ingredient i = getField(g, "m", Ingredient.class);
        this.item = MobChipUtil.convert(i.itemStacks[0]);
    }

    /**
     * Constructs a PathfinderTempt with the default speed modifier.
     * @param m Creature to use
     * @param item ItemStack to be tempted by
     * @throws IllegalArgumentException if item is null
     */
    public PathfinderTempt(@NotNull Creature m, @NotNull ItemStack item) throws IllegalArgumentException {
        this(m, item, DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Constructs a PathfinderTempt with the default speed modifier.
     * @param m Creature to use
     * @param item ItemStack to be tempted by
     * @param speedMod Speed Modifier while moving to target holding item
     * @throws IllegalArgumentException if item is null
     */
    public PathfinderTempt(@NotNull Creature m, @NotNull ItemStack item, double speedMod) throws IllegalArgumentException {
        super(m);

        if (item == null) throw new IllegalArgumentException("item cannot be null");

        this.item = item;
        this.speedMod = speedMod;
    }

    /**
     * Gets the ItemStack belonging to this PathfinderTempt.
     * @return ItemStack found
     */
    @NotNull
    public ItemStack getItem() {
        return this.item;
    }

    /**
     * Sets the item for this PathfinderTempt.
     * @param item Item to use
     */
    public void setItem(@NotNull ItemStack item) {
        this.item = item;
    }

    @Override
    public double getSpeedModifier() {
        return this.speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) {
        this.speedMod = mod;
    }

    @Override
    public TemptGoal getHandle() {
        return new TemptGoal((PathfinderMob) nmsEntity, speedMod, Ingredient.of(MobChipUtil.convert(item)), false);
    }

}
