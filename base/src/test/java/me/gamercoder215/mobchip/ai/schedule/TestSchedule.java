package me.gamercoder215.mobchip.ai.schedule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSchedule {

    @Test
    @DisplayName("Test Schedule Builder")
    public void testBuilder() {
        Schedule s = Schedule.builder()
                .addActivity(10, Activity.IDLE)
                .addActivity(20, Activity.CORE)
                .addActivity(3000, Activity.IDLE)
                .build();


        Assertions.assertEquals(Activity.IDLE, s.get(10));
    }

}
