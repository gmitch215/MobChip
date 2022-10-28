package me.gamercoder215.mobchip.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestPosition {

    @Test
    @DisplayName("Test Basic Position")
    public void testBasic() {
        Position p = new Position(0, 0, 0);
        Assertions.assertEquals(0, p.getX());
        Assertions.assertEquals(0, p.getY());
        Assertions.assertEquals(0, p.getZ());

        p.setX(1).setY(2).setZ(3);
        Assertions.assertEquals(1, p.getX());
        Assertions.assertEquals(2, p.getY());
        Assertions.assertEquals(3, p.getZ());
    }

    @Test
    @DisplayName("Test Position Distance")
    public void testDistance() {
        Position p1 = new Position(1, 1, 1);
        Position p2 = new Position(3, 3, 3);

        Assertions.assertEquals(p1.distanceSquared(p2), 12);
        Assertions.assertEquals(p1.distance(p2), Math.sqrt(12));
    }

}
