package slotify4j.session.videogames.reelgames;

import slotify4j.session.videogames.reelgames.reelscontroller.ReelsControllerConfig;

import java.util.HashMap;
import java.util.Map;

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

    public static Map<Long, Map<String, Map<Integer, Long>>> createDefaultPaytableMap(long[] availableBets, String[] availableItems, int reelsNumber, String wildItemId) {
        HashMap<Long, Map<String, Map<Integer, Long>>> r = new HashMap<>();
        for (int i = 0; i < availableBets.length; i++) {
            long bet = availableBets[i];
            r.put(bet, new HashMap<>());
            for (int j = 0; j < availableItems.length; j++) {
                String itemId = availableItems[j];
                if (!itemId.equals(wildItemId)) {
                    r.get(bet).put(itemId, new HashMap<>());
                    for (int k = 3; k <= reelsNumber; k++) {
                        r.get(bet).get(itemId).put(k, (k - 2) * bet);
                    }
                }
            }
        }
        return r;
    }
}
