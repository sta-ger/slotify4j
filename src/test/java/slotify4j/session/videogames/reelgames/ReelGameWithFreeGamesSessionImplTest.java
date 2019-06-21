package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;
import slotify4j.session.GameSessionConfig;
import slotify4j.session.GameSessionImplTest;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsController;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerImpl;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;
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
    void passBaseTests() throws Exception {
        ReelGameWithFreeGamesSessionConfig conf = new DefaultReelGameWithFreeGamesSessionConfig();
        ReelGameWithFreeGamesSession sess = new ReelGameWithFreeGamesSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        GameSessionImplTest.testDefaultSessionHasProperInitialValues(sess, conf);

        GameSessionConfig baseConf = GameSessionImplTest.createCustomConfigForTestProperInitialValues();
        conf = new DefaultReelGameWithFreeGamesSessionConfig();
        conf.setAvailableBets(baseConf.getAvailableBets());
        conf.setCreditsAmount(baseConf.getCreditsAmount());
        sess = new ReelGameWithFreeGamesSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        GameSessionImplTest.testDefaultSessionHasProperInitialValuesWithCustomConfig(sess, conf);

        baseConf = GameSessionImplTest.createCustomConfigForWrongBetTest();
        conf = new DefaultReelGameWithFreeGamesSessionConfig();
        conf.setAvailableBets(baseConf.getAvailableBets());
        sess = new ReelGameWithFreeGamesSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        GameSessionImplTest.testDefaultSessionWithWrongInitialBet(sess, conf);

        ReelGameWithFreeGamesSessionConfig configForTestReelGameBaseTests = new DefaultReelGameWithFreeGamesSessionConfig();
        Arrays.stream(conf.getScattersData()).forEach(scatterData ->
                IntStream.range(0, configForTestReelGameBaseTests.getReelsNumber() * configForTestReelGameBaseTests.getReelsItemsNumber()).forEach(i ->
                        configForTestReelGameBaseTests.setFreeGamesForScatters(scatterData.getItemId(), i, 0)));
        sess = new ReelGameWithFreeGamesSessionImpl(configForTestReelGameBaseTests, new ReelGameSessionReelsControllerImpl(configForTestReelGameBaseTests), new ReelGameSessionWinCalculatorImpl(configForTestReelGameBaseTests));
        ReelGameSessionImplTest.testDefaultReelGameSessionHasProperInitialValues(sess, configForTestReelGameBaseTests);
        ReelGameSessionImplTest.testPlayUntilWin(sess, configForTestReelGameBaseTests);
    }

    @Test
    void feeGamesNumSumBankSettersTest() {
        ReelGameWithFreeGamesSessionConfig conf = createConfigForTestPlayFreeGames();
        ReelGameWithFreeGamesSession session = new ReelGameWithFreeGamesSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        testFreeGamesGettersSetters(session);
    }

    @Test
    void createDefaultReelGameWithFreeGamesSessionTest() {
        ReelGameWithFreeGamesSessionConfig conf = createConfigForTestPlayFreeGames();
        ReelGameWithFreeGamesSession session = new ReelGameWithFreeGamesSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        testDefaultReelGameWithFreeGamesSession(session);
    }

    @Test
    void playUntilWinFreeGamesTest() throws Exception {
        ReelGameWithFreeGamesSessionConfig conf = createConfigForTestPlayFreeGames();
        ReelGameWithFreeGamesSession session = new ReelGameWithFreeGamesSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        testPlayUntilWinFreeGames(session);
    }

    @Test
    void playFreeGamesTest() throws Exception {
        ReelGameWithFreeGamesSessionConfig conf = createConfigForTestPlayFreeGames();
        ReelGameWithFreeGamesSession session = new ReelGameWithFreeGamesSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));

        //The following situations need to be checked:
        boolean wasNormalFreeGames = false; //played normal 10 free games
        boolean wasAdditionalFreeGames = false; //free games was won again at free games mode
        boolean wasAdditionalFreeGamesWonAtLastFreeGame = false; //additional free games was won at 10 of 10 free games
        boolean wasFreeBank = false; //was any winning during free games mode
        boolean wasNoFreeBank = false; //was no winnings during free games mode
        while (
                !wasNormalFreeGames ||
                !wasAdditionalFreeGames ||
                !wasAdditionalFreeGamesWonAtLastFreeGame ||
                !wasFreeBank ||
                !wasNoFreeBank
        ) {
            while (session.getFreeGameSum() == 0 || (session.getFreeGameSum() > 0 && session.getFreeGameNum() == session.getFreeGameSum())) { //Play until won free games
                session.setCreditsAmount(10000);
                session.play();
            }
            int playedFreeGamesCount = 0;
            int expectedPlayedFreeGamesCount = session.getFreeGameSum();
            long lastFreeBank = 0;
            long lastFreeGamesSum;
            long creditsBeforeFreeGame = session.getCreditsAmount();
            while (session.getFreeGameSum() > 0 && session.getFreeGameNum() != session.getFreeGameSum()) { //Play until end of free games
                lastFreeBank = session.getFreeGameBank();
                lastFreeGamesSum = session.getFreeGameSum();
                session.play();
                if (session.getFreeGameSum() > lastFreeGamesSum && session.getFreeGameNum() == lastFreeGamesSum) {
                    wasAdditionalFreeGamesWonAtLastFreeGame = true;
                }
                assertEquals(session.getFreeGameBank(), lastFreeBank + session.getWinningAmount());
                if (session.getFreeGameNum() < session.getFreeGameSum() || session.getFreeGameSum() > expectedPlayedFreeGamesCount) {
                    assertEquals(session.getCreditsAmount(), creditsBeforeFreeGame); //Bet should not be subtracted at free games mode
                } else {
                    assertEquals(session.getCreditsAmount(), creditsBeforeFreeGame + session.getFreeGameBank());
                }
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

            assertEquals(playedFreeGamesCount, expectedPlayedFreeGamesCount);
        }
    }


}
