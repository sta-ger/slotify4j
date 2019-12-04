package slotify4j.simulation;

public interface GameSessionSimulationConfig {

    void setNumberOfRounds(long value);

    long getNumberOfRounds();

    void setPlayStrategy(GameSessionSimulationPlayStrategy changeBetScenario);

    GameSessionSimulationPlayStrategy getPlayStrategy();

    void setChangeBetStrategy(GameSessionSimulationChangeBetStrategy changeBetScenario);

    GameSessionSimulationChangeBetStrategy getChangeBetStrategy();

}
