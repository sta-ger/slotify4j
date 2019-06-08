package slotify4j.session.videogames.reelgames;

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

}
