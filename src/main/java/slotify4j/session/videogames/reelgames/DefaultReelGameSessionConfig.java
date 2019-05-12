package slotify4j.session.videogames.reelgames;

import slotify4j.session.DefaultGameSessionConfig;

public class DefaultReelGameSessionConfig extends DefaultGameSessionConfig implements ReelGameSessionConfig {
    public static final String[] DEFAULT_AVAILABLE_ITEMS = new String[] {
            "A",
            "K",
            "Q",
            "J",
            "10",
            "9",
            "W",
            "S"
    };
    public static final int DEFAULT_REELS_NUMBER = 5;
    public static final int DEFAULT_REELS_ITEMS_NUMBER = 3;
    public static final String DEFAULT_WILD_ITEM_ID = "W";
    public static final String DEFAULT_SCATTER_ITEM_ID = "S";
    public static final int DEFAULT_MINIMUM_ITEMS_NUM_FOR_SCATTER_WIN = 3;

    private int reelsNumber;
    private int reelsItemsNumber;
    private String[] availableItems;
    private String wildItemId;
    private String[][] reelsItemsSequences;
    private final ReelGameSessionPaytableData paytable;
    private ReelGameSessionScatterData[] scatters;
    private ReelGameSessionLinesDirectionData linesDirections;
    private ReelGameSessionWildsMultipliersData wildsMultipliers;

    public DefaultReelGameSessionConfig() {
        reelsNumber = DEFAULT_REELS_NUMBER;
        reelsItemsNumber = DEFAULT_REELS_ITEMS_NUMBER;
        availableItems = DEFAULT_AVAILABLE_ITEMS;
        wildItemId = DEFAULT_WILD_ITEM_ID;
        paytable = new ReelGameSessionPaytableDataImpl(ReelGameSessionPaytableDataImpl.createDefaultPaytableMap(getAvailableBets(), availableItems, reelsNumber, wildItemId));
        scatters = new ReelGameSessionScatterData[]{new ReelGameSessionScattersDataImpl(DEFAULT_SCATTER_ITEM_ID, DEFAULT_MINIMUM_ITEMS_NUM_FOR_SCATTER_WIN)};
        linesDirections = new ReelGameSessionLinesDirectionDataImpl(ReelGameSessionLinesDirectionDataImpl.createDefaultLinesDirectionsMap(reelsNumber, reelsItemsNumber));
        wildsMultipliers = new ReelGameSessionWildsMultipliersDataPowerOfTwo();
    }

    @Override
    public ReelGameSessionPaytableData getPaytable() {
        return null;
    }

    @Override
    public void setPaytable(ReelGameSessionPaytableData paytable) {

    }

    @Override
    public String getWildItemId() {
        return null;
    }

    @Override
    public void setWildItemId(String wildItemId) {

    }

    @Override
    public ReelGameSessionScatterData[] getScattersData() {
        return new ReelGameSessionScatterData[0];
    }

    @Override
    public void setScattersData(ReelGameSessionScatterData[] scattersData) {

    }

    @Override
    public int getReelsNumber() {
        return reelsNumber;
    }

    @Override
    public int setReelsItemsNumber() {
        return 0;
    }

    @Override
    public ReelGameSessionLinesDirectionData getLinesDirections() {
        return null;
    }

    @Override
    public void setLinesDirections(ReelGameSessionLinesDirectionData linesDirections) {

    }

    @Override
    public ReelGameSessionWildsMultipliersData getWildsMultipliers() {
        return null;
    }

    @Override
    public void getWildsMultipliers(ReelGameSessionWildsMultipliersData wildsMultipliers) {

    }

    @Override
    public void setReelsNumber(int reelsNumber) {
        this.reelsNumber = reelsNumber;
    }

    @Override
    public int getReelsItemsNumber() {
        return reelsItemsNumber;
    }

    @Override
    public void setReelsItemsNumber(int reelsItemsNumber) {
        this.reelsItemsNumber = reelsItemsNumber;
    }

    @Override
    public String[] getAvailableItems() {
        return availableItems;
    }

    @Override
    public void setAvailableItems(String[] availableItems) {
        this.availableItems = availableItems;
    }

    @Override
    public String[][] getReelsItemsSequences() {
        return reelsItemsSequences;
    }

    @Override
    public void setReelsItemsSequences(String[][] reelsItemsSequences) {
        this.reelsItemsSequences = reelsItemsSequences;
    }

    @Override
    public boolean isItemWild(String itemId) {
        return false;
    }

    @Override
    public boolean isItemScatter(String itemId) {
        return false;
    }

}
