package slotify4j.simulation;

import org.junit.jupiter.api.Test;
import slotify4j.session.GameSession;
import slotify4j.simulation.playstrategy.PlayStrategy;

import static org.junit.jupiter.api.Assertions.*;

class DefaultGameSessionSimulationConfigTest {

    @Test
    public void createDefaultSimulationConfig() {
        DefaultGameSessionSimulationConfig conf = new DefaultGameSessionSimulationConfig();
        assertEquals(conf.getNumberOfRounds(), DefaultGameSessionSimulationConfig.DEFAULT_NUMBER_OF_ROUNDS);
        assertNull(conf.getChangeBetStrategy());
        assertNull(conf.getPlayStrategy());
    }

    @Test
    public void createCustomSimulationConfig() {
        DefaultGameSessionSimulationConfig conf = new DefaultGameSessionSimulationConfig();
        conf.setNumberOfRounds(999);
        conf.setChangeBetStrategy(new ChangeBetStrategy() {
            @Override
            public void setBetForPlay(GameSession session) {

            }
        });
        conf.setPlayStrategy(new PlayStrategy() {
            @Override
            public boolean canPlayNextGame(GameSession session) {
                return false;
            }
        });
        assertEquals(conf.getNumberOfRounds(), 999);
        assertNotNull(conf.getChangeBetStrategy());
        assertNotNull(conf.getPlayStrategy());
    }

}
