package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsController;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerImpl;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReelGameWithFreeGamesSessionImplTest {

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
        conf.setReelsItemsSequences(ReelGameSessionReelsController.createItemsSequences(conf.getReelsNumber(), conf.getAvailableItems(), new HashMap<>() {{
            put(0, new HashMap<>() {{
                put("S", 0);
            }});
            put(4, new HashMap<>() {{
                put("S", 0);
            }});
        }}));
        return conf;
    }

    @Test
    void testPlayFreeGames() throws Exception {
        ReelGameWithFreeGamesSessionConfig conf = createConfigForTestPlayFreeGames();
        ReelGameWithFreeGamesSession session = new ReelGameWithFreeGamesSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));

        //The following situations need to be checked:
        boolean wasNormalFreeGames = false; //played normal 10 free games
        boolean wasAdditionalFreeGames = false; //free games was won again at free games mode
        boolean wasFreeBank = false; //was any winning during free games mode
        boolean wasNoFreeBank = false; //was no winnings during free games mode
        while (!wasNormalFreeGames || !wasAdditionalFreeGames || !wasFreeBank || !wasNoFreeBank) {
            while (session.getFreeGameSum() == 0 || (session.getFreeGameSum() > 0 && session.getFreeGameNum() == session.getFreeGameSum())) { //Play until won free games
                session.play();
            }
            int playedFreeGamesCount = 0;
            int expectedPlayedFreeGamesCount = session.getFreeGameSum();
            long lastFreeBank = 0;
            long creditsBeforeFreeGame = session.getCreditsAmount();
            while (session.getFreeGameSum() > 0 && session.getFreeGameNum() != session.getFreeGameSum()) { //Play until end of free games
                lastFreeBank = session.getFreeGameBank();
                session.play();
                assertEquals(session.getFreeGameBank(), lastFreeBank + session.getWinningAmount());
                assertEquals(session.getCreditsAmount(), creditsBeforeFreeGame); //Bet should not be subtracted at free games mode
                playedFreeGamesCount++;
                if (session.getFreeGameSum() > expectedPlayedFreeGamesCount) {
                    wasAdditionalFreeGames = true;
                    expectedPlayedFreeGamesCount = session.getFreeGameSum();
                    assertTrue(session.getWinningScatters().size() > 0);
                    Stream.of(conf.getScattersData()).forEach(scatterData -> {
                        if (conf.getFreeGamesForScatters(scatterData.getItemId(), scatterData.getMinimumItemsNumForScatterWin()) > 0) {
                            assertTrue(session.getWinningScatters().containsKey(scatterData.getItemId()));
                            assertTrue(conf.getFreeGamesForScatters(scatterData.getItemId(), session.getWinningScatters().get(scatterData.getItemId()).getItemsPositions().length) > 0);
                            assertEquals(session.getWonFreeGamesNumber(), conf.getFreeGamesForScatters(scatterData.getItemId(), session.getWinningScatters().get(scatterData.getItemId()).getItemsPositions().length));
                        }
                    });
                } else {
                    wasNormalFreeGames = true;
                }
            }

            if (lastFreeBank == 0) {
                wasNoFreeBank = true;
            } else {
                wasFreeBank = true;
            }

            assertEquals(session.getCreditsAmount(), creditsBeforeFreeGame + lastFreeBank);
            assertEquals(playedFreeGamesCount, expectedPlayedFreeGamesCount);
        }
    }

    ;


}
