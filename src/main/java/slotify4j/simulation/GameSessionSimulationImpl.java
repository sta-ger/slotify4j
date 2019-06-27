package slotify4j.simulation;

import slotify4j.session.videogames.reelgames.ReelGameSession;

public class GameSessionSimulationImpl implements GameSessionSimulation {

    public GameSessionSimulationImpl(ReelGameSession session, GameSessionSimulationConfig simulationConfig) {

    }

    @Override
    public void run() {
        // Not implemented
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
        // Not implemented
    }

    @Override
    public void removeBeforePlayCallback() {
        // Not implemented
    }

    @Override
    public void setAfterPlayCallback(Runnable callback) {
        // Not implemented
    }

    @Override
    public void removeAfterPlayCallback() {
        // Not implemented
    }

    @Override
    public void setOnFinishedCallback(Runnable callback) {
        // Not implemented
    }

    @Override
    public void removeOnFinishedCallback() {
        // Not implemented
    }
}
