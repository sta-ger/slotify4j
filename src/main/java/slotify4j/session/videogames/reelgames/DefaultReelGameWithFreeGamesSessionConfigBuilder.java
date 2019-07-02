package slotify4j.session.videogames.reelgames;

public class DefaultReelGameWithFreeGamesSessionConfigBuilder {
    private final DefaultReelGameWithFreeGamesSessionConfig config;

    public DefaultReelGameWithFreeGamesSessionConfigBuilder() {
        config = new DefaultReelGameWithFreeGamesSessionConfig();
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withFreeGamesForScatters(
            String itemId,
            int numberOfItems,
            int freeGamesNum
    ) {
        config.setFreeGamesForScatters(itemId, numberOfItems, freeGamesNum);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withAvailableBets(long[] value) {
        config.setAvailableBets(value);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withCreditsAmount(long value) {
        config.setCreditsAmount(value);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withBet(long value) {
        config.setBet(value);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withReelsNumber(int value) {
        config.setReelsNumber(value);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withReelsItemsNumber(int value) {
        config.setReelsItemsNumber(value);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withAvailableItems(String[] value) {
        config.setAvailableItems(value);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withReelsItemsSequences(String[][] value) {
        config.setReelsItemsSequences(value);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withPaytable(ReelGameSessionPaytableData value) {
        config.setPaytable(value);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withWildItemId(String value) {
        config.setWildItemId(value);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withScattersData(ReelGameSessionScatterData[] value) {
        config.setScattersData(value);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withLinesDirections(
            ReelGameSessionLinesDirectionData value
    ) {
        config.setLinesDirections(value);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfigBuilder withWildsMultipliers(
            ReelGameSessionWildsMultipliersData value
    ) {
        config.setWildsMultipliers(value);
        return this;
    }

    public DefaultReelGameWithFreeGamesSessionConfig build() {
        return config;
    }

}
