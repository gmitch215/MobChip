package me.gamercoder215.mobchip.ai.memories;

import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestEntityMemory {

    @Test
    @DisplayName("Test Fetcher")
    public void testFetcher() {
        Assertions.assertNotNull(EntityMemory.getByKey(NamespacedKey.minecraft("home")));
        Assertions.assertNotNull(EntityMemory.getByKey(NamespacedKey.minecraft("job_site")));
    }

}
