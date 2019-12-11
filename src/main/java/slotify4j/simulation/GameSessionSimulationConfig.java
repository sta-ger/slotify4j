package slotify4j.simulation;

import slotify4j.simulation.playstrategy.PlayStrategy;

public interface GameSessionSimulationConfig {

    void setNumberOfRounds(long value);

    long getNumberOfRounds();

    void setPlayStrategy(PlayStrategy playStrategy);

    PlayStrategy getPlayStrategy();

    void setChangeBetStrategy(ChangeBetStrategy changeBetStrategy);

    ChangeBetStrategy getChangeBetStrategy();

}
