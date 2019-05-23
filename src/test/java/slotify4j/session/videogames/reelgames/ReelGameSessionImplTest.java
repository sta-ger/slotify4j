package slotify4j.session.videogames.reelgames;

import slotify4j.session.GameSession;
import slotify4j.session.GameSessionConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class ReelGameSessionImplTest {

    public static void testDefaultReelGameSessionHasProperInitialValues(Constructor<? extends ReelGameSession> sessionConstructor, Constructor<? extends GameSessionConfig> configConstructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        GameSessionConfig config = configConstructor.newInstance();
        GameSession session = sessionConstructor.newInstance(config);
        assertEquals(session.getWinningAmount(), 0);
        /*expect(session.getPaytable()).toEqual(config.paytable[session.getBet()]);
        expect(session.getReelsItemsSequences().length).toEqual(config.reelsItemsSequences.length);
        expect(session.getReelsItemsNumber()).toEqual(config.reelsItemsNumber);
        expect(session.getReelsNumber()).toEqual(config.reelsNumber);
        expect(session.getReelsItems()).not.toBeDefined();
        expect(session.getWinningLines()).not.toBeDefined();
        expect(session.getWinningScatters()).not.toBeDefined();*/
    };

}
