package slotify4j.simulation;

import slotify4j.session.UnableToPlayException;

public interface GameSessionSimulation {

    void run() throws UnableToPlayException;

    void run(GameSessionSimulationStrategy strategy) throws UnableToPlayException;

    double getRtp();

    long getTotalBetAmount();

    long getTotalReturn();

    long getCurrentGameNumber();

    long getTotalGamesToPlayNumber();

    void setBeforePlayCallback(Runnable callback);

    void removeBeforePlayCallback();

    void setAfterPlayCallback(Runnable callback);

    void removeAfterPlayCallback();

    void setOnFinishedCallback(Runnable callback);

    void removeOnFinishedCallback();

}
