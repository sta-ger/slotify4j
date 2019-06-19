package slotify4j.session.videogames.reelgames.wincalculator;

import slotify4j.session.UnableToPlayException;
import slotify4j.session.videogames.reelgames.*;

import java.util.*;
import java.util.stream.IntStream;

public class ReelGameSessionWinCalculatorImpl implements ReelGameSessionWinCalculator {
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
    private long scattersWinning;
    private Map<String, ReelGameSessionWinningScatterModel> winningScatters;

    public ReelGameSessionWinCalculatorImpl(ReelGameSessionWinCalculatorConfig conf) {
        config = conf;
        wildItemId = conf.getWildItemId();
        wildsMultipliers = conf.getWildsMultipliers();
        paytable = conf.getPaytable();
        linesDirections = conf.getLinesDirections();
        linesPatterns = ReelGameSessionWinCalculator.createLinesPatterns(conf.getReelsNumber());
        scatters = conf.getScattersData();
    }

    @Override
    public void setGameState(long bet, String[][] items) throws UnableToPlayException {
        if (Arrays.stream(config.getAvailableBets()).anyMatch(availableBet -> availableBet == bet)) {
            this.items = items;
            this.calculateWinning(bet);
        } else {
            throw new UnableToPlayException("Bet " + bet + "is not specified at paytable");
        }
    }

    private void calculateWinning(long bet) {
        ReelGameSessionWinningLineModel line;
        winningLines = new HashMap<>();
        linesWinning = 0;
        int[] winningLinesIds = ReelGameSessionWinCalculator.getWinningLinesIds(items, linesDirections, linesPatterns, wildItemId);
        for (int lineId : winningLinesIds) {
            line = this.generateWinningLine(bet, lineId);
            if (line.getWinningAmount() > 0) {
                this.winningLines.put(line.getLineId(), line);
                this.linesWinning += line.getWinningAmount();
            }
        }
        this.scattersWinning = 0;
        this.winningScatters = this.generateWinningScatters(bet);
        winningScatters.forEach((scatterId, scatter) -> this.scattersWinning = this.scattersWinning + scatter.getWinningAmount());
    }

    private ReelGameSessionWinningLineModel generateWinningLine(long bet, int lineId) {
        int[] direction = linesDirections.getVerticalItemsPositionsForLineId(lineId);
        String[] itemsLine = ReelGameSessionWinCalculator.getItemsForDirection(items, direction);
        int[] pattern = ReelGameSessionWinCalculator.getMatchingPattern(itemsLine, linesPatterns, wildItemId);
        assert pattern != null;
        int[] itemsPositions = IntStream.range(0, pattern.length).filter(i -> pattern[i] == 1).toArray();
        String itemId = ReelGameSessionWinCalculator.getWinningItemId(itemsLine, pattern, wildItemId);
        int[] wildItemsPositions = ReelGameSessionWinCalculator.getWildItemsPositions(itemsLine, pattern, wildItemId);
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
        return ReelGameSessionWinCalculator.getScatterItemsPositions(this.items, itemId);
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
