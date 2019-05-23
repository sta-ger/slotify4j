package slotify4j.session;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class GameSessionImplTest {

    public static void testDefaultSessionHasProperInitialValues(Constructor<? extends GameSession> sessionConstructor, Constructor<? extends GameSessionConfig> configConstructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        GameSessionConfig config = configConstructor.newInstance();
        GameSession session = sessionConstructor.newInstance(config);
        assertEquals(session.getAvailableBets(), config.getAvailableBets());
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
        assertEquals(session.getCreditsAmount(), 1000);
    }

    public static void testDefaultSessionHasProperInitialValuesWithCustomConfig(Constructor<? extends GameSession> sessionConstructor, Constructor<? extends GameSessionConfig> configConstructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        GameSessionConfig config = configConstructor.newInstance();
        config.setAvailableBets(new long[]{10, 20, 30});
        config.setCreditsAmount(5000);
        GameSession session = sessionConstructor.newInstance(config);
        assertFalse(session.isBetAvailable(1));
        assertTrue(session.isBetAvailable(10));
        assertEquals(session.getAvailableBets(), config.getAvailableBets());
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
        assertEquals(session.getCreditsAmount(), 5000);
    }

    public static void testDefaultSessionWithWrongInitialBet(Constructor<? extends GameSession> sessionConstructor, Constructor<? extends GameSessionConfig> configConstructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        GameSessionConfig config = configConstructor.newInstance();
        config.setAvailableBets(new long[]{10, 20, 30});
        config.setBet(1);
        GameSession session = sessionConstructor.newInstance(config);
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
    }

    public static void testDefaultSessionPlaysWhileEnoughCredits(GameSession session) {
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
    void testCreateNewSession() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        testDefaultSessionHasProperInitialValues(GameSessionImpl.class.getConstructor(GameSessionConfig.class), DefaultGameSessionConfig.class.getConstructor());
    }

    @Test
    void testCreateNewSessionWithCustomConfig() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        testDefaultSessionHasProperInitialValuesWithCustomConfig(GameSessionImpl.class.getConstructor(GameSessionConfig.class), DefaultGameSessionConfig.class.getConstructor());
    }

    @Test
    void testCreateNewSessionWithWrongBet() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        testDefaultSessionWithWrongInitialBet(GameSessionImpl.class.getConstructor(GameSessionConfig.class), DefaultGameSessionConfig.class.getConstructor());
    }

    @Test
    void testPlayWhileEnoughCredits() {
        testDefaultSessionPlaysWhileEnoughCredits(new GameSessionImpl(new DefaultGameSessionConfig()));
    }

}
