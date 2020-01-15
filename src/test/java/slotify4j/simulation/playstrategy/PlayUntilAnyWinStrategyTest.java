package slotify4j.simulation.playstrategy;

import org.junit.jupiter.api.Test;
import slotify4j.session.GameSession;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameSessionReturnSpecifiedWinMock implements GameSession {
    private AtomicLong winAmountReference;

    public GameSessionReturnSpecifiedWinMock(AtomicLong winAmountReference) {
        this.winAmountReference = winAmountReference;
    }

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
        return winAmountReference.get();
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
}

class PlayUntilAnyWinStrategyTest {

    @Test
    public void canPlayNextGame() {
        PlayUntilAnyWinStrategy strategy = new PlayUntilAnyWinStrategy();

        AtomicLong winAmount = new AtomicLong(0);
        GameSession sessionMock = new GameSessionReturnSpecifiedWinMock(winAmount);

        assertTrue(strategy.canPlayNextGame(sessionMock));
        winAmount.set(100);
        assertFalse(strategy.canPlayNextGame(sessionMock));
    }

}
