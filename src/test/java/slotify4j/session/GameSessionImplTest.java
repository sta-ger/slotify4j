package slotify4j.session;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameSessionImplTest {

    public static void testDefaultSessionHasProperInitialValues(GameSession session, GameSessionConfig config) {
        assertEquals(session.getAvailableBets(), config.getAvailableBets());
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
        assertEquals(session.getCreditsAmount(), 1000);
    }

    public static GameSessionConfig createCustomConfigForTestProperInitialValues() {
        DefaultGameSessionConfig conf = new DefaultGameSessionConfig();
        conf.setAvailableBets(new long[]{10, 20, 30});
        conf.setCreditsAmount(5000);
        return conf;
    }

    public static void testDefaultSessionHasProperInitialValuesWithCustomConfig(GameSession session, GameSessionConfig config) {
        assertFalse(session.isBetAvailable(1));
        assertTrue(session.isBetAvailable(10));
        assertEquals(session.getAvailableBets(), config.getAvailableBets());
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
        assertEquals(session.getCreditsAmount(), config.getCreditsAmount());
    }

    public static GameSessionConfig createCustomConfigForWrongBetTest() {
        DefaultGameSessionConfig conf = new DefaultGameSessionConfig();
        conf.setAvailableBets(new long[]{10, 20, 30});
        return conf;
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
    void testCreateNewSession() {
        DefaultGameSessionConfig conf = new DefaultGameSessionConfig();
        GameSessionImpl sess = new GameSessionImpl(conf);
        testDefaultSessionHasProperInitialValues(sess, conf);
    }

    @Test
    void testCreateNewSessionWithCustomConfig() {
        GameSessionConfig conf = createCustomConfigForTestProperInitialValues();
        GameSessionImpl sess = new GameSessionImpl(conf);
        testDefaultSessionHasProperInitialValuesWithCustomConfig(sess, conf);
    }

    @Test
    void testCreateNewSessionWithWrongBet() {
        GameSessionConfig conf = createCustomConfigForWrongBetTest();
        GameSessionImpl sess = new GameSessionImpl(conf);
        testDefaultSessionWithWrongInitialBet(sess, createCustomConfigForTestProperInitialValues());
    }

    @Test
    void testPlayWhileEnoughCredits() throws Exception {
        DefaultGameSessionConfig conf = new DefaultGameSessionConfig();
        GameSessionImpl sess = new GameSessionImpl(conf);
        testDefaultSessionPlaysWhileEnoughCredits(sess);
    }

}
