package slotify4j.simulation;

public interface GameSessionSimulationConfig {

    void setNumberOfRounds(long value);

    long getNumberOfRounds();

    void setChangeBetScenario(ChangeBetScenario changeBetScenario);

    ChangeBetScenario getChangeBetScenario();

}
