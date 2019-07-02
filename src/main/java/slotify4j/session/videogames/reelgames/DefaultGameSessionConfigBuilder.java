package slotify4j.session.videogames.reelgames;

import slotify4j.session.DefaultGameSessionConfig;

public class DefaultGameSessionConfigBuilder {
    private DefaultGameSessionConfig config;

    public DefaultGameSessionConfigBuilder() {
        config = new DefaultGameSessionConfig();
    }

    public DefaultGameSessionConfigBuilder withAvailableBets(long[] value) {
        config.setAvailableBets(value);
        return this;
    }

    public DefaultGameSessionConfigBuilder withCreditsAmount(long value) {
        config.setCreditsAmount(value);
        return this;
    }

    public DefaultGameSessionConfigBuilder withBet(long value) {
        config.setBet(value);
        return this;
    }

    public DefaultGameSessionConfig build() {
        return config;
    }

}
