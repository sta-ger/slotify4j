package slotify4j.session.videogames.reelgames;

import slotify4j.session.GameSessionConfigBuilder;

public class DefaultReelGameWithFreeGamesSessionConfigBuilder
        implements ReelGameWithFreeGamesSessionConfigBuilder, ReelGameSessionConfigBuilder, GameSessionConfigBuilder {
    private final DefaultReelGameWithFreeGamesSessionConfig config;

    DefaultReelGameWithFreeGamesSessionConfigBuilder() {
        config = new DefaultReelGameWithFreeGamesSessionConfig();
    }

    public ReelGameWithFreeGamesSessionConfigBuilder withFreeGamesForScatters(
            String itemId,
            int numberOfItems,
            int freeGamesNum
    ) {
        config.setFreeGamesForScatters(itemId, numberOfItems, freeGamesNum);
        return this;
    }

    public GameSessionConfigBuilder withAvailableBets(long[] value) {
        config.setAvailableBets(value);
        return this;
    }

    public GameSessionConfigBuilder withCreditsAmount(long value) {
        config.setCreditsAmount(value);
        return this;
    }

    public GameSessionConfigBuilder withBet(long value) {
        config.setBet(value);
        return this;
    }

    public ReelGameSessionConfigBuilder withReelsNumber(int value) {
        config.setReelsNumber(value);
        return this;
    }

    public ReelGameSessionConfigBuilder withReelsItemsNumber(int value) {
        config.setReelsItemsNumber(value);
        return this;
    }

    public ReelGameSessionConfigBuilder withAvailableItems(String[] value) {
        config.setAvailableItems(value);
        return this;
    }

    public ReelGameSessionConfigBuilder withReelsItemsSequences(String[][] value) {
        config.setReelsItemsSequences(value);
        return this;
    }

    public ReelGameSessionConfigBuilder withPaytable(ReelGameSessionPaytableData value) {
        config.setPaytable(value);
        return this;
    }

    public ReelGameSessionConfigBuilder withWildItemId(String value) {
        config.setWildItemId(value);
        return this;
    }

    public ReelGameSessionConfigBuilder withScattersData(ReelGameSessionScatterData[] value) {
        config.setScattersData(value);
        return this;
    }

    public ReelGameSessionConfigBuilder withLinesDirections(
            ReelGameSessionLinesDirectionData value
    ) {
        config.setLinesDirections(value);
        return this;
    }

    public ReelGameSessionConfigBuilder withWildsMultipliers(
            ReelGameSessionWildsMultipliersData value
    ) {
        config.setWildsMultipliers(value);
        return this;
    }

    public ReelGameWithFreeGamesSessionConfig build() {
        return config;
    }

}
