package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;
import slotify4j.session.DefaultGameSessionConfig;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultGameSessionConfigBuilderTest {

    @Test
    public void withAvailableBetsTest() {
        assertArrayEquals(DefaultGameSessionConfig.builder()
                .withAvailableBets(new long[]{1, 2, 3, 4, 5})
                .build()
                .getAvailableBets(),
                new long[]{1, 2, 3, 4, 5}
        );
    }

    @Test
    public void withCreditsAmountTest() {
        assertEquals(DefaultGameSessionConfig.builder()
                        .withCreditsAmount(999)
                        .build()
                        .getCreditsAmount(),
                999
        );
    }

    @Test
    public void withBetTest() {
        assertEquals(DefaultGameSessionConfig.builder()
                        .withBet(100)
                        .build()
                        .getBet(),
                100
        );
    }

    @Test
    public void buildTest() {
        assertDoesNotThrow(() -> DefaultGameSessionConfig.builder().build());
    }

}
