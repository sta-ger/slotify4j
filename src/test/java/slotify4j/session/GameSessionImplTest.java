package slotify4j.session;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameSessionImplTest {

    public static void testDefaultSessionHasProperInitialValues(GameSession session, GameSessionConfig config) {
        assertArrayEquals(session.getAvailableBets(), config.getAvailableBets());
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
        assertEquals(session.getCreditsAmount(), 1000);
    }

    public static GameSessionConfig createCustomConfigForTestProperInitialValues() {
        return new DefaultGameSessionConfig.Builder()
                .withAvailableBets(new long[]{10, 20, 30})
                .withCreditsAmount(5000)
                .build();
    }

    public static void testDefaultSessionHasProperInitialValuesWithCustomConfig(GameSession session, GameSessionConfig config) {
        assertFalse(session.isBetAvailable(1));
        assertTrue(session.isBetAvailable(10));
        assertArrayEquals(session.getAvailableBets(), config.getAvailableBets());
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
        assertEquals(session.getCreditsAmount(), config.getCreditsAmount());
    }

    public static GameSessionConfig createCustomConfigForWrongBetTest() {
        return new DefaultGameSessionConfig.Builder()
                .withAvailableBets(new long[]{10, 20, 30})
                .build();
    }

    public static void testDefaultSessionWithWrongInitialBet(GameSession session, GameSessionConfig config) {
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
    }

    public static void testDefaultSessionPlaysWhileEnoughCredits(GameSession session) throws Exception {
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
    }

    @Test
    public void testCreateNewSession() {
        DefaultGameSessionConfig conf = new DefaultGameSessionConfig();
        GameSessionImpl sess = new GameSessionImpl(conf);
        testDefaultSessionHasProperInitialValues(sess, conf);
    }

    @Test
    public void testCreateNewSessionWithCustomConfig() {
        GameSessionConfig conf = createCustomConfigForTestProperInitialValues();
        GameSessionImpl sess = new GameSessionImpl(conf);
        testDefaultSessionHasProperInitialValuesWithCustomConfig(sess, conf);
    }

    @Test
    public void testCreateNewSessionWithWrongBet() {
        GameSessionConfig conf = createCustomConfigForWrongBetTest();
        GameSessionImpl sess = new GameSessionImpl(conf);
        testDefaultSessionWithWrongInitialBet(sess, createCustomConfigForTestProperInitialValues());
    }

    @Test
    public void testPlayWhileEnoughCredits() throws Exception {
        DefaultGameSessionConfig conf = new DefaultGameSessionConfig();
        GameSessionImpl sess = new GameSessionImpl(conf);
        testDefaultSessionPlaysWhileEnoughCredits(sess);
    }

}
