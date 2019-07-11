package slotify4j.simulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultGameSessionSimulationConfigTest {

    @Test
    public void createDefaultSimulationConfig() {
        DefaultGameSessionSimulationConfig conf = new DefaultGameSessionSimulationConfig();
        assertEquals(conf.getNumberOfRounds(), DefaultGameSessionSimulationConfig.DEFAULT_NUMBER_OF_ROUNDS);
        assertEquals(conf.getChangeBetScenario(), DefaultGameSessionSimulationConfig.DEFAULT_CHANGE_BET_SCENARIO);
    }

    @Test
    public void createCustomSimulationConfig() {
        DefaultGameSessionSimulationConfig conf = new DefaultGameSessionSimulationConfig();
        conf.setNumberOfRounds(999);
        conf.setChangeBetScenario(ChangeBetScenario.CHANGE_RANDOMLY);
        assertEquals(conf.getNumberOfRounds(), 999);
        assertEquals(conf.getChangeBetScenario(), ChangeBetScenario.CHANGE_RANDOMLY);
    }

}
