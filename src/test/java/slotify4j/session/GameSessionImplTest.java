package slotify4j.session;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameSessionImplTest {

    public static boolean testDefaultSessionHasProperInitialValues(GameSession session, GameSessionConfig config) {
        assertArrayEquals(session.getAvailableBets(), config.getAvailableBets());
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
        assertEquals(session.getCreditsAmount(), 1000);
        return true;
    }

    public static GameSessionConfig createCustomConfigForTestProperInitialValues() {
        return DefaultGameSessionConfig.builder()
                .withAvailableBets(new long[]{10, 20, 30})
                .withCreditsAmount(5000)
                .build();
    }

    public static boolean testDefaultSessionHasProperInitialValuesWithCustomConfig(GameSession session, GameSessionConfig config) {
        assertFalse(session.isBetAvailable(1));
        assertTrue(session.isBetAvailable(10));
        assertArrayEquals(session.getAvailableBets(), config.getAvailableBets());
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
        assertEquals(session.getCreditsAmount(), config.getCreditsAmount());
        return true;
    }

    public static GameSessionConfig createCustomConfigForWrongBetTest() {
        return DefaultGameSessionConfig.builder()
                .withAvailableBets(new long[]{10, 20, 30})
                .build();
    }

    public static boolean testDefaultSessionWithWrongInitialBet(GameSession session, GameSessionConfig config) {
        long bet = config.getAvailableBets()[0];
        while (session.isBetAvailable(bet)) {
            bet = new Random().nextInt();
        }
        session.setBet(bet);
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
        return true;
    }

    public static boolean testDefaultSessionPlaysWhileEnoughCredits(GameSession session) throws Exception {
        session.setBet(10);
        session.play();
        assertEquals(session.getCreditsAmount(), 990);
        assertTrue(session.canPlayNextGame());

        //Play with different bet
        session.setBet(100);
        session.play();
        assertEquals(session.getCreditsAmount(), 890);
        assertTrue(session.canPlayNextGame());

        int playedGamesNum = 0;
        int expectedGamesToPlay = (int) ((double) session.getCreditsAmount() / session.getBet());
        while (session.canPlayNextGame()) {
            session.play();
            playedGamesNum++;
        }

        assertEquals(playedGamesNum, expectedGamesToPlay);

        //Decrease bet to 10 and play remaining 9 games
        session.setBet(10);

        playedGamesNum = 0;
        expectedGamesToPlay = (int) ((double) session.getCreditsAmount() / session.getBet());
        while (session.canPlayNextGame()) {
            session.play();
            playedGamesNum++;
        }

        assertEquals(playedGamesNum, expectedGamesToPlay);

        return true;
    }

    @Test
    public void testCreateNewSession() {
        DefaultGameSessionConfig conf = new DefaultGameSessionConfig();
        GameSessionImpl sess = new GameSessionImpl(conf);
        assertTrue(testDefaultSessionHasProperInitialValues(sess, conf));
    }

    @Test
    public void testCreateNewSessionWithCustomConfig() {
        GameSessionConfig conf = createCustomConfigForTestProperInitialValues();
        GameSessionImpl sess = new GameSessionImpl(conf);
        assertTrue(testDefaultSessionHasProperInitialValuesWithCustomConfig(sess, conf));
    }

    @Test
    public void testCreateNewSessionWithWrongBet() {
        GameSessionConfig conf = createCustomConfigForWrongBetTest();
        GameSessionImpl sess = new GameSessionImpl(conf);
        assertTrue(testDefaultSessionWithWrongInitialBet(sess, createCustomConfigForTestProperInitialValues()));
    }

    @Test
    public void testPlayWhileEnoughCredits() throws Exception {
        DefaultGameSessionConfig conf = new DefaultGameSessionConfig();
        GameSessionImpl sess = new GameSessionImpl(conf);
        assertTrue(testDefaultSessionPlaysWhileEnoughCredits(sess));
    }

}
