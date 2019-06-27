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
        this.availableBets = availableBets.clone();
    }

    @Override
    public long[] getAvailableBets() {
        return availableBets.clone();
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

    public static class Builder {
        private GameSessionConfig config;

        public Builder() {
            config = new DefaultGameSessionConfig();
        }

        public Builder withAvailableBets(long[] value) {
            config.setAvailableBets(value);
            return this;
        }

        public Builder withCreditsAmount(long value) {
            config.setCreditsAmount(value);
            return this;
        }

        public Builder withBet(long value) {
            config.setBet(value);
            return this;
        }

        public GameSessionConfig build() {
            return config;
        }

    }

}
