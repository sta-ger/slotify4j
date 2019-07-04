package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultReelGameWithFreeGamesSessionConfigBuilderTest {

    public static boolean testBuilderMethods(ReelGameWithFreeGamesSessionConfigBuilder builder) {
        assertEquals(builder
                        .withFreeGamesForScatters("S", 3, 10)
                        .build()
                        .getFreeGamesForScatters("S", 3),
                10
        );
        return true;
    }

    @Test
    public void testDefaultReelGameWithFreeGamesSessionConfigBuilder() {
        assertTrue(testBuilderMethods(DefaultReelGameWithFreeGamesSessionConfig.builder()));
    }

}
