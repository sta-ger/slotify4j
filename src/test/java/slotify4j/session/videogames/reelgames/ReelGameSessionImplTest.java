package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;
import slotify4j.session.GameSessionConfig;
import slotify4j.session.GameSessionImplTest;
import slotify4j.session.UnableToPlayException;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerImpl;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorImpl;

import static org.junit.jupiter.api.Assertions.*;

class ReelGameSessionImplTest {

    public static boolean testDefaultReelGameSessionHasProperInitialValues(ReelGameSession session, ReelGameSessionConfig config) {
        boolean flag = false;
        try {
            assertEquals(session.getWinningAmount(), 0);
            assertEquals(session.getPaytable(), config.getPaytable());
            assertEquals(session.getReelsItemsSequences().length, config.getReelsItemsSequences().length);
            assertEquals(session.getReelsItemsNumber(), config.getReelsItemsNumber());
            assertEquals(session.getReelsNumber(), config.getReelsNumber());
            assertArrayEquals(session.getAvailableItems(), config.getAvailableItems());
            assertEquals(session.getReelsItems().length, 0);
            assertEquals(session.getWinningLines().size(), 0);
            assertEquals(session.getWinningScatters().size(), 0);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean testPlayUntilWin(ReelGameSession session, ReelGameSessionConfig config) throws UnableToPlayException {
        boolean flag = false;

        try {
            long lastBet = 0;
            long lastCredits = 0;
            boolean wasLinesWin = false;
            boolean wasScattersWin = false;

            int timesToPlay = 1000;
            for (int i = 0; i < timesToPlay; i++) {
                while (session.getWinningAmount() == 0 || wasLinesWin || wasScattersWin) {
                    wasLinesWin = false;
                    wasScattersWin = false;
                    lastCredits = session.getCreditsAmount();
                    lastBet = session.getBet();
                    session.play();
                    if (session.getWinningAmount() == 0) {
                        assertEquals(session.getCreditsAmount(), lastCredits - lastBet);
                    } else if (!session.canPlayNextGame()) {
                        session.setCreditsAmount(Integer.MAX_VALUE);
                    }
                }
                assertTrue(session.getCreditsAmount() >= lastCredits - lastBet);

                wasLinesWin = session.getWinningLines().size() > 0;
                if (wasLinesWin) {
                    session.getWinningLines().forEach((lineId, lineModel) ->
                            assertArrayEquals(
                                    lineModel.getDirection(),
                                    config.getLinesDirections().getVerticalItemsPositionsForLineId(lineModel.getLineId())
                            )
                    );
                }
                wasScattersWin = session.getWinningScatters().size() > 0;
                if (i == timesToPlay - 1 && !wasLinesWin && !wasScattersWin) {
                    i = 0;
                }
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Test
    public void passBaseTests() {
        ReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        ReelGameSessionImpl sess = new ReelGameSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        assertTrue(GameSessionImplTest.testDefaultSessionHasProperInitialValues(sess, conf));

        GameSessionConfig baseConf = GameSessionImplTest.createCustomConfigForTestProperInitialValues();
        conf = DefaultReelGameSessionConfig.builder()
                .withAvailableBets(baseConf.getAvailableBets())
                .withCreditsAmount(baseConf.getCreditsAmount())
                .build();
        sess = new ReelGameSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        assertTrue(GameSessionImplTest.testDefaultSessionHasProperInitialValuesWithCustomConfig(sess, conf));

        baseConf = GameSessionImplTest.createCustomConfigForWrongBetTest();
        conf = DefaultReelGameSessionConfig.builder()
                .withAvailableBets(baseConf.getAvailableBets())
                .build();
        sess = new ReelGameSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        assertTrue(GameSessionImplTest.testDefaultSessionWithWrongInitialBet(sess, conf));
    }

    @Test
    public void testCreateNewSession() {
        ReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        ReelGameSessionImpl sess = new ReelGameSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        assertTrue(testDefaultReelGameSessionHasProperInitialValues(sess, conf));
    }

    @Test
    public void testPlaySeveralTimesUntilAnyWinning() throws UnableToPlayException {
        ReelGameSessionConfig conf = DefaultReelGameSessionConfig.builder()
                .withCreditsAmount(Integer.MAX_VALUE)
                .build();
        ReelGameSessionImpl sess = new ReelGameSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        assertTrue(testPlayUntilWin(sess, conf));
    }

}
