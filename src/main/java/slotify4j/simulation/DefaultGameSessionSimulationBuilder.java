package slotify4j.simulation;

public class DefaultGameSessionSimulationBuilder {
    private final DefaultGameSessionSimulationConfig config;

    public DefaultGameSessionSimulationBuilder() {
        config = new DefaultGameSessionSimulationConfig();
    }

    public DefaultGameSessionSimulationBuilder withNumberOfRounds(long value) {
        config.setNumberOfRounds(value);
        return this;
    }

    public DefaultGameSessionSimulationBuilder withChangeBetStrategy(GameSessionSimulationChangeBetStrategy value) {
        config.setChangeBetStrategy(value);
        return this;
    }

    public DefaultGameSessionSimulationBuilder withPlayStrategy(GameSessionSimulationPlayStrategy value) {
        config.setPlayStrategy(value);
        return this;
    }

    public GameSessionSimulationConfig build() {
        return config;
    }

}
