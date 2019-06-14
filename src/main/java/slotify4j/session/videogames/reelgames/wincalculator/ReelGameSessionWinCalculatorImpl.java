package slotify4j.session.videogames.reelgames.wincalculator;

import slotify4j.session.videogames.reelgames.*;

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

    public static String[] getItemsForDirection(String[][] items, int[] direction) {
        return IntStream.range(0, direction.length)
                .mapToObj((col) -> items[col][direction[col]])
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
        return unique.stream().reduce((prev, cur) -> {
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
        int[] r = new int[0];
        for (int[] pattern : patterns) {
            if (isMatchPattern(items, pattern, wildItemId)) {
                r = pattern;
                break;
            }
        }
        return r.length > 0 ? r : null;
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

    public static int[] getWinningLinesIds(String[][] items, ReelGameSessionLinesDirectionData linesDirections, int[][] patterns) {
        return getWinningLinesIds(items, linesDirections, patterns, null);
    }

    public static int[] getWinningLinesIds(String[][] items, ReelGameSessionLinesDirectionData linesDirections, int[][] patterns, String wildItemId) {
        int[] lines = linesDirections.getLinesIds();
        return IntStream.of(lines).filter((lineId) -> {
            String[] itemsLine = getItemsForDirection(items, linesDirections.getVerticalItemsPositionsForLineId(lineId));
            return getMatchingPattern(itemsLine, patterns, wildItemId) != null;
        }).toArray();
    }

    private final ReelGameSessionWinCalculatorConfig config;
    private final ReelGameSessionPaytableData paytable;
    private final ReelGameSessionLinesDirectionData linesDirections;
    private final int[][] linesPatterns;
    private final String wildItemId;
    private final ReelGameSessionWildsMultipliersData wildsMultipliers;
    private final ReelGameSessionScatterData[] scatters;

    private String[][] items;
    private Map<Integer, ReelGameSessionWinningLineModel> winningLines;
    private long linesWinning;
    private int scattersWinning;
    private Map<String, ReelGameSessionWinningScatterModel> winningScatters;

    public ReelGameSessionWinCalculatorImpl(ReelGameSessionWinCalculatorConfig conf) {
        config = conf;
        wildItemId = conf.getWildItemId();
        wildsMultipliers = conf.getWildsMultipliers();
        paytable = conf.getPaytable();
        linesDirections = conf.getLinesDirections();
        linesPatterns = createLinesPatterns(conf.getReelsNumber());
        scatters = conf.getScattersData();
    }

    @Override
    public void setGameState(long bet, String[][] items) throws Exception {
        if (Arrays.stream(config.getAvailableBets()).anyMatch((availableBet) -> availableBet == bet)) {
            this.items = items;
            this.calculateWinning(bet);
        } else {
            throw new Exception("Bet " + bet + "is not specified at paytable");
        }
    }

    private void calculateWinning(long bet) {
        ReelGameSessionWinningLineModel line;
        winningLines = new HashMap<>();
        linesWinning = 0;
        int[] winningLinesIds = getWinningLinesIds(items, linesDirections, linesPatterns, wildItemId);
        for (int lineId : winningLinesIds) {
            line = this.generateWinningLine(bet, lineId);
            if (line.getWinningAmount() > 0) {
                this.winningLines.put(line.getLineId(), line);
                this.linesWinning += line.getWinningAmount();
            }
        }
        this.scattersWinning = 0;
        this.winningScatters = this.generateWinningScatters(bet);
        winningScatters.forEach((scatterId, scatter) -> this.scattersWinning += scatter.getWinningAmount());
    }

    private ReelGameSessionWinningLineModel generateWinningLine(long bet, int lineId) {
        int[] direction = linesDirections.getVerticalItemsPositionsForLineId(lineId);
        String[] itemsLine = getItemsForDirection(items, direction);
        int[] pattern = getMatchingPattern(itemsLine, linesPatterns, wildItemId);
        assert pattern != null;
        int[] itemsPositions = IntStream.range(0, pattern.length).filter(i -> pattern[i] == 1).toArray();
        String itemId = getWinningItemId(itemsLine, pattern, wildItemId);
        int[] wildItemsPositions = getWildItemsPositions(itemsLine, pattern, wildItemId);
        long winAmount = getLineWinningAmount(bet, itemId, itemsPositions.length, wildItemsPositions.length);
        return new ReelGameSessionWinningLineModelImpl(winAmount, direction, lineId, itemsPositions, wildItemsPositions, itemId);
    }

    private long getLineWinningAmount(long bet, String itemId, int numOfWinningItems, int numOfWilds) {
        long rv = 0;
        if (paytable.getWinningAmountForItem(itemId, numOfWinningItems, bet) != 0) {
            rv = paytable.getWinningAmountForItem(itemId, numOfWinningItems, bet) * wildsMultipliers.getMultiplierValueForWildsNum(numOfWilds);
        }
        return rv;
    }

    private Map<String, ReelGameSessionWinningScatterModel> generateWinningScatters(long bet) {
        Map<String, ReelGameSessionWinningScatterModel> rv = new HashMap<>();
        if (this.scatters != null) {
            for (ReelGameSessionScatterData scatter : scatters) {
                String curScatterItemId = scatter.getItemId();
                int curScatterMinItemsForWin = scatter.getMinimumItemsNumForScatterWin();
                int[][] curScatterItemsPositions = getScatterItemsPositions(curScatterItemId);
                long winningAmount = getLineWinningAmount(bet, curScatterItemId, curScatterItemsPositions.length, 0);
                if (curScatterItemsPositions.length >= curScatterMinItemsForWin) {
                    rv.put(curScatterItemId, new ReelGameSessionWinningScatterModelImpl(curScatterItemId, curScatterItemsPositions, winningAmount));
                }
            }
        }
        return rv;
    }

    private int[][] getScatterItemsPositions(String itemId) {
        return ReelGameSessionWinCalculatorImpl.getScatterItemsPositions(this.items, itemId);
    }

    @Override
    public Map<Integer, ReelGameSessionWinningLineModel> getWinningLines() {
        return winningLines;
    }

    @Override
    public Map<String, ReelGameSessionWinningScatterModel> getWinningScatters() {
        return winningScatters;
    }

    @Override
    public long getWinningAmount() {
        return getLinesWinning() + getScattersWinning();
    }

    @Override
    public long getLinesWinning() {
        return linesWinning;
    }

    @Override
    public long getScattersWinning() {
        return scattersWinning;
    }
}
