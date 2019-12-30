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

        GameSession sessionMock = new GameSession() {
            @Override
            public long getCreditsAmount() {
                return 0;
            }

            @Override
            public void setCreditsAmount(long creditsAmount) {
                /* no-op */
            }

            @Override
            public long getWinningAmount() {
                return winAmount.get();
            }

            @Override
            public long[] getAvailableBets() {
                return new long[0];
            }

            @Override
            public boolean isBetAvailable(long bet) {
                return false;
            }

            @Override
            public long getBet() {
                return 0;
            }

            @Override
            public void setBet(long bet) {
                /* no-op */
            }

            @Override
            public boolean canPlayNextGame() {
                return false;
            }

            @Override
            public void play() {
                /* no-op */
            }
        };

        assertFalse(strategy.canPlayNextGame(sessionMock));
        winAmount.set(100);
        assertTrue(strategy.canPlayNextGame(sessionMock));
    }

}