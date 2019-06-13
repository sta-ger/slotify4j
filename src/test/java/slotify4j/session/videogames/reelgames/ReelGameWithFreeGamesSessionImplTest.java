package slotify4j.session.videogames.reelgames;

import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerImpl;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorImpl;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReelGameWithFreeGamesSessionImplTest {

    public static void testDefaultReelGameWithFreeGamesSession(ReelGameWithFreeGamesSession session) {
        assertEquals(session.getFreeGameNum(), 0);
        assertEquals(session.getFreeGameSum(), 0);
        assertEquals(session.getFreeGameBank(), 0);
    }

    public static void testFreeGamesGettersSetters(ReelGameWithFreeGamesSession session) {
        session.setFreeGameBank(100);
        session.setFreeGameNum(5);
        session.setFreeGameSum(10);
        assertEquals(session.getFreeGameSum(), 10);
        assertEquals(session.getFreeGameNum(), 5);
        assertEquals(session.getFreeGameBank(), 100);
    }

    public static void testPlayUntilWinFreeGames(ReelGameWithFreeGamesSession session) throws Exception {
        while (session.getFreeGameSum() == 0) {
            session.setCreditsAmount(Long.MIN_VALUE);
            session.play();
        }
        assertEquals(session.getFreeGameNum(), 0);
        assertEquals(session.getFreeGameSum(), 0);
    }

    public static ReelGameWithFreeGamesSessionConfig createConfigForTestPlayFreeGames() {
        DefaultReelGameWithFreeGamesSessionConfig conf = new DefaultReelGameWithFreeGamesSessionConfig();
        conf.setReelsItemsSequences(ReelGameSessionReelsControllerImpl.createItemsSequences(conf.getReelsNumber(), conf.getAvailableItems(), new HashMap<>(){{
            put(0, new HashMap<>(){{
                put("S", 0);
            }});
            put(4, new HashMap<>(){{
                put("S", 0);
            }});
        }}));
        return conf;
    }

    public static ReelGameWithFreeGamesSession createSessionForTestPlayFreeGames() {
        ReelGameWithFreeGamesSessionConfig conf = createConfigForTestPlayFreeGames();
        return new ReelGameWithFreeGamesSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
    }

}
