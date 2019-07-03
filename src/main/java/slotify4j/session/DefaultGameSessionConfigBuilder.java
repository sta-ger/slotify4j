package slotify4j.session;

public class DefaultGameSessionConfigBuilder implements GameSessionConfigBuilder {
    private DefaultGameSessionConfig config;

    public DefaultGameSessionConfigBuilder() {
        config = new DefaultGameSessionConfig();
    }

    public GameSessionConfigBuilder withAvailableBets(long[] value) {
        config.setAvailableBets(value);
        return this;
    }

    public GameSessionConfigBuilder withCreditsAmount(long value) {
        config.setCreditsAmount(value);
        return this;
    }

    public GameSessionConfigBuilder withBet(long value) {
        config.setBet(value);
        return this;
    }

    public DefaultGameSessionConfig build() {
        return config;
    }

}
