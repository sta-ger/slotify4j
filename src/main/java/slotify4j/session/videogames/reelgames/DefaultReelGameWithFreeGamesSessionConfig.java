package slotify4j.session.videogames.reelgames;

import java.util.HashMap;
import java.util.Map;

public class DefaultReelGameWithFreeGamesSessionConfig implements ReelGameWithFreeGamesSessionConfig {
    public static final int DEFAULT_FREE_GAMES_FOR_SCATTERS_NUM = 10;

    private final DefaultReelGameSessionConfig baseConfig;
    private final Map<String, Map<Integer, Integer>> freeGamesForScattersMap;

    public DefaultReelGameWithFreeGamesSessionConfig() {
        baseConfig = new DefaultReelGameSessionConfig();
        this.freeGamesForScattersMap = ReelGameWithFreeGamesSessionConfig.createDefaultFreeGamesForScattersMap();
    }

    @Override
    public int getFreeGamesForScatters(String itemId, int numberOfItems) {
        return freeGamesForScattersMap.getOrDefault(itemId, new HashMap<>()).getOrDefault(numberOfItems, 0);
    }

    @Override
    public void setFreeGamesForScatters(String itemId, int numberOfItems, int freeGamesNum) {
        if (!freeGamesForScattersMap.containsKey(itemId)) {
            freeGamesForScattersMap.put(itemId, new HashMap<>());
        }
        freeGamesForScattersMap.get(itemId).put(numberOfItems, freeGamesNum);
    }

    @Override
    public boolean isItemWild(String itemId) {
        return baseConfig.isItemWild(itemId);
    }

    @Override
    public boolean isItemScatter(String itemId) {
        return baseConfig.isItemScatter(itemId);
    }

    @Override
    public void setAvailableBets(long[] availableBets) {
        baseConfig.setAvailableBets(availableBets.clone());
    }

    @Override
    public long[] getAvailableBets() {
        return baseConfig.getAvailableBets().clone();
    }

    @Override
    public ReelGameSessionPaytableData getPaytable() {
        return baseConfig.getPaytable();
    }

    @Override
    public void setPaytable(ReelGameSessionPaytableData paytable) {
        baseConfig.setPaytable(paytable);
    }

    @Override
    public String getWildItemId() {
        return baseConfig.getWildItemId();
    }

    @Override
    public void setWildItemId(String wildItemId) {
        baseConfig.setWildItemId(wildItemId);
    }

    @Override
    public ReelGameSessionScatterData[] getScattersData() {
        return baseConfig.getScattersData();
    }

    @Override
    public void setScattersData(ReelGameSessionScatterData[] scattersData) {
        baseConfig.setScattersData(scattersData.clone());
    }

    @Override
    public void setCreditsAmount(long creditsAmount) {
        baseConfig.setCreditsAmount(creditsAmount);
    }

    @Override
    public long getCreditsAmount() {
        return baseConfig.getCreditsAmount();
    }

    @Override
    public void setBet(long bet) {
        baseConfig.setBet(bet);
    }

    @Override
    public long getBet() {
        return baseConfig.getBet();
    }

    @Override
    public int getReelsNumber() {
        return baseConfig.getReelsNumber();
    }

    @Override
    public void setReelsNumber(int reelsNumber) {
        baseConfig.setReelsNumber(reelsNumber);
    }

    @Override
    public int getReelsItemsNumber() {
        return baseConfig.getReelsItemsNumber();
    }

    @Override
    public void setReelsItemsNumber(int reelsItemsNumber) {
        baseConfig.setReelsItemsNumber(reelsItemsNumber);
    }

    @Override
    public ReelGameSessionLinesDirectionData getLinesDirections() {
        return baseConfig.getLinesDirections();
    }

    @Override
    public void setLinesDirections(ReelGameSessionLinesDirectionData linesDirections) {
        baseConfig.setLinesDirections(linesDirections);
    }

    @Override
    public ReelGameSessionWildsMultipliersData getWildsMultipliers() {
        return baseConfig.getWildsMultipliers();
    }

    @Override
    public void setWildsMultipliers(ReelGameSessionWildsMultipliersData wildsMultipliers) {
        baseConfig.setWildsMultipliers(wildsMultipliers);
    }

    @Override
    public String[] getAvailableItems() {
        return baseConfig.getAvailableItems().clone();
    }

    @Override
    public void setAvailableItems(String[] availableItems) {
        baseConfig.setAvailableItems(availableItems.clone());
    }

    @Override
    public String[][] getReelsItemsSequences() {
        return baseConfig.getReelsItemsSequences().clone();
    }

    @Override
    public void setReelsItemsSequences(String[][] reelsItemsSequences) {
        baseConfig.setReelsItemsSequences(reelsItemsSequences.clone());
    }

    public static class Builder {
        private final DefaultReelGameWithFreeGamesSessionConfig config;

        public Builder() {
            config = new DefaultReelGameWithFreeGamesSessionConfig();
        }

        public Builder withFreeGamesForScatters(String itemId, int numberOfItems, int freeGamesNum) {
            config.setFreeGamesForScatters(itemId, numberOfItems, freeGamesNum);
            return this;
        }

        public Builder withAvailableBets(long[] value) {
            config.setAvailableBets(value);
            return this;
        }

        public Builder withCreditsAmount(long value) {
            config.setCreditsAmount(value);
            return this;
        }

        public Builder withBet(long value) {
            config.setBet(value);
            return this;
        }

        public Builder withReelsNumber(int value) {
            config.setReelsNumber(value);
            return this;
        }

        public Builder withReelsItemsNumber(int value) {
            config.setReelsItemsNumber(value);
            return this;
        }

        public Builder withAvailableItems(String[] value) {
            config.setAvailableItems(value);
            return this;
        }

        public Builder withReelsItemsSequences(String[][] value) {
            config.setReelsItemsSequences(value);
            return this;
        }

        public Builder withPaytable(ReelGameSessionPaytableData value) {
            config.setPaytable(value);
            return this;
        }

        public Builder withWildItemId(String value) {
            config.setWildItemId(value);
            return this;
        }

        public Builder withScattersData(ReelGameSessionScatterData[] value) {
            config.setScattersData(value);
            return this;
        }

        public Builder withLinesDirections(ReelGameSessionLinesDirectionData value) {
            config.setLinesDirections(value);
            return this;
        }

        public Builder withWildsMultipliers(ReelGameSessionWildsMultipliersData value) {
            config.setWildsMultipliers(value);
            return this;
        }

        public DefaultReelGameWithFreeGamesSessionConfig build() {
            return config;
        }

    }

}
