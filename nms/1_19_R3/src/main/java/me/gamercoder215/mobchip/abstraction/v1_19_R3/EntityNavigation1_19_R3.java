package me.gamercoder215.mobchip.abstraction.v1_19_R3;

import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.ai.navigation.NavigationPath;
import me.gamercoder215.mobchip.util.Position;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

final class EntityNavigation1_19_R3 implements EntityNavigation {

    private final PathNavigation handle;

    private int speedMod;
    private int range;
    private final List<Position> points;

    private final Mob m;

    public EntityNavigation1_19_R3(Mob m) {
        this.handle = ChipUtil1_19_R3.toNMS(m).getNavigation();
        this.points = new ArrayList<>();

        this.speedMod = 1;
        this.range = Integer.MAX_VALUE;
        this.m = m;
    }

    @Override
    public double getSpeedModifier() {
        return this.speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) throws IllegalArgumentException {
        if (mod > Integer.MAX_VALUE) throw new IllegalArgumentException("Must be integer");
        this.speedMod = (int) Math.floor(mod);
    }

    @Override
    public EntityNavigation recompute() {
        this.handle.recomputePath();
        return this;
    }

    @Override
    public EntityNavigation addPoint(@NotNull Position point) {
        this.points.add(point);
        return this;
    }

    @Override
    public EntityNavigation addPoint(int index, @NotNull Position point) {
        this.points.add(index, point);
        return this;
    }

    @Override
    public EntityNavigation removePoint(@NotNull Position point) {
        this.points.remove(point);
        return this;
    }

    @Override
    public EntityNavigation removePoint(int index) {
        this.points.remove(index);
        return this;
    }

    private List<Node> toNodes() {
        List<Node> nodes = new ArrayList<>();
        for (Position p : this.points)
            nodes.add(new Node(p.getX(), p.getY(), p.getZ()));

        return nodes;
    }

    @Override
    @NotNull
    public NavigationPath buildPath() {
        if (this.points.isEmpty()) throw new IllegalArgumentException("Path is empty");

        return new NavigationPath1_19_R3(new Path(toNodes(), null, true), m, speedMod);
    }

    @Override
    public EntityNavigation setRange(int range) {
        this.range = range;
        return this;
    }

    @Override
    public int getRange() {
        return this.range;
    }
}
