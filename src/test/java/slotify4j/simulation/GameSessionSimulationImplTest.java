package slotify4j.simulation;

import org.junit.jupiter.api.Test;
import slotify4j.session.videogames.reelgames.DefaultReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSession;
import slotify4j.session.videogames.reelgames.ReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSessionImpl;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsController;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerImpl;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculator;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorImpl;

import static org.junit.jupiter.api.Assertions.*;

public class GameSessionSimulationImplTest {

    @Test
    void playSpecifiedNumOfRoundsAndCalculateRtpTest() {
        ReelGameSessionConfig sessionConfig = new DefaultReelGameSessionConfig();
        sessionConfig.setCreditsAmount(10000);
        sessionConfig.setReelsItemsSequences(new String[]{
                new String{"J", "9", "Q", "10", "A", "S", "K"},
                new String{"K", "S", "10", "A", "9", "Q", "J"},
                new String{"J", "Q", "10", "9", "S", "A", "K"},
                new String{"Q", "10", "9", "S", "K", "A", "J"},
                new String{"Q", "A", "J", "10", "9", "S", "K"},
        });

        ReelGameSessionReelsController reelsController = new ReelGameSessionReelsControllerImpl(sessionConfig);
        ReelGameSessionWinCalculator winningCalculator = new ReelGameSessionWinCalculatorImpl(sessionConfig);
        ReelGameSession session = new ReelGameSessionImpl(sessionConfig, reelsController, winningCalculator);
        GameSessionSimulationConfig simulationConfig = new GameSessionSimulationConfigImpl.Builder()
                .withNumberOfRounds(10000)
                .build();
        GameSessionSimulation simulation = new GameSessionSimulationImpl(simulationConfig);
    }

}
