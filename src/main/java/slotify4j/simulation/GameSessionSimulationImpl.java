package slotify4j.simulation;

public class GameSessionSimulationImpl implements GameSessionSimulation {

    public GameSessionSimulationImpl(GameSessionSimulationConfig config) {

    }

    @Override
    public void run() {

    }

    @Override
    public double getRtp() {
        return 0;
    }

    @Override
    public long getTotalBetAmount() {
        return 0;
    }

    @Override
    public long getTotalReturn() {
        return 0;
    }

    @Override
    public long getCurrentGameNumber() {
        return 0;
    }

    @Override
    public long getTotalGamesToPlayNumber() {
        return 0;
    }

    @Override
    public void setBeforePlayCallback(Runnable callback) {

    }

    @Override
    public void removeBeforePlayCallback() {

    }

    @Override
    public void setAfterPlayCallback(Runnable callback) {

    }

    @Override
    public void removeAfterPlayCallback() {

    }

    @Override
    public void setOnFinishedCallback(Runnable callback) {

    }

    @Override
    public void removeOnFinishedCallback() {

    }
}
