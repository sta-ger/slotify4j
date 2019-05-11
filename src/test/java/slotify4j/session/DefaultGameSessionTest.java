package slotify4j.session;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class DefaultGameSessionTest {

    public void testDefaultSessionHasProperInitialValues(Constructor<? extends GameSession> sessionConstructor, Constructor<? extends GameSessionConfig> configConstructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        GameSessionConfig config = configConstructor.newInstance();
        GameSession session = sessionConstructor.newInstance(config);
        assertEquals(session.getAvailableBets(), config.getAvailableBets());
        assertEquals(session.getBet(), config.getAvailableBets()[0]);
        assertEquals(session.getCreditsAmount(), 1000);
    }

    @Test
    public void testCreateNewSession() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        testDefaultSessionHasProperInitialValues(DefaultGameSession.class.getConstructor(GameSessionConfig.class), DefaultGameSessionConfig.class.getConstructor());
    }

}
