package slotify4j.simulation;

public class DefaultGameSessionSimulationConfig implements GameSessionSimulationConfig {
    public static final long DEFAULT_NUMBER_OF_ROUNDS = 1000;
    public static final ChangeBetScenario DEFAULT_CHANGE_BET_SCENARIO = ChangeBetScenario.DONT_CHANGE;

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

    public static DefaultGameSessionSimulationBuilder builder() {
        return new DefaultGameSessionSimulationBuilder();
    }

}
