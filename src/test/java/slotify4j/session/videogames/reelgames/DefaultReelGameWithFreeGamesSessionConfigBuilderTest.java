package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultReelGameWithFreeGamesSessionConfigBuilderTest {

    public static boolean testBuilderMethods(ReelGameWithFreeGamesSessionConfigBuilder builder) {
        boolean flag = false;
        try {
            assertEquals(builder
                            .withFreeGamesForScatters("S", 3, 10)
                            .build()
                            .getFreeGamesForScatters("S", 3),
                    10
            );
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Test
    public void testDefaultReelGameWithFreeGamesSessionConfigBuilder() {
        assertTrue(testBuilderMethods(DefaultReelGameWithFreeGamesSessionConfig.builder()));
    }

    @Test
    public void testBaseTests() {
        assertTrue(DefaultGameSessionConfigBuilderTest.testBuilderMethods(DefaultReelGameWithFreeGamesSessionConfig.builder()));
        assertTrue(DefaultReelGameSessionConfigBuilderTest.testBuilderMethods(DefaultReelGameWithFreeGamesSessionConfig.builder()));
    }

}
