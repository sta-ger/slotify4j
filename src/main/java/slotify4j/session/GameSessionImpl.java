package slotify4j.session;

import java.util.stream.LongStream;

public class GameSessionImpl implements GameSession {
    private final GameSessionConfig config;
    private long bet;
    private long credits;

    public GameSessionImpl(GameSessionConfig config) {
        this.config = config;
        bet = isBetAvailable(config.getBet()) ? config.getBet() : config.getAvailableBets()[0];
        credits = config.getCreditsAmount();
    }

    @Override
    public long getCreditsAmount() {
        return credits;
    }

    @Override
    public void setCreditsAmount(long creditsAmount) {
        credits = creditsAmount;
    }

    @Override
    public long getWinningAmount() {
        return 0;
    }

    @Override
    public long[] getAvailableBets() {
        return config.getAvailableBets();
    }

    @Override
    public boolean isBetAvailable(long bet) {
        return LongStream.of(config.getAvailableBets()).anyMatch(x -> x == bet);
    }

    @Override
    public long getBet() {
        return bet;
    }

    @Override
    public void setBet(long bet) {
        this.bet = bet;
    }

    @Override
    public boolean canPlayNextGame() {
        return credits >= bet;
    }

    @Override
    public void play() {
        if (canPlayNextGame()) {
            credits -= bet;
        }
    }
}
