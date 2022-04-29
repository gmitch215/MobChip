package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;

public class BehaviorAnimalPanic extends Behavior implements SpeedModifier {

    private float speedModifier;

    public BehaviorAnimalPanic(float speedModifier) {
        this.speedModifier = speedModifier;
    }

    /**
     * Construct a BehaviorAnimalPanic with a speedModifier of {@link SpeedModifier#DEFAULT_SPEED_MODIFIER}
     */
    public BehaviorAnimalPanic() {
        this.speedModifier = DEFAULT_SPEED_MODIFIER;
    }

    @Override
    public AnimalPanic getHandle() {
        return new AnimalPanic(speedModifier);
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
}