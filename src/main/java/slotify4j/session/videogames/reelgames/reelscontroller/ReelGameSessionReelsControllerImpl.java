package slotify4j.session.videogames.reelgames.reelscontroller;

import java.util.Map;

public class ReelGameSessionReelsControllerImpl implements ReelGameSessionReelsController {
    public static Object[][] transposeMatrix(Object[][] source) {
        Object[][] tmp = new Object[source[0].length][source.length];
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                tmp[j][i] = source[i][j];
            }
        }
        return tmp;
    }

    public static String[] createItemsSequence(String[] availableItems) {
        return null;
    }

    public static String[] createItemsSequence(String[] availableItems, Map<String, Integer> countsOfItems) {
        return null;
    }

    public static String[] createItemsSequence(String[] availableItems, int countOfItems) {
        return null;
    }

    private final int reelsNumber;
    private final int reelsItemsNumber;
    private final String[][] reelsSequences;

    ReelGameSessionReelsControllerImpl(ReelGameSessionReelsControllerConfig config) {
        reelsNumber = config.getReelsNumber();
        reelsItemsNumber = config.getReelsItemsNumber();
        reelsSequences = config.getReelsItemsSequences();
    }

    @Override
    public String[] getRandomItemsCombination() {
        return new String[0];
    }

    @Override
    public String[] getRandomReelItems(int reelId) {
        return new String[0];
    }

    @Override
    public String getRandomItem(int reelId) {
        return null;
    }

}
