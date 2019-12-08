package slotify4j.simulation;

import org.junit.jupiter.api.Test;
import slotify4j.session.GameSession;
import slotify4j.session.UnableToPlayException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DefaultGameSessionSimulationBuilderTest {

    @Test
    public void testBuilder() {
        GameSessionSimulationConfig conf = DefaultGameSessionSimulationConfig
                .builder()
                .withNumberOfRounds(100500)
                .withChangeBetStrategy(new GameSessionSimulationChangeBetStrategy() {
                    @Override
                    public void setBetForPlay(GameSession session) {

                    }
                })
                .withPlayStrategy(new GameSessionSimulationPlayStrategy() {
                    @Override
                    public void run() throws UnableToPlayException {

                    }
                })
                .build();
        assertNotNull(conf.getChangeBetStrategy());
        assertNotNull(conf.getPlayStrategy());
        assertEquals(conf.getNumberOfRounds(), 100500);
    }

}
