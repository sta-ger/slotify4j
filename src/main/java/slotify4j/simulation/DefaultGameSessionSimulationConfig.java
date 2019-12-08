package slotify4j.simulation;

public class DefaultGameSessionSimulationConfig implements GameSessionSimulationConfig {
    public static final long DEFAULT_NUMBER_OF_ROUNDS = 1000;

    private long numberOfRounds = DEFAULT_NUMBER_OF_ROUNDS;
    private GameSessionSimulationChangeBetStrategy changeBetStrategy;
    private GameSessionSimulationPlayStrategy playStrategy;

    @Override
    public void setNumberOfRounds(long value) {
        numberOfRounds = value;
    }

    @Override
    public long getNumberOfRounds() {
        return numberOfRounds;
    }

    @Override
    public void setPlayStrategy(GameSessionSimulationPlayStrategy playStrategy) {
        this.playStrategy = playStrategy;
    }

    @Override
    public GameSessionSimulationPlayStrategy getPlayStrategy() {
        return playStrategy;
    }

    @Override
    public void setChangeBetStrategy(GameSessionSimulationChangeBetStrategy changeBetStrategy) {
        this.changeBetStrategy = changeBetStrategy;
    }

    @Override
    public GameSessionSimulationChangeBetStrategy getChangeBetStrategy() {
        return changeBetStrategy;
    }

    public static DefaultGameSessionSimulationBuilder builder() {
        return new DefaultGameSessionSimulationBuilder();
    }

}
