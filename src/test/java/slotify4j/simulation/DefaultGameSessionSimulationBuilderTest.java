package slotify4j.simulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultGameSessionSimulationBuilderTest {

    @Test
    public void testBuilder() {
        GameSessionSimulationConfig conf = DefaultGameSessionSimulationConfig
                .builder()
                .withNumberOfRounds(100500)
                .withChangeBetScenario(ChangeBetScenario.CHANGE_RANDOMLY)
                .build();
        assertEquals(conf.getChangeBetScenario(), ChangeBetScenario.CHANGE_RANDOMLY);
        assertEquals(conf.getNumberOfRounds(), 100500);
    }

}
