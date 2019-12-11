package slotify4j.simulation;

import slotify4j.simulation.playstrategy.PlayStrategy;

public class DefaultGameSessionSimulationBuilder {
    private final DefaultGameSessionSimulationConfig config;

    public DefaultGameSessionSimulationBuilder() {
        config = new DefaultGameSessionSimulationConfig();
    }

    public DefaultGameSessionSimulationBuilder withNumberOfRounds(long value) {
        config.setNumberOfRounds(value);
        return this;
    }

    public DefaultGameSessionSimulationBuilder withChangeBetStrategy(ChangeBetStrategy value) {
        config.setChangeBetStrategy(value);
        return this;
    }

    public DefaultGameSessionSimulationBuilder withPlayStrategy(PlayStrategy value) {
        config.setPlayStrategy(value);
        return this;
    }

    public GameSessionSimulationConfig build() {
        return config;
    }

}
