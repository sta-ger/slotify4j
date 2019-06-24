package slotify4j.simulation;

public interface GameSessionSimulation {

    void run();

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
