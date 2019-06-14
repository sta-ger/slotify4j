package slotify4j.session.videogames.reelgames.reelscontroller;

import java.util.*;

public interface ReelGameSessionReelsController {

    static String[][] transposeItemsMatrix(String[][] source) {
        String[][] tmp = new String[source[0].length][source.length];
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                tmp[j][i] = source[i][j];
            }
        }
        return tmp;
    }

    static String[] createItemsSequence(String[] availableItems) {
        return createItemsSequence(availableItems, null);
    }

    static String[] createItemsSequence(String[] availableItems, Map<String, Integer> numberOfEachItem) {
        ArrayList<String> rv;
        rv = new ArrayList<>();
        Arrays.stream(availableItems).forEach((itemId) -> {
            int numberOfItem = numberOfEachItem != null && numberOfEachItem.containsKey(itemId) ? numberOfEachItem.get(itemId) : 1;
            for (int i = 0; i < numberOfItem; i++) {
                rv.add(itemId);
            }
        });
        Collections.shuffle(rv);
        return rv.toArray(new String[0]);
    }

    static String[] createItemsSequence(String[] availableItems, int numberOfItems) {
        ArrayList<String> rv;
        rv = new ArrayList<>();
        Arrays.stream(availableItems).forEach((itemId) -> {
            for (int i = 0; i < numberOfItems; i++) {
                rv.add(itemId);
            }
        });
        Collections.shuffle(rv);
        return rv.toArray(new String[0]);
    }

    static String[][] createItemsSequences(int reelsNumber, String[] availableItems) {
        return createItemsSequences(reelsNumber, availableItems, 1);
    }

    static String[][] createItemsSequences(int reelsNumber, String[] availableItems, HashMap<Integer, HashMap<String, Integer>> itemsNumbersForReels) {
        String[][] rv;
        int i;
        int reelId;
        rv = new String[reelsNumber][];
        for (i = 0; i < reelsNumber; i++) {
            reelId = i;
            if (itemsNumbersForReels.containsKey(reelId)) {
                rv[reelId] = createItemsSequence(availableItems, itemsNumbersForReels.get(reelId));
            } else {
                rv[reelId] = createItemsSequence(availableItems, 1);
            }
        }
        return rv;
    }

    static String[][] createItemsSequences(int reelsNumber, String[] availableItems, int numberOfEachItemOnEachReel) {
        String[][] rv;
        int i;
        int reelId;
        rv = new String[reelsNumber][];
        for (i = 0; i < reelsNumber; i++) {
            reelId = i;
            rv[reelId] = createItemsSequence(availableItems, numberOfEachItemOnEachReel);
        }
        return rv;
    }

    String[][] getRandomItemsCombination();

    String[] getRandomReelItems(int reelId);

    String getRandomItem(int reelId);

}
