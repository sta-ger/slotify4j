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

    @Test
    public void testCreateLinesPatterns() {
        int[][] patterns = ReelGameSessionWinCalculatorImpl.createLinesPatterns(5);
        assertArrayEquals(patterns, new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 0, 0},
                {1, 1, 0, 0, 0},
        });
    }

    @Test
    public void testGetItemsMatchingPattern() {
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}), new String[]{"A", "A", "A"});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{0, 1, 1, 1, 0}), new String[]{"A", "A", "K"});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{0, 0, 1, 1, 1}), new String[]{"A", "K", "Q"});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{0, 1, 0, 1, 0}), new String[]{"A", "K"});
    }

    @Test
    public void testIsItemsArrayMatchPattern() {
        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 0, 0, 0}));
        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}));
        assertFalse(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 1, 0}));
        assertFalse(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 1, 1}));
        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 0, 1, 0, 0}));
        assertFalse(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 0, 1, 0, 1}));

        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"A", "W", "K", "Q", "J",}, new int[]{1, 1, 0, 0, 0}, "W"));
        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"W", "A", "K", "Q", "J",}, new int[]{1, 1, 0, 0, 0}, "W"));
        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"A", "W", "W", "Q", "J",}, new int[]{1, 1, 1, 0, 0}, "W"));
        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"A", "W", "W", "W", "J",}, new int[]{1, 1, 1, 1, 0}, "W"));
        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"A", "W", "W", "W", "W",}, new int[]{1, 1, 1, 1, 1}, "W"));
        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"W", "W", "K", "Q", "J",}, new int[]{1, 1, 1, 0, 0}, "W"));
        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"W", "W", "W", "K", "Q",}, new int[]{1, 1, 1, 1, 0}, "W"));
        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"W", "W", "W", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"));
        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"W", "W", "K", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"));
        assertTrue(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"K", "W", "K", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"));
        assertFalse(ReelGameSessionWinCalculatorImpl.isMatchPattern(new String[]{"K", "W", "A", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"));
    }

    @Test
    public void testGetWinningItemId() {
        assertEquals(ReelGameSessionWinCalculatorImpl.getWinningItemId(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}), "A");
        assertEquals(ReelGameSessionWinCalculatorImpl.getWinningItemId(new String[]{"A", "W", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
        assertEquals(ReelGameSessionWinCalculatorImpl.getWinningItemId(new String[]{"A", "W", "W", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
        assertEquals(ReelGameSessionWinCalculatorImpl.getWinningItemId(new String[]{"W", "W", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
        assertEquals(ReelGameSessionWinCalculatorImpl.getWinningItemId(new String[]{"W", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
        assertEquals(ReelGameSessionWinCalculatorImpl.getWinningItemId(new String[]{"W", "A", "W", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
    }

    @Test
    public void testGetMatchingPattern() {
        int[][] patterns = ReelGameSessionWinCalculatorImpl.createLinesPatterns(5);
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"A", "A", "K", "Q", "J",}, patterns), new int[]{1, 1, 0, 0, 0});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, patterns), new int[]{1, 1, 1, 0, 0});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"A", "A", "A", "A", "Q",}, patterns), new int[]{1, 1, 1, 1, 0});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"A", "A", "A", "A", "A",}, patterns), new int[]{1, 1, 1, 1, 1});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"A", "W", "A", "W", "A",}, patterns, "W"), new int[]{1, 1, 1, 1, 1});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"W", "W", "A", "W", "K",}, patterns, "W"), new int[]{1, 1, 1, 1, 0});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"A", "W", "A", "W", "K",}, patterns, "W"), new int[]{1, 1, 1, 1, 0});
    }

}
