package slotify4j.simulation;

public class GameSessionSimulationConfigImpl implements GameSessionSimulationConfig {
    private long numberOfRounds = 1000;
    private ChangeBetScenario changeBetScenario = ChangeBetScenario.DontChange;

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
}
