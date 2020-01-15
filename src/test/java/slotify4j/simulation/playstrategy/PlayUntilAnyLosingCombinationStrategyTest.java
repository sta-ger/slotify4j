package slotify4j.simulation.playstrategy;

import org.junit.jupiter.api.Test;
import slotify4j.session.GameSession;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class PlayUntilAnyLosingCombinationStrategyTest {

    @Test
    public void canPlayNextGame() {
        PlayUntilAnyLosingCombinationStrategy strategy = new PlayUntilAnyLosingCombinationStrategy();

        AtomicLong winAmount = new AtomicLong(0);
        GameSession sessionMock = new GameSessionReturnSpecifiedWinMock(winAmount);

        assertFalse(strategy.canPlayNextGame(sessionMock));
        winAmount.set(100);
        assertTrue(strategy.canPlayNextGame(sessionMock));
    }

}
