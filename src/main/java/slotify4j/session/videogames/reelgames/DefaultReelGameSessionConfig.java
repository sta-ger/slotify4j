package slotify4j.session.videogames.reelgames;

import slotify4j.session.videogames.reelgames.reelscontroller.ReelsControllerConfig;

public class DefaultReelGameSessionConfig implements ReelGameSessionConfig {
    private int reelsNumber;
    private int reelsItemsNumber;
    private String[] availableItems;
    private String[][] reelsItemsSequences;

    private String wildItemId;

    public DefaultReelGameSessionConfig() {
        fillDefaultReelsParameters(this);
        fillDefaultItemsParameters(this);
    }

    public DefaultReelGameSessionConfig(int reelsNumber, int reelsItemsNumber) {
        this.reelsNumber = reelsNumber;
        this.reelsItemsNumber = reelsItemsNumber;
        fillDefaultItemsParameters(this);
    }

    @Override
    public int getReelsNumber() {
        return reelsNumber;
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

    public static void fillDefaultReelsParameters(ReelsControllerConfig config) {
        config.setReelsNumber(5);
        config.setReelsItemsNumber(3);
    }

    public static void fillDefaultItemsParameters(ReelGameSessionConfig config) {
        config.setAvailableItems(new String[]{
                "A",
                "K",
                "Q",
                "J",
                "10",
                "9",
                "W",
                "S"
        });
    }
}
