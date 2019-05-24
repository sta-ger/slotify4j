package slotify4j.session.videogames.reelgames;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class ReelGameSessionImplTest {

    public static void testDefaultReelGameSessionHasProperInitialValues(Constructor<? extends ReelGameSession> sessionConstructor, Constructor<? extends ReelGameSessionConfig> configConstructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ReelGameSessionConfig config = configConstructor.newInstance();
        ReelGameSession session = sessionConstructor.newInstance(config);
        assertEquals(session.getWinningAmount(), 0);
        assertEquals(session.getPaytable(), config.getPaytable());
        assertEquals(session.getReelsItemsSequences().length, config.getReelsItemsSequences().length);
        assertEquals(session.getReelsItemsNumber(), config.getReelsItemsNumber());
        assertEquals(session.getReelsNumber(), config.getReelsNumber());
        assertNull(session.getReelsItems());
        assertNull(session.getWinningLines());
        assertNull(session.getWinningScatters());
    }

    public static void testPlayUntilWin(Constructor<? extends ReelGameSession> sessionConstructor, Constructor<? extends ReelGameSessionConfig> configConstructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        long lastBet;
        long lastCredits;
        boolean wasLinesWin;
        boolean wasScattersWin;

        ReelGameSessionConfig config = configConstructor.newInstance();
        config.setCreditsAmount(10000000);
        ReelGameSession session = sessionConstructor.newInstance(config);

        int timesToPlay = 1000;
        for (int i = 0; i < timesToPlay; i++) {
            while (session.getWinningAmount() == 0 || wasLinesWin || wasScattersWin) {
                wasLinesWin = false;
                wasScattersWin = false;
                lastCredits = session.getCreditsAmount();
                lastBet = session.getBet();
                session.play();
                if (session.getWinningAmount() === 0) {
                    assertEquals(session.getCreditsAmount(), lastCredits - lastBet);
                }
            }
            assertTrue(session.getCreditsAmount() >= lastCredits - lastBet);

            wasLinesWin = session.getWinningLines().size() > 0;
            wasScattersWin = session.getWinningScatters().size() > 0;
            if (i == timesToPlay - 1 && !wasLinesWin && !wasScattersWin) {
                i = 0;
            }
        }
    };

}
