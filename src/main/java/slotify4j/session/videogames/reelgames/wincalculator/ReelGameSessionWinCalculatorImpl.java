package slotify4j.session.videogames.reelgames.wincalculator;

import slotify4j.session.videogames.reelgames.*;

import java.util.*;
import java.util.stream.IntStream;

public class ReelGameSessionWinCalculatorImpl implements ReelGameSessionWinCalculator {

    private final ReelGameSessionLinesDirectionData linesDirections;
    private final int reelsItemsNumber;
    private final int reelsNumber;
    private final int[][] linesPatterns;
    private final String wildItemId;
    private final ReelGameSessionWildsMultipliersData wildsMultipliers;

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
            String[] itemsLine = ReelGameSessionWinCalculatorImpl.getItemsForDirection(items, linesDirections.getVerticalItemsPositionsForLineId(lineId));
            return ReelGameSessionWinCalculatorImpl.getMatchingPattern(itemsLine, patterns, wildItemId) != null;
        }).toArray();
    }

    private final ReelGameSessionWinCalculatorConfig config;
    private final ReelGameSessionPaytableData paytable;
    private String[][] items;
    private ArrayList<ReelGameSessionWinningLineModel> winningLines;
    private long linesWinning;

    public ReelGameSessionWinCalculatorImpl(ReelGameSessionWinCalculatorConfig conf) {
        config = conf;
        reelsItemsNumber = conf.getReelsItemsNumber();
        reelsNumber = conf.getReelsNumber();
        wildItemId = conf.getWildItemId();
        wildsMultipliers = conf.getWildsMultipliers();
        paytable = conf.getPaytable();
        linesDirections = conf.getLinesDirections();
        linesPatterns = ReelGameSessionWinCalculatorImpl.createLinesPatterns(reelsNumber);
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
        int lineId;
        ReelGameSessionWinningLineModel line;
        winningLines = new ArrayList<ReelGameSessionWinningLineModel>();
        linesWinning = 0;
        int[] winningLinesIds = ReelGameSessionWinCalculatorImpl.getWinningLinesIds(items, linesDirections, linesPatterns, wildItemId);
        for (lineId : winningLinesIds) {
            line = this.generateWinningLine(bet, lineId);
            if (line.winningAmount > 0) {
                this._winningLines[lineId] = line;
                this._linesWinning += line.winningAmount;
            }
        }
        this._scattersWinning = 0;
        this._winningScatters = this.generateWinningScatters(bet);
        Object.keys(this._winningScatters).forEach((scatterId) => {
                this._scattersWinning += this._winningScatters[scatterId].winningAmount;
        });
    }

    private ReelGameSessionWinningLineModel generateWinningLine(long bet, int lineId) {
        ReelGameSessionWinningLineModel line;
        int[] direction;
        String[] itemsLine;
        int[] pattern;
        direction = linesDirections.getVerticalItemsPositionsForLineId(lineId);
        itemsLine = ReelGameSessionWinCalculatorImpl.getItemsForDirection(items, direction);
        pattern = ReelGameSessionWinCalculatorImpl.getMatchingPattern(itemsLine, linesPatterns, wildItemId);
        line = new ReelGameSessionWinningLineModelImpl(0, direction, lineId, pattern, ReelGameSessionWinCalculatorImpl.getWildItemsPositions(itemsLine, pattern, wildItemId), ReelGameSessionWinCalculatorImpl.getWinningItemId(itemsLine, pattern, wildItemId));
        return line;
    }

    private long getLineWinningAmount(long bet, ReelGameSessionWinningLineModel line) {
        long rv;
        if (paytable.getWinningAmountForItem(line.getItemId(), line.getItemsPositions().length, bet) != 0) {
            rv = paytable.getWinningAmountForItem(line.getItemId(), line.getItemsPositions().length, bet) * wildsMultipliers.getMultiplierValueForWildsNum(line.getWildItemsPositions().length);
        }
        return rv;
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
