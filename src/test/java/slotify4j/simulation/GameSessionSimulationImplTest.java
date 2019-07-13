package slotify4j.simulation;

import org.junit.jupiter.api.Test;
import slotify4j.session.*;
import slotify4j.session.videogames.reelgames.DefaultReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSession;
import slotify4j.session.videogames.reelgames.ReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSessionImpl;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsController;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerImpl;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculator;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorImpl;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameSessionSimulationImplTest {

    public static ReelGameSession createDefaultReelGameSessionForTest() {
        ReelGameSessionConfig sessionConfig = new DefaultReelGameSessionConfig();
        ReelGameSessionReelsController reelsController = new ReelGameSessionReelsControllerImpl(sessionConfig);
        ReelGameSessionWinCalculator winningCalculator = new ReelGameSessionWinCalculatorImpl(sessionConfig);
        return new ReelGameSessionImpl(sessionConfig, reelsController, winningCalculator);
    }

    @Test
    public void playSpecifiedNumOfRoundsAndCalculateRtpTest() throws UnableToPlayException {
        ReelGameSessionConfig sessionConfig = DefaultReelGameSessionConfig.builder().withReelsItemsSequences(
                new String[][]{
                        {"J", "9", "Q", "10", "A", "S", "K"},
                        {"K", "S", "10", "A", "9", "Q", "J"},
                        {"J", "Q", "10", "9", "S", "A", "K"},
                        {"Q", "10", "9", "S", "K", "A", "J"},
                        {"Q", "A", "J", "10", "9", "S", "K"},
                }).build();
        ReelGameSessionReelsController reelsController = new ReelGameSessionReelsControllerImpl(sessionConfig);
        ReelGameSessionWinCalculator winningCalculator = new ReelGameSessionWinCalculatorImpl(sessionConfig);
        ReelGameSession session = new ReelGameSessionImpl(sessionConfig, reelsController, winningCalculator);
        GameSessionSimulationConfig simulationConfig = DefaultGameSessionSimulationConfig.builder()
                .withNumberOfRounds(10000)
                .build();
        GameSessionSimulation simulation = new GameSessionSimulationImpl(session, simulationConfig);

        AtomicLong totalBet = new AtomicLong();
        AtomicLong totalReturn = new AtomicLong();
        int[] callbacksCounts = {0, 0, 0};
        simulation.setBeforePlayCallback(() -> {
            assertEquals(simulation.getCurrentGameNumber(), callbacksCounts[0]);
            callbacksCounts[0]++;
            session.setCreditsAmount(10000);
        });
        simulation.setAfterPlayCallback(() -> {
            callbacksCounts[1]++;
            totalBet.addAndGet(session.getBet());
            totalReturn.addAndGet(session.getWinningAmount());
        });
        simulation.setOnFinishedCallback(() -> callbacksCounts[2]++);

        simulation.run();

        assertEquals(callbacksCounts[0], simulation.getTotalGamesToPlayNumber());
        assertEquals(callbacksCounts[1], simulation.getTotalGamesToPlayNumber());
        assertEquals(callbacksCounts[2], 1);

        assertEquals(simulation.getTotalBetAmount(), totalBet.get());
        assertEquals(simulation.getTotalReturn(), totalReturn.get());
        assertTrue(simulation.getRtp() > 0.5 && simulation.getRtp() < 0.6);
    }

    @Test
    public void testSetAndRemoveCallbacks() throws UnableToPlayException {
        ReelGameSession session = createDefaultReelGameSessionForTest();
        GameSessionSimulationConfig simulationConfig = DefaultGameSessionSimulationConfig
                .builder()
                .withNumberOfRounds(100)
                .build();
        GameSessionSimulation simulation = new GameSessionSimulationImpl(session, simulationConfig);

        int[] callbacksCounts = {0, 0, 0};
        simulation.setBeforePlayCallback(() -> callbacksCounts[0]++);
        simulation.setAfterPlayCallback(() -> {
                callbacksCounts[1]++;
                if (simulation.getCurrentGameNumber() == 50) {
                    simulation.removeBeforePlayCallback();
                    simulation.removeAfterPlayCallback();
                }
        });
        simulation.setOnFinishedCallback(() -> callbacksCounts[2]++);

        simulation.run();

        assertEquals(callbacksCounts[0], 50);
        assertEquals(callbacksCounts[1], 50);
        assertEquals(callbacksCounts[2], 1);

        callbacksCounts[2] = 0;

        simulation.removeOnFinishedCallback();
        simulation.run();

        assertEquals(callbacksCounts[2], 0);
    }

    @Test
    public void testSetBetBeforePlay() throws UnableToPlayException {
        long[] bets = {1, 10};
        GameSessionConfig config = DefaultGameSessionConfig
                .builder()
                .withAvailableBets(bets)
                .withCreditsAmount(59)
                .withBet(10)
                .build();
        GameSession session = new GameSessionImpl(config);
        GameSessionSimulationConfig simulationConfig = new DefaultGameSessionSimulationConfig();
        GameSessionSimulation simulation = new GameSessionSimulationImpl(session, simulationConfig);

        AtomicBoolean expectLowerBet = new AtomicBoolean();
        simulation.setBeforePlayCallback(() -> {
            if (session.getCreditsAmount() < 10) {
                expectLowerBet.set(true);
            }
        });
        simulation.setAfterPlayCallback(() -> {
            if (expectLowerBet.get()) {
                assertEquals(session.getBet(), 1);
            } else {
                assertEquals(session.getBet(), 10);
            }
        });

        simulation.run();
    }

}
