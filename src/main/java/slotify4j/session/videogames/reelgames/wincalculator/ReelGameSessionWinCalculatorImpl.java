package slotify4j.session.videogames.reelgames.wincalculator;

import slotify4j.session.videogames.reelgames.ReelGameSessionWinningLineModel;
import slotify4j.session.videogames.reelgames.ReelGameSessionWinningScatterModel;

import java.util.*;
import java.util.stream.IntStream;

public class ReelGameSessionWinCalculatorImpl implements ReelGameSessionWinCalculator {

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
        return unique.stream().reduce((prev, cur) -> {
            if (!cur.equals(wildItemId)) {
                prev = cur;
            }
            return prev;
        }).orElse(null);
    }

    public ReelGameSessionWinCalculatorImpl(ReelGameSessionWinCalculatorConfig conf) {

    }

    public static int[] getMatchingPattern(String[] items, int[][] patterns) {
        return getMatchingPattern(items, patterns, null);
    }

    public static int[] getMatchingPattern(String[] items, int[][] patterns, String wildItemId) {
        int[] r = new int[0];
        for (int i = 0; i < patterns.length; i++) {
            if (isMatchPattern(items, patterns[i], wildItemId)) {
                r = patterns[i];
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


    @Override
    public void setGameState(long bet, String[][] items) {

    }

    @Override
    public Map<String, ReelGameSessionWinningLineModel> getWinningLines() {
        return null;
    }

    @Override
    public Map<String, ReelGameSessionWinningScatterModel> getWinningScatters() {
        return null;
    }

    @Override
    public long getWinningAmount() {
        return 0;
    }

    @Override
    public long getLinesWinning() {
        return 0;
    }

    @Override
    public long getScattersWinning() {
        return 0;
    }
}
