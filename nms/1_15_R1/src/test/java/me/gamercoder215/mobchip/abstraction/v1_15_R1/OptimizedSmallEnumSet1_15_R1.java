package me.gamercoder215.mobchip.abstraction.v1_15_R1;

// Copied from PaperMC: com/destroystokyo/paper/util/set/OptimizedSmallEnumSet
// Used for Testing Purposes

public final class OptimizedSmallEnumSet1_15_R1<E extends Enum<E>> {
    private long backingSet;

    public OptimizedSmallEnumSet1_15_R1(Class<E> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Null class");
        } else if (!clazz.isEnum()) {
            throw new IllegalArgumentException("Class must be enum, not " + clazz.getCanonicalName());
        } else {
        }
    }

    public boolean addUnchecked(E element) {
        int ordinal = element.ordinal();
        long key = 1L << ordinal;
        long prev = this.backingSet;
        this.backingSet = prev | key;
        return (prev & key) == 0L;
    }

    public boolean removeUnchecked(E element) {
        int ordinal = element.ordinal();
        long key = 1L << ordinal;
        long prev = this.backingSet;
        this.backingSet = prev & ~key;
        return (prev & key) != 0L;
    }

    public void clear() {
        this.backingSet = 0L;
    }

    public int size() {
        return Long.bitCount(this.backingSet);
    }

    public long getBackingSet() {
        return this.backingSet;
    }

    public boolean hasElement(E element) {
        return (this.backingSet & 1L << element.ordinal()) != 0L;
    }
}