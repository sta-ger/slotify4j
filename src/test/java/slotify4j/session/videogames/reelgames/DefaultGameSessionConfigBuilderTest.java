package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;
import slotify4j.session.DefaultGameSessionConfig;
import slotify4j.session.GameSessionConfigBuilder;

import static org.junit.jupiter.api.Assertions.*;

class DefaultGameSessionConfigBuilderTest {

    public static boolean testBuilderMethods(GameSessionConfigBuilder builder) {
        boolean flag = false;
        try {
            assertArrayEquals(builder.withAvailableBets(new long[]{1, 2, 3, 4, 5})
                            .build()
                            .getAvailableBets(),
                    new long[]{1, 2, 3, 4, 5}
            );
            assertEquals(builder.withCreditsAmount(999)
                            .build()
                            .getCreditsAmount(),
                    999
            );
            assertEquals(builder.withBet(100)
                            .build()
                            .getBet(),
                    100
            );
            assertDoesNotThrow(() -> DefaultGameSessionConfig.builder().build());
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Test
    public void testDefaultGameSessionConfigBuilder() {
        assertTrue(testBuilderMethods(DefaultGameSessionConfig.builder()));
    }

}
