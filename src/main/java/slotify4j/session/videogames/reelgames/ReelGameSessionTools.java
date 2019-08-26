package slotify4j.session.videogames.reelgames;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class ReelGameSessionTools {

    private ReelGameSessionTools() {

    }

    public static int[] findSectorsWithItemsOnSequence(String[] sequence, String[] items, int reelItemsNumber) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < sequence.length; i++) {
            String[] sector = new String[reelItemsNumber];
            sector[0] = sequence[i];
            for (int j = 1; j < sector.length; j++) {
                String nextItem;
                if (i + j < sequence.length) {
                    nextItem = sequence[i + j];
                } else {
                    nextItem = sequence[(i + j) - sequence.length];
                }
                sector[j] = nextItem;
            }
            AtomicBoolean flag = new AtomicBoolean(true);
            Arrays.stream(items).forEach((String item) -> {
                if (!flag.get() || !Arrays.asList(sector).contains((item))) {
                    flag.set(false);
                }
            });
            if (flag.get()) {
                list.add(i);
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    public static String[][] transposeItemsMatrix(String[][] source) {
        String[][] tmp = new String[source[0].length][source.length];
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                tmp[j][i] = source[i][j];
            }
        }
        return tmp;
    }

    public static String[][] createReelsItemsSequences(int reelsNumber, String[] availableItems) {
        String[][] r = new String[reelsNumber][availableItems.length * availableItems.length];
        for (int i = 0; i < reelsNumber; i++) {
            String[] seq = Stream.of(availableItems)
                    .reduce("", (a, b) -> a.concat(String.join(",", availableItems) + ","))
                    .split(",");
            Collections.shuffle(Arrays.asList(seq));
            r[i] = seq;
        }
        return r;
    }

    public static String[] createItemsSequence(String[] availableItems) {
        return createItemsSequence(availableItems, null);
    }

    public static String[] createItemsSequence(String[] availableItems, Map<String, Integer> numberOfEachItem) {
        ArrayList<String> rv;
        rv = new ArrayList<>();
        Arrays.stream(availableItems).forEach((String itemId) -> {
            int numberOfItem;
            if (numberOfEachItem != null && numberOfEachItem.containsKey(itemId)) {
                numberOfItem = numberOfEachItem.get(itemId);
            } else {
                numberOfItem = 1;
            }
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
        Arrays.stream(availableItems).forEach((String itemId) -> {
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

    public static String[][] createItemsSequences(
            int reelsNumber,
            String[] availableItems,
            Map<Integer, Map<String, Integer>> itemsNumbersForReels
    ) {
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

    public static String[][] createItemsSequences(
            int reelsNumber,
            String[] availableItems,
            int numberOfEachItemOnEachReel
    ) {
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

    public static Map<Integer, int[]> createDefaultLinesDirectionsMap(int reelsNumber, int reelsItemsNumber) {
        HashMap<Integer, int[]> r = new HashMap<>();
        for (int i = 0; i < reelsItemsNumber; i++) {
            r.put(i, new int[reelsNumber]);
            for (int j = 0; j < reelsNumber; j++) {
                r.get(i)[j] = i;
            }
        }
        return r;
    }

    public static int[][] createLinesPatterns(int reelsNumber) {
        int[][] r = new int[reelsNumber - 1][];
        for (int i = 0; i < reelsNumber - 1; i++) {
            int[] arr = IntStream.generate(() -> 0).limit(reelsNumber).toArray();
            for (int j = 0; j < reelsNumber - i; j++) {
                arr[j] = 1;
            }
            r[i] = arr;
        }
        return r;
    }

    public static String[] getItemsForDirection(String[][] items, int[] direction) {
        return IntStream.range(0, direction.length)
                .mapToObj(col -> items[col][direction[col]])
                .toArray(String[]::new);
    }

    public static String[] getItemsMatchingPattern(String[] items, int[] pattern) {
        return IntStream.range(0, items.length)
                .filter(i -> pattern[i] == 1)
                .mapToObj(i -> items[i])
                .toArray(String[]::new);
    }

    public static boolean isMatchPattern(String[] items, int[] pattern) {
        return isMatchPattern(items, pattern, null);
    }

    public static boolean isMatchPattern(String[] items, int[] pattern, String wildItemId) {
        List<String> itemsByPattern = Arrays.asList(getItemsMatchingPattern(items, pattern));
        HashSet<String> unique = new HashSet<>(itemsByPattern);
        return unique.size() == 1 || (unique.size() == 2 && unique.contains(wildItemId));
    }

    public static String getWinningItemId(String[] items, int[] pattern) {
        return getWinningItemId(items, pattern, null);
    }

    public static String getWinningItemId(String[] items, int[] pattern, String wildItemId) {
        List<String> itemsByPattern = Arrays.asList(getItemsMatchingPattern(items, pattern));
        HashSet<String> unique = new HashSet<>(itemsByPattern);
        return unique.stream().reduce((String prev, String cur) -> {
            if (!cur.equals(wildItemId)) {
                prev = cur;
            }
            return prev;
        }).orElse(null);
    }

    public static int[] getMatchingPattern(String[] items, int[][] patterns) {
        return getMatchingPattern(items, patterns, null);
    }

    public static int[] getMatchingPattern(String[] items, int[][] patterns, String wildItemId) {
        int[] r = null;
        for (int[] pattern : patterns) {
            if (isMatchPattern(items, pattern, wildItemId)) {
                r = pattern;
                break;
            }
        }
        return r;
    }

    public static int[] getWildItemsPositions(String[] items, int[] pattern, String wildItemId) {
        return IntStream.range(0, items.length)
                .filter(i -> items[i].equals(wildItemId) && pattern[i] == 1)
                .toArray();
    }

    public static int[][] getScatterItemsPositions(String[][] items, String scatterItemId) {
        ArrayList<int[]> r = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[i].length; j++) {
                if (items[i][j].equals(scatterItemId)) {
                    r.add(new int[]{i, j});
                }
            }
        }
        return r.toArray(new int[r.size()][]);
    }

    public static int[] getWinningLinesIds(
            String[][] items,
            ReelGameSessionLinesDirectionData linesDirections,
            int[][] patterns
    ) {
        return getWinningLinesIds(items, linesDirections, patterns, null);
    }

    public static int[] getWinningLinesIds(
            String[][] items,
            ReelGameSessionLinesDirectionData linesDirections,
            int[][] patterns, String wildItemId
    ) {
        int[] lines = linesDirections.getLinesIds();
        int[] ids = IntStream.of(lines).filter((int lineId) -> {
            String[] itemsLine = getItemsForDirection(
                    items,
                    linesDirections.getVerticalItemsPositionsForLineId(lineId)
            );
            return getMatchingPattern(itemsLine, patterns, wildItemId) != null;
        }).toArray();
        Arrays.sort(ids);
        return ids;
    }

    public static Map<Long, Map<String, Map<Integer, Long>>> createDefaultPaytableMap(
            long[] availableBets,
            String[] availableItems,
            int reelsNumber,
            String wildItemId
    ) {
        HashMap<Long, Map<String, Map<Integer, Long>>> r = new HashMap<>();
        for (long bet : availableBets) {
            r.put(bet, new HashMap<>());
            for (String itemId : availableItems) {
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

    public static Map<String, Map<Integer, Integer>> createDefaultFreeGamesForScattersMap() {
        Map<String, Map<Integer, Integer>> rv = new HashMap<>();
        Map<Integer, Integer> entry = new HashMap<>();
        entry.put(
                DefaultReelGameSessionConfig.DEFAULT_MINIMUM_ITEMS_NUM_FOR_SCATTER_WIN,
                DefaultReelGameWithFreeGamesSessionConfig.DEFAULT_FREE_GAMES_FOR_SCATTERS_NUM
        );
        rv.put(DefaultReelGameSessionConfig.DEFAULT_SCATTER_ITEM_ID, entry);
        return rv;
    }

}
