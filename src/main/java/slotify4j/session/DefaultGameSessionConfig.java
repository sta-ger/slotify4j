package slotify4j.session;

public class DefaultGameSessionConfig implements GameSessionConfig {
    private long[] availableBets = {1, 2, 3, 4, 5, 10, 20, 30, 40, 50, 100};

    private long creditsAmount = 1000;

    private long bet;

    public DefaultGameSessionConfig() {
        bet = availableBets[0];
    }

    @Override
    public void setAvailableBets(long[] availableBets) {
        this.availableBets = availableBets;
    }

    @Override
    public long[] getAvailableBets() {
        return availableBets;
    }

    @Override
    public void setCreditsAmount(long creditsAmount) {
        this.creditsAmount = creditsAmount;
    }

    @Override
    public long getCreditsAmount() {
        return creditsAmount;
    }

    @Override
    public void setBet(long bet) {
        this.bet = bet;
    }

    @Override
    public long getBet() {
        return bet;
    }
}
