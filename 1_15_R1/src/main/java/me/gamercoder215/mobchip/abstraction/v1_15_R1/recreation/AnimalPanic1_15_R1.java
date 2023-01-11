package me.gamercoder215.mobchip.abstraction.v1_15_R1.recreation;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.v1_15_R1.*;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

public class AnimalPanic1_15_R1 extends Behavior<EntityCreature> {

    private final float speedMultiplier;

    public AnimalPanic1_15_R1(float speedMod) {
        super(ImmutableMap.of(MemoryModuleType.HURT_BY, MemoryStatus.VALUE_PRESENT), 100, 120);
        this.speedMultiplier = speedMod;
    }

    protected boolean canStillUse(WorldServer var0, EntityCreature var1, long var2) { return true; }

    protected void start(WorldServer w, EntityCreature c, long var2) {
        c.getBehaviorController().removeMemory(MemoryModuleType.WALK_TARGET);
    }

    protected void stop(WorldServer w, EntityCreature c, long var2) {}

    protected void tick(WorldServer w, EntityCreature c, long var2) {
        if (c.getNavigation().m()) {
            Vec3D var4 = this.getPanicPos(c, w);
            if (var4 != null) {
                c.getBehaviorController().setMemory(MemoryModuleType.WALK_TARGET, new MemoryTarget(var4, this.speedMultiplier, 0));
            }
        }

    }

    private static Vec3D atBottomCenterOf(BaseBlockPosition pos) {
        return new Vec3D((double) pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D);
    }


    private Vec3D getPanicPos(EntityCreature c, WorldServer w) {
        if (c.isBurning()) {
            Optional<Vec3D> var2 = this.lookForWater(w, c).map(AnimalPanic1_15_R1::atBottomCenterOf);
            if (var2.isPresent()) return var2.get();
        }

        return getPos(c, 5, 4, c::f);
    }

    private static boolean isRestricted(EntityCreature c, int var1) {
        return c.eg() && c.ed().a(c.getPositionVector(), (double) (c.ee() + (float) var1) + 1.0D);
    }

    public static boolean isRestricted(boolean condition, EntityCreature c, BlockPosition pos) {
        return condition && !c.a(pos);
    }

    public static boolean isNotStable(NavigationAbstract var0, BlockPosition var1) {
        return !var0.a(var1);
    }

    private static Vec3D getPos(EntityCreature c, int var1, int var2, ToDoubleFunction<BlockPosition> func) {
        boolean var4 = isRestricted(c, var1);
        return generateRandomPos(() -> {
            BlockPosition var4x = generateRandomDirection(c.getRandom(), var1, var2);
            BlockPosition var5 = generateRandomPosTowardDirection(c, var1, var4, var4x);
            return var5 == null ? null : movePosUpOutOfSolid(c, var5);
        }, func);
    }

    private static BlockPosition moveUpOutOfSolid(BlockPosition var0, int var1, Predicate<BlockPosition> var2) {
        if (!var2.test(var0)) return var0;

        BlockPosition var3 = var0.up();
        while (var2.test(var3)) var3 = var3.up();

        return var3;
    }

    private static BlockPosition movePosUpOutOfSolid(EntityCreature c, BlockPosition ogPosition) {
        BlockPosition pos = moveUpOutOfSolid(ogPosition, 256, (var1x) -> isSolid(c, var1x));
        return !isWater(c, pos) && !hasMalus(c, pos) ? pos : null;
    }

    private static boolean isSolid(EntityCreature c, BlockPosition pos) {
        return c.world.getType(pos).getMaterial().isSolid();
    }

    private static Vec3D generateRandomPos(Supplier<BlockPosition> var0, ToDoubleFunction<BlockPosition> var1) {
        double var2 = Double.NEGATIVE_INFINITY;
        BlockPosition var4 = null;

        for(int var5 = 0; var5 < 10; ++var5) {
            BlockPosition var6 = var0.get();
            if (var6 != null) {
                double var7 = var1.applyAsDouble(var6);
                if (var7 > var2) {
                    var2 = var7;
                    var4 = var6;
                }
            }
        }

        return var4 != null ? atBottomCenterOf(var4) : null;
    }

    private static BlockPosition generateRandomDirection(Random var0, int var1, int var2) {
        int var3 = var0.nextInt(2 * var1 + 1) - var1;
        int var4 = var0.nextInt(2 * var2 + 1) - var2;
        int var5 = var0.nextInt(2 * var1 + 1) - var1;
        return new BlockPosition(var3, var4, var5);
    }


    private static BlockPosition generateRandomPosTowardDirection(EntityCreature c, int var1, Random var2, BlockPosition var3) {
        int var4 = var3.getX();
        int var5 = var3.getZ();
        if (c.eg() && var1 > 1) {
            BlockPosition center = c.ed();
            if (c.locX() > (double) center.getX()) {
                var4 -= var2.nextInt(var1 / 2);
            } else {
                var4 += var2.nextInt(var1 / 2);
            }

            if (c.locZ() > (double) center.getZ()) {
                var5 -= var2.nextInt(var1 / 2);
            } else {
                var5 += var2.nextInt(var1 / 2);
            }
        }

        return new BlockPosition((double) var4 + c.locX(), (double) var3.getY() + c.locY(), (double) var5 + c.locZ());
    }

    private static BlockPosition generateRandomPosTowardDirection(EntityCreature c, int var1, boolean var2, BlockPosition var3) {
        BlockPosition var4 = generateRandomPosTowardDirection(c, var1, c.getRandom(), var3);
        return !isOutsideLimits(var4) && !isRestricted(var2, c, var4) && !isNotStable(c.getNavigation(), var4) ? var4 : null;
    }

    private static boolean isOutsideLimits(@NotNull BlockPosition pos) {
        return pos.getY() < 0 || pos.getY() > 256;
    }

    private static boolean isWater(@NotNull EntityCreature c, BlockPosition pos) {
        return c.world.getFluid(pos).a(TagsFluid.WATER);
    }

    public static boolean hasMalus(@NotNull EntityCreature c, @NotNull BlockPosition pos) {
        return c.a(PathfinderNormal.b(c.world, pos.getX(), pos.getY(), pos.getZ())) != 0.0F;
    }


    private Optional<BlockPosition> lookForWater(@NotNull IBlockAccess access, @NotNull Entity en) {
        BlockPosition pos = new BlockPosition(en.getPositionVector());
        return !access.getType(pos).getCollisionShape(access, pos).isEmpty() ? Optional.empty() : findClosestMatch(pos, 5, 1,
                position -> access.getFluid(position).a(TagsFluid.WATER));
    }

    private static Iterable<BlockPosition> withinManhattan(BlockPosition pos, int var1, int var2, int var3) {
        int var4 = var1 + var2 + var3;
        int var5 = pos.getX();
        int var6 = pos.getY();
        int var7 = pos.getZ();

        int posInt = (int) pos.asLong();

        return () -> new AbstractIterator<BlockPosition>() {
            private final BlockPosition.MutableBlockPosition cursor = new BlockPosition.MutableBlockPosition();
            private int currentDepth;
            private int maxX;
            private int maxY;
            private int x;
            private int y;
            private boolean zMirror;

            protected BlockPosition computeNext() {
                if (this.zMirror) {
                    this.zMirror = false;
                    this.cursor.q(posInt - (this.cursor.getZ() - posInt));
                    return this.cursor;
                } else {
                    BlockPosition.MutableBlockPosition var0x;
                    for(var0x = null; var0x == null; ++this.y) {
                        if (this.y > this.maxY) {
                            ++this.x;
                            if (this.x > this.maxX) {
                                ++this.currentDepth;
                                if (this.currentDepth > var1) {
                                    return this.endOfData();
                                }

                                this.maxX = Math.min(var2, this.currentDepth);
                                this.x = -this.maxX;
                            }

                            this.maxY = Math.min(var3, this.currentDepth - Math.abs(this.x));
                            this.y = -this.maxY;
                        }

                        int x = this.x;
                        int y = this.y;
                        int depth = this.currentDepth - Math.abs(x) - Math.abs(y);
                        if (depth <= var4) {
                            this.zMirror = depth != 0;
                            var0x = this.cursor.d(var5 + x, var6 + y, posInt + depth);
                        }
                    }

                    return var0x;
                }
            }
        };
    }

    private static Optional<BlockPosition> findClosestMatch(BlockPosition var0, int var1, int var2, Predicate<BlockPosition> var3) {
        Iterator<BlockPosition> var4 = withinManhattan(var0, var1, var2, var1).iterator();

        BlockPosition var5;
        do {
            if (!var4.hasNext()) return Optional.empty();
            var5 = var4.next();
        } while(!var3.test(var5));

        return Optional.of(var5);
    }

    
}
