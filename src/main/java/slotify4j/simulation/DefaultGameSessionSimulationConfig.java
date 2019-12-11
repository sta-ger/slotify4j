package slotify4j.simulation;

import slotify4j.simulation.playstrategy.PlayStrategy;

public class DefaultGameSessionSimulationConfig implements GameSessionSimulationConfig {
    public static final long DEFAULT_NUMBER_OF_ROUNDS = 1000;

    private long numberOfRounds = DEFAULT_NUMBER_OF_ROUNDS;
    private ChangeBetStrategy changeBetStrategy;
    private PlayStrategy playStrategy;

    @Override
    public void setNumberOfRounds(long value) {
        numberOfRounds = value;
    }

    @Override
    public long getNumberOfRounds() {
        return numberOfRounds;
    }

    @Override
    public void setPlayStrategy(PlayStrategy playStrategy) {
        this.playStrategy = playStrategy;
    }

    @Override
    public PlayStrategy getPlayStrategy() {
        return playStrategy;
    }

    @Override
    public void setChangeBetStrategy(ChangeBetStrategy changeBetStrategy) {
        this.changeBetStrategy = changeBetStrategy;
    }

    @Override
    public ChangeBetStrategy getChangeBetStrategy() {
        return changeBetStrategy;
    }

    public static DefaultGameSessionSimulationBuilder builder() {
        return new DefaultGameSessionSimulationBuilder();
    }

}
