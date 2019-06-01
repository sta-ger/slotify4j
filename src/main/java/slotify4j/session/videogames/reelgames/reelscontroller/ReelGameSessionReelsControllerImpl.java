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

    public static String[] createItemsSequence(String[] availableItems, Map<String, Integer> countsOfItems) {
        ArrayList<String> rv;
        rv = new ArrayList<>();
        Arrays.stream(availableItems).forEach((itemId) -> {
            int countIoItems = countsOfItems != null && countsOfItems.containsKey(itemId) ? countsOfItems.get(itemId) : 1;
            for (int i = 0; i < countIoItems; i++) {
                rv.add(itemId);
            }
        });
        Collections.shuffle(rv);
        return rv.toArray(new String[0]);
    }

    public static String[] createItemsSequence(String[] availableItems, int countOfItems) {
        ArrayList<String> rv;
        rv = new ArrayList<>();
        Arrays.stream(availableItems).forEach((itemId) -> {
            for (int i = 0; i < countOfItems; i++) {
                rv.add(itemId);
            }
        });
        Collections.shuffle(rv);
        return rv.toArray(new String[0]);
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
