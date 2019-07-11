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

    public DefaultGameSessionSimulationBuilder withChangeBetScenario(ChangeBetScenario value) {
        config.setChangeBetScenario(value);
        return this;
    }

    public GameSessionSimulationConfig build() {
        return config;
    }

}
