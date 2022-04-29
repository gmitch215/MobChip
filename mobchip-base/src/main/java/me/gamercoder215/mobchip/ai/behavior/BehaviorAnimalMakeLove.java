package me.gamercoder215.mobchip.ai.behavior;

import org.bukkit.entity.EntityType;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.animal.Animal;

/**
 * A Behavior for Animals to Breed
 */
public class BehaviorAnimalMakeLove extends Behavior implements SpeedModifier {

    private final EntityType partnerType;
    
    private float speedModifier;

    /**
     * Construct a BehaviorAnimalMakeLove
     * @param type Partner EntityType
     * @param speedModifier Speed Modifier
     */
    public BehaviorAnimalMakeLove(EntityType type, float speedModifier) {
        this.partnerType = type;
        this.speedModifier = speedModifier;
    }

    /**
     * Construct a BehaviorAnimalMakeLove, with the default speed modifier
     * @param type Partner EntityType
     */
    public BehaviorAnimalMakeLove(EntityType type) {
        this.partnerType = type;
        this.speedModifier = DEFAULT_SPEED_MODIFIER;
    }

    /**
     * Get the EntityType that this Entity is looking for.
     * @return EntityType found
     */
    public EntityType getPartnerType() {
        return this.partnerType;
    }

    @Override
    public double getSpeedModifier() {
        return speedModifier;
    }

    /**
     * @throws IllegalArgumentException if greater than {@link Float#MAX_VALUE}
     */
    @Override
    public void setSpeedModifier(double mod) throws IllegalArgumentException {
        if (mod > Float.MAX_VALUE) throw new IllegalArgumentException("Greater than Float.MAX_VALUE");
        this.speedModifier = (float) mod;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AnimalMakeLove getHandle() {
        try {
            return new AnimalMakeLove((net.minecraft.world.entity.EntityType<? extends Animal>) ChipConversions.convertType(partnerType), speedModifier);
        } catch (ClassCastException e) {
            return null;
        }
    }
    
}