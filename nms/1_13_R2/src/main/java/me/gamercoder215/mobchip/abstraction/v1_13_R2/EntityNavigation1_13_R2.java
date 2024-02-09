package me.gamercoder215.mobchip.abstraction.v1_13_R2;

import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.ai.navigation.NavigationPath;
import me.gamercoder215.mobchip.util.Position;
import net.minecraft.server.v1_13_R2.NavigationAbstract;
import net.minecraft.server.v1_13_R2.PathEntity;
import net.minecraft.server.v1_13_R2.PathPoint;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

final class EntityNavigation1_13_R2 implements EntityNavigation {

    private final NavigationAbstract handle;

    private int speedMod;
    private int range;
    private final List<Position> points;

    private final Mob m;

    public EntityNavigation1_13_R2(Mob m) {
        this.handle = ChipUtil1_13_R2.toNMS(m).getNavigation();
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
        this.handle.j();
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

    private List<PathPoint> toNodes() {
        List<PathPoint> nodes = new ArrayList<>();
        for (Position p : this.points)
            nodes.add(new PathPoint(p.getX(), p.getY(), p.getZ()));

        return nodes;
    }

    @Override
    @NotNull
    public NavigationPath buildPath() {
        if (this.points.isEmpty()) throw new IllegalArgumentException("Path is empty");

        return new NavigationPath1_13_R2(new PathEntity(toNodes().toArray(new PathPoint[0])), m, speedMod);
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
