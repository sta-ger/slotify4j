package slotify4j.simulation;

public class GameSessionSimulationConfigImpl implements GameSessionSimulationConfig {
    public static long DEFAULT_NUMBER_OF_ROUNDS = 1000;
    public static ChangeBetScenario DEFAULT_CHANGE_BET_SCENARIO = ChangeBetScenario.DontChange;

    private long numberOfRounds = DEFAULT_NUMBER_OF_ROUNDS;
    private ChangeBetScenario changeBetScenario = DEFAULT_CHANGE_BET_SCENARIO;

    @Override
    public void setNumberOfRounds(long value) {
        numberOfRounds = value;
    }

    @Override
    public long getNumberOfRounds() {
        return numberOfRounds;
    }

    @Override
    public void setChangeBetScenario(ChangeBetScenario changeBetScenario) {
        this.changeBetScenario = changeBetScenario;
    }

    @Override
    public ChangeBetScenario getChangeBetScenario() {
        return changeBetScenario;
    }

    public static class Builder {
        private GameSessionSimulationConfigImpl config;

        public Builder() {
            config = new GameSessionSimulationConfigImpl();
        }

        public Builder withNumberOfRounds(long value) {
            config.setNumberOfRounds(value);
            return this;
        }

        public Builder withChangeBetScenario(ChangeBetScenario value) {
            config.setChangeBetScenario(value);
            return this;
        }

        public GameSessionSimulationConfig build() {
            return config;
        }

    }
}
