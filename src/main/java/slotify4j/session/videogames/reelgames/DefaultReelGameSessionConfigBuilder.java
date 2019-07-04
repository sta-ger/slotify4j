package slotify4j.session.videogames.reelgames;

public class DefaultReelGameSessionConfigBuilder implements ReelGameSessionConfigBuilder {
    private final DefaultReelGameSessionConfig config;

    DefaultReelGameSessionConfigBuilder() {
        config = new DefaultReelGameSessionConfig();
    }

    public DefaultReelGameSessionConfigBuilder withAvailableBets(long[] value) {
        config.setAvailableBets(value.clone());
        return this;
    }

    public DefaultReelGameSessionConfigBuilder withCreditsAmount(long value) {
        config.setCreditsAmount(value);
        return this;
    }

    public DefaultReelGameSessionConfigBuilder withBet(long value) {
        config.setBet(value);
        return this;
    }

    public DefaultReelGameSessionConfigBuilder withReelsNumber(int value) {
        config.setReelsNumber(value);
        return this;
    }

    public DefaultReelGameSessionConfigBuilder withReelsItemsNumber(int value) {
        config.setReelsItemsNumber(value);
        return this;
    }

    public DefaultReelGameSessionConfigBuilder withAvailableItems(String[] value) {
        config.setAvailableItems(value);
        return this;
    }

    public DefaultReelGameSessionConfigBuilder withReelsItemsSequences(String[][] value) {
        config.setReelsItemsSequences(value);
        return this;
    }

    public DefaultReelGameSessionConfigBuilder withPaytable(ReelGameSessionPaytableData value) {
        config.setPaytable(value);
        return this;
    }
    public DefaultReelGameSessionConfigBuilder withWildItemId(String value) {
        config.setWildItemId(value);
        return this;
    }
    public DefaultReelGameSessionConfigBuilder withScattersData(ReelGameSessionScatterData[] value) {
        config.setScattersData(value.clone());
        return this;
    }

    public DefaultReelGameSessionConfigBuilder withLinesDirections(ReelGameSessionLinesDirectionData value) {
        config.setLinesDirections(value);
        return this;
    }
    public DefaultReelGameSessionConfigBuilder withWildsMultipliers(ReelGameSessionWildsMultipliersData value) {
        config.setWildsMultipliers(value);
        return this;
    }

    public ReelGameSessionConfig build() {
        return config;
    }

}
