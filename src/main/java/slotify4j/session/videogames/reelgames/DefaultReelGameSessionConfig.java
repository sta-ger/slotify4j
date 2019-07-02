package slotify4j.session.videogames.reelgames;

import slotify4j.session.DefaultGameSessionConfig;

import java.util.stream.Stream;

public class DefaultReelGameSessionConfig implements ReelGameSessionConfig {
    public static final String DEFAULT_SCATTER_ITEM_ID = "S";
    public static final int DEFAULT_MINIMUM_ITEMS_NUM_FOR_SCATTER_WIN = 3;

    public static final int DEFAULT_REELS_NUMBER = 5;
    public static final int DEFAULT_REELS_ITEMS_NUMBER = 3;
    public static final String DEFAULT_WILD_ITEM_ID = "W";

    private final DefaultGameSessionConfig baseConfig;

    private int reelsNumber;
    private int reelsItemsNumber;
    private String[] availableItems;
    private String wildItemId;
    private String[][] reelsItemsSequences;
    private ReelGameSessionPaytableData paytable;
    private ReelGameSessionScatterData[] scatters;
    private ReelGameSessionLinesDirectionData linesDirections;
    private ReelGameSessionWildsMultipliersData wildsMultipliers;

    public DefaultReelGameSessionConfig() {
        baseConfig = new DefaultGameSessionConfig();
        reelsNumber = DEFAULT_REELS_NUMBER;
        reelsItemsNumber = DEFAULT_REELS_ITEMS_NUMBER;
        availableItems = getDefaultAvailableItems();
        wildItemId = DEFAULT_WILD_ITEM_ID;
        paytable = new ReelGameSessionPaytableDataImpl(ReelGameSessionTools.createDefaultPaytableMap(
                        getAvailableBets(), availableItems, reelsNumber, wildItemId)
        );
        scatters = new ReelGameSessionScatterData[] {
                new ReelGameSessionScattersDataImpl(DEFAULT_SCATTER_ITEM_ID, DEFAULT_MINIMUM_ITEMS_NUM_FOR_SCATTER_WIN)
        };
        linesDirections = new ReelGameSessionLinesDirectionDataImpl(
                ReelGameSessionTools.createDefaultLinesDirectionsMap(reelsNumber, reelsItemsNumber)
        );
        wildsMultipliers = new ReelGameSessionWildsMultipliersDataPowerOfTwo();
        reelsItemsSequences = ReelGameSessionTools.createReelsItemsSequences(reelsNumber, availableItems);
    }

    public static String[] getDefaultAvailableItems() {
        return new String[] {
                "A",
                "K",
                "Q",
                "J",
                "10",
                "9",
                "W",
                "S"
        };
    }

    @Override
    public ReelGameSessionPaytableData getPaytable() {
        return paytable;
    }

    @Override
    public void setPaytable(ReelGameSessionPaytableData paytable) {
        this.paytable = paytable;
    }

    @Override
    public String getWildItemId() {
        return wildItemId;
    }

    @Override
    public void setWildItemId(String wildItemId) {
        this.wildItemId = wildItemId;
    }

    @Override
    public ReelGameSessionScatterData[] getScattersData() {
        return scatters.clone();
    }

    @Override
    public void setScattersData(ReelGameSessionScatterData[] scattersData) {
        this.scatters = scattersData.clone();
    }

    @Override
    public ReelGameSessionLinesDirectionData getLinesDirections() {
        return linesDirections;
    }

    @Override
    public void setLinesDirections(ReelGameSessionLinesDirectionData linesDirections) {
        this.linesDirections = linesDirections;
    }

    @Override
    public ReelGameSessionWildsMultipliersData getWildsMultipliers() {
        return wildsMultipliers;
    }

    @Override
    public void setWildsMultipliers(ReelGameSessionWildsMultipliersData wildsMultipliers) {
        this.wildsMultipliers = wildsMultipliers;
    }

    @Override
    public int getReelsItemsNumber() {
        return reelsItemsNumber;
    }

    @Override
    public void setReelsItemsNumber(int reelsItemsNumber) {
        this.reelsItemsNumber = reelsItemsNumber;
        linesDirections = new ReelGameSessionLinesDirectionDataImpl(
                ReelGameSessionTools.createDefaultLinesDirectionsMap(reelsNumber, reelsItemsNumber)
        );
    }

    @Override
    public int getReelsNumber() {
        return reelsNumber;
    }

    @Override
    public void setReelsNumber(int reelsItemsNumber) {
        this.reelsNumber = reelsItemsNumber;
        linesDirections = new ReelGameSessionLinesDirectionDataImpl(
                ReelGameSessionTools.createDefaultLinesDirectionsMap(reelsNumber, reelsItemsNumber)
        );
    }

    @Override
    public String[] getAvailableItems() {
        return availableItems.clone();
    }

    @Override
    public void setAvailableItems(String[] availableItems) {
        this.availableItems = availableItems.clone();
        paytable = new ReelGameSessionPaytableDataImpl(
                ReelGameSessionTools.createDefaultPaytableMap(
                        getAvailableBets(),
                        availableItems,
                        reelsNumber,
                        wildItemId
                )
        );
        reelsItemsSequences = ReelGameSessionTools.createReelsItemsSequences(reelsNumber, availableItems);
    }

    @Override
    public String[][] getReelsItemsSequences() {
        return reelsItemsSequences.clone();
    }

    @Override
    public void setReelsItemsSequences(String[][] reelsItemsSequences) {
        this.reelsItemsSequences = reelsItemsSequences.clone();
    }

    @Override
    public boolean isItemWild(String itemId) {
        return itemId.equals(wildItemId);
    }

    @Override
    public boolean isItemScatter(String itemId) {
        return Stream.of(scatters).anyMatch(s -> s.getItemId().equals(itemId));
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

    public static DefaultReelGameSessionConfigBuilder builder() {
        return new DefaultReelGameSessionConfigBuilder();
    }

}
