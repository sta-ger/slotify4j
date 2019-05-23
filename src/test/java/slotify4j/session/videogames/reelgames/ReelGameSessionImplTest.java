package slotify4j.session.videogames.reelgames;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

}
