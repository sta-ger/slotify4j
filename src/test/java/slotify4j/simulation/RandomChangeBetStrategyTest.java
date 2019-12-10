package slotify4j.simulation;

import org.junit.jupiter.api.Test;
import slotify4j.session.UnableToPlayException;
import slotify4j.session.videogames.reelgames.DefaultReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSessionImpl;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsController;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerImpl;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculator;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorImpl;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RandomChangeBetStrategyTest {

    @Test
    public void setBetForPlay() throws UnableToPlayException {
        ReelGameSessionConfig sessionConfig = new DefaultReelGameSessionConfig();
        ReelGameSessionReelsController reelsController = new ReelGameSessionReelsControllerImpl(sessionConfig);
        ReelGameSessionWinCalculator winningCalculator = new ReelGameSessionWinCalculatorImpl(sessionConfig);
        ReelGameSessionImpl session = new ReelGameSessionImpl(sessionConfig, reelsController, winningCalculator);

        GameSessionSimulationConfig simulationConfig = DefaultGameSessionSimulationConfig
                .builder()
                .withChangeBetStrategy(new RandomChangeBetStrategy())
                .build();
        GameSessionSimulation simulation = new GameSessionSimulationImpl(session, simulationConfig);

        HashSet<Long> playedBets = new HashSet<>();
        simulation.setBeforePlayCallback(() -> session.setCreditsAmount(1000000));
        simulation.setAfterPlayCallback(() -> playedBets.add(session.getBet()));

        simulation.run();

        assertEquals(playedBets.size(), session.getAvailableBets().length);

        // After simulation playedBets should contain shuffled set of bets
        assertFalse(
                Arrays.equals(
                        session.getAvailableBets(),
                        playedBets.stream().mapToLong(Number::longValue).toArray()
                )
        );

    }

}
