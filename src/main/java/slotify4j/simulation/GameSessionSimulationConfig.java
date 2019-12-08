package slotify4j.simulation;

public interface GameSessionSimulationConfig {

    void setNumberOfRounds(long value);

    long getNumberOfRounds();

    void setPlayStrategy(GameSessionSimulationPlayStrategy playStrategy);

    GameSessionSimulationPlayStrategy getPlayStrategy();

    void setChangeBetStrategy(GameSessionSimulationChangeBetStrategy changeBetStrategy);

    GameSessionSimulationChangeBetStrategy getChangeBetStrategy();

}
