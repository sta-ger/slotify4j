package slotify4j.session.videogames.reelgames.wincalculator;

import slotify4j.session.videogames.reelgames.ReelGameSessionWinningLineModel;
import slotify4j.session.videogames.reelgames.ReelGameSessionWinningScatterModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        ArrayList<String> list = new ArrayList<>();
        IntStream.range(0, pattern.length).forEach((i) -> {
            if (pattern[i] == 1) {
                list.add(items[i]);
            }
        });
        return (String[]) list.toArray();
    }

    public ReelGameSessionWinCalculatorImpl(ReelGameSessionWinCalculatorConfig conf) {

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
