package slotify4j.session.videogames.reelgames.wincalculator;

import org.junit.Test;
import slotify4j.session.videogames.reelgames.DefaultReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSessionWinningLineModel;
import slotify4j.session.videogames.reelgames.ReelGameSessionWinningScatterModel;

import java.util.Map;

import static org.junit.Assert.*;

public class ReelGameSessionWinCalculatorImplTest {
    private ReelGameSessionConfig config = new DefaultReelGameSessionConfig();
    private ReelGameSessionWinCalculator winningCalculator = new ReelGameSessionWinCalculatorImpl(config);
    private Map<String, ReelGameSessionWinningLineModel> lines;
    private Map<String, ReelGameSessionWinningScatterModel> scatters;

    private void testWinning(long bet, Map<String, ReelGameSessionWinningLineModel> lines) {
        lines.forEach((lineId, line) -> {
            long lineWin = config.getPaytable().getWinningAmountForItem(line.getItemId(), line.getItemsPositions().length, bet);
            long wildMlt = config.getWildsMultipliers().getMultiplierValueForWildsNum(line.getWildItemsPositions().length);
            assertEquals(line.getWinningAmount(), lineWin * wildMlt);
        });
    }

    private void testItemsPositions(ReelGameSessionWinningLineModel line, int expectedItemsPositionsLength) {
        assertEquals(line.getItemsPositions().length, expectedItemsPositionsLength);
    }

    private void testWildItemsPositions(ReelGameSessionWinningLineModel line, int expectedItemsPositionsLength) {
        assertEquals(line.getWildItemsPositions().length, expectedItemsPositionsLength);
    }

    ;

    @Test
    public void testCreateLinesPatterns() {
        int[][] patterns = ReelGameSessionWinCalculatorImpl.createLinesPatterns(5);
        assertArrayEquals(patterns, new int[][] {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 0, 0},
                {1, 1, 0, 0, 0},
        });
    }

}
