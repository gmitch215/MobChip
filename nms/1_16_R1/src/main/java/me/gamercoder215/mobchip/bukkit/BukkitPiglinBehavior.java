package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.PiglinBehavior;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.Item;
import org.bukkit.entity.Piglin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

class BukkitPiglinBehavior extends BukkitCreatureBehavior implements PiglinBehavior {

    private int hoglinScanRadius = 100;

    final org.bukkit.entity.Piglin piglin;

    public BukkitPiglinBehavior(Piglin piglin) {
        super(piglin);
        this.piglin = piglin;
    }

    static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
    }

    BehaviorResult run(String behaviorName, Object... args) {
        switch (getServerVersion()) {
            default: return wrapper.runBehavior(piglin, behaviorName, "net.minecraft.world.entity.monster.piglin");

            case "1_16_R1":
            case "1_16_R2":
            case "1_16_R3": return wrapper.runBehavior(piglin, behaviorName, "net.minecraft.server.{V}", args);
        }
    }

    @Override
    public @NotNull BehaviorResult stopHunting() {
        return run("BehaviorRememberHuntedHoglin");
    }

    @Override
    public @NotNull BehaviorResult startAdmiring(@Nullable Item want, int duration) {
        wrapper.setMemory(piglin, EntityMemory.IS_ADMIRING_ITEM, true);
        wrapper.setMemory(piglin, EntityMemory.NEAREST_WANTED_ITEM, want);

        return run("BehaviorStartAdmiringItem", duration);
    }

    @Override
    public @NotNull BehaviorResult startHunting() {
        Optional<Hoglin> targetO = Optional.ofNullable(piglin.getTarget())
                .filter(Hoglin.class::isInstance)
                .map(Hoglin.class::cast);

        Hoglin target = targetO.orElse((Hoglin) piglin.getNearbyEntities(hoglinScanRadius, hoglinScanRadius, hoglinScanRadius)
                .stream()
                .filter(Hoglin.class::isInstance)
                .findFirst()
                .orElse(null));

        if (target == null) return BehaviorResult.STOPPED;

        wrapper.setMemory(piglin, "nearest_visible_huntable_hoglin", target);

        return run("BehaviorHuntHoglin");
    }

    @Override
    public void setHuntingRadius(int radius) throws IllegalArgumentException {
        if (radius <= 0) throw new IllegalArgumentException("Radius must be positive!");
        hoglinScanRadius = radius;
    }

    @Override
    public @NotNull BehaviorResult stopAdmiring() {
        wrapper.removeMemory(piglin, EntityMemory.IS_ADMIRING_ITEM);
        wrapper.removeMemory(piglin, EntityMemory.NEAREST_WANTED_ITEM);

        return run("BehaviorStopAdmiring");
    }
}
