package slotify4j.session;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultGameSessionTest {

    public void testDefaultSessionHasProperInitialValues(GameSession session, GameSessionConfig config) {
        assertEquals(session.getAvailableBets(), config.getAvailableBets());
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
        assertEquals(session.getCreditsAmount(), 1000);
    }

    @Test
    public void testCreateNewSession() {
        GameSessionConfig config = new DefaultGameSessionConfig();
        GameSession session = new DefaultGameSession(config);
        testDefaultSessionHasProperInitialValues(session, config);
    }

}
