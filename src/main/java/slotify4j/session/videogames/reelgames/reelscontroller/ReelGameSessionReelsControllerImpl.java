package slotify4j.session.videogames.reelgames.reelscontroller;

import java.util.*;

public class ReelGameSessionReelsControllerImpl implements ReelGameSessionReelsController {

    public static String[][] transposeItemsMatrix(String[][] source) {
        String[][] tmp = new String[source[0].length][source.length];
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                tmp[j][i] = source[i][j];
            }
        }
        return tmp;
    }

    public static String[] createItemsSequence(String[] availableItems) {
        return createItemsSequence(availableItems, null);
    }

    public static String[] createItemsSequence(String[] availableItems, Map<String, Integer> numberOfEachItem) {
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

    public static String[] createItemsSequence(String[] availableItems, int numberOfItems) {
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

    public static String[][] createItemsSequences(int reelsNumber, String[] availableItems) {
        return createItemsSequences(reelsNumber, availableItems, 1);
    }

    public static String[][] createItemsSequences(int reelsNumber, String[] availableItems, HashMap<Integer, HashMap<String, Integer>> itemsNumbersForReels) {
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

    public static String[][] createItemsSequences(int reelsNumber, String[] availableItems, int numberOfEachItemOnEachReel) {
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

    private final int reelsNumber;
    private final int reelsItemsNumber;
    private final String[][] reelsSequences;

    public ReelGameSessionReelsControllerImpl(ReelGameSessionReelsControllerConfig config) {
        reelsNumber = config.getReelsNumber();
        reelsItemsNumber = config.getReelsItemsNumber();
        reelsSequences = config.getReelsItemsSequences();
    }

    @Override
    public String[][] getRandomItemsCombination() {
        String[][] rv = new String[reelsNumber][];
        for (int i = 0; i < reelsNumber; i++) {
            rv[i] = getRandomReelItems(i);
        }
        return rv;
    }

    @Override
    public String[] getRandomReelItems(int reelId) {
        String[] rv = new String[reelsItemsNumber];
        String[] sequence = reelsSequences[reelId];
        int placeOnSequence = (int) Math.floor(Math.random() * sequence.length);
        int j = 0;
        for (int i = placeOnSequence; i < placeOnSequence + reelsItemsNumber; i++) {
            String item;
            if (i > sequence.length - 1) {
                item = sequence[i - sequence.length];
            } else {
                item = sequence[i];
            }
            rv[j] = item;
            j++;
        }
        return rv;
    }

    @Override
    public String getRandomItem(int reelId) {
        String[] sequence = reelsSequences[reelId];
        return sequence[(int) Math.floor(Math.random() * sequence.length)];
    }

}
