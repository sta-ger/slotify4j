package slotify4j.session.videogames.reelgames.wincalculator;

import org.junit.jupiter.api.Test;
import slotify4j.session.videogames.reelgames.*;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReelGameSessionWinCalculatorImplTest {
    private final ReelGameSessionConfig config = new DefaultReelGameSessionConfig();
    private final ReelGameSessionWinCalculator winningCalculator = new ReelGameSessionWinCalculatorImpl(config);
    private Map<Integer, ReelGameSessionWinningLineModel> lines;

    private void testWinning(long bet, Map<Integer, ReelGameSessionWinningLineModel> lines) {
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
    public void createLinesPatternsTest() {
        int[][] patterns = ReelGameSessionTools.createLinesPatterns(5);
        assertArrayEquals(patterns, new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 0, 0},
                {1, 1, 0, 0, 0},
        });
    }

    @Test
    public void getItemsMatchingPatternTest() {
        assertArrayEquals(ReelGameSessionTools.getItemsMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}), new String[]{"A", "A", "A"});
        assertArrayEquals(ReelGameSessionTools.getItemsMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{0, 1, 1, 1, 0}), new String[]{"A", "A", "K"});
        assertArrayEquals(ReelGameSessionTools.getItemsMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{0, 0, 1, 1, 1}), new String[]{"A", "K", "Q"});
        assertArrayEquals(ReelGameSessionTools.getItemsMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{0, 1, 0, 1, 0}), new String[]{"A", "K"});
    }

    @Test
    public void isMatchPatternTest() {
        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 0, 0, 0}));
        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}));
        assertFalse(ReelGameSessionTools.isMatchPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 1, 0}));
        assertFalse(ReelGameSessionTools.isMatchPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 1, 1}));
        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 0, 1, 0, 0}));
        assertFalse(ReelGameSessionTools.isMatchPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 0, 1, 0, 1}));

        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"A", "W", "K", "Q", "J",}, new int[]{1, 1, 0, 0, 0}, "W"));
        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"W", "A", "K", "Q", "J",}, new int[]{1, 1, 0, 0, 0}, "W"));
        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"A", "W", "W", "Q", "J",}, new int[]{1, 1, 1, 0, 0}, "W"));
        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"A", "W", "W", "W", "J",}, new int[]{1, 1, 1, 1, 0}, "W"));
        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"A", "W", "W", "W", "W",}, new int[]{1, 1, 1, 1, 1}, "W"));
        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"W", "W", "K", "Q", "J",}, new int[]{1, 1, 1, 0, 0}, "W"));
        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"W", "W", "W", "K", "Q",}, new int[]{1, 1, 1, 1, 0}, "W"));
        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"W", "W", "W", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"));
        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"W", "W", "K", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"));
        assertTrue(ReelGameSessionTools.isMatchPattern(new String[]{"K", "W", "K", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"));
        assertFalse(ReelGameSessionTools.isMatchPattern(new String[]{"K", "W", "A", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"));
    }

    @Test
    public void getWinningItemIdTest() {
        assertEquals(ReelGameSessionTools.getWinningItemId(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}), "A");
        assertEquals(ReelGameSessionTools.getWinningItemId(new String[]{"A", "W", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
        assertEquals(ReelGameSessionTools.getWinningItemId(new String[]{"A", "W", "W", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
        assertEquals(ReelGameSessionTools.getWinningItemId(new String[]{"W", "W", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
        assertEquals(ReelGameSessionTools.getWinningItemId(new String[]{"W", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
        assertEquals(ReelGameSessionTools.getWinningItemId(new String[]{"W", "A", "W", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
    }

    @Test
    public void getMatchingPatternTest() {
        int[][] patterns = ReelGameSessionTools.createLinesPatterns(5);
        assertArrayEquals(ReelGameSessionTools.getMatchingPattern(new String[]{"A", "A", "K", "Q", "J",}, patterns), new int[]{1, 1, 0, 0, 0});
        assertArrayEquals(ReelGameSessionTools.getMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, patterns), new int[]{1, 1, 1, 0, 0});
        assertArrayEquals(ReelGameSessionTools.getMatchingPattern(new String[]{"A", "A", "A", "A", "Q",}, patterns), new int[]{1, 1, 1, 1, 0});
        assertArrayEquals(ReelGameSessionTools.getMatchingPattern(new String[]{"A", "A", "A", "A", "A",}, patterns), new int[]{1, 1, 1, 1, 1});
        assertArrayEquals(ReelGameSessionTools.getMatchingPattern(new String[]{"A", "W", "A", "W", "A",}, patterns, "W"), new int[]{1, 1, 1, 1, 1});
        assertArrayEquals(ReelGameSessionTools.getMatchingPattern(new String[]{"W", "W", "A", "W", "K",}, patterns, "W"), new int[]{1, 1, 1, 1, 0});
        assertArrayEquals(ReelGameSessionTools.getMatchingPattern(new String[]{"A", "W", "A", "W", "K",}, patterns, "W"), new int[]{1, 1, 1, 1, 0});
    }

    @Test
    public void getWildItemsPositionsTest() {
        assertArrayEquals(ReelGameSessionTools.getWildItemsPositions(new String[]{"A", "W", "K", "Q", "J",}, new int[]{1, 1, 0, 0, 0}, "W"), new int[]{1});
        assertArrayEquals(ReelGameSessionTools.getWildItemsPositions(new String[]{"W", "A", "K", "Q", "J",}, new int[]{1, 1, 0, 0, 0}, "W"), new int[]{0});
        assertArrayEquals(ReelGameSessionTools.getWildItemsPositions(new String[]{"A", "W", "W", "Q", "J",}, new int[]{1, 1, 1, 0, 0}, "W"), new int[]{1, 2});
        assertArrayEquals(ReelGameSessionTools.getWildItemsPositions(new String[]{"A", "W", "W", "W", "J",}, new int[]{1, 1, 1, 1, 0}, "W"), new int[]{1, 2, 3});
        assertArrayEquals(ReelGameSessionTools.getWildItemsPositions(new String[]{"A", "W", "W", "W", "W",}, new int[]{1, 1, 1, 1, 1}, "W"), new int[]{1, 2, 3, 4});
        assertArrayEquals(ReelGameSessionTools.getWildItemsPositions(new String[]{"W", "W", "K", "Q", "J",}, new int[]{1, 1, 1, 0, 0}, "W"), new int[]{0, 1});
        assertArrayEquals(ReelGameSessionTools.getWildItemsPositions(new String[]{"W", "W", "W", "K", "Q",}, new int[]{1, 1, 1, 1, 0}, "W"), new int[]{0, 1, 2});
        assertArrayEquals(ReelGameSessionTools.getWildItemsPositions(new String[]{"W", "W", "W", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"), new int[]{0, 1, 2, 3});
        assertArrayEquals(ReelGameSessionTools.getWildItemsPositions(new String[]{"W", "W", "K", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"), new int[]{0, 1, 3});
        assertArrayEquals(ReelGameSessionTools.getWildItemsPositions(new String[]{"K", "W", "K", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"), new int[]{1, 3});
    }

    @Test
    public void getScatterItemsPositionsTest() {
        assertArrayEquals(ReelGameSessionTools.getScatterItemsPositions(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "K", "Q", "J", "10"},
                {"S", "S", "Q", "J", "S"},
                {"A", "K", "S", "J", "10"},
        }), "S"), new int[][]{
                {0, 1},
                {1, 1},
                {2, 2},
                {4, 1},
        });
    }

    @Test
    public void getItemsForDirectionTest() {
        assertArrayEquals(ReelGameSessionTools.getItemsForDirection(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }), new int[]{0, 0, 0, 0, 0}), new String[]{"A", "A", "A", "K", "Q"});
        assertArrayEquals(ReelGameSessionTools.getItemsForDirection(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }), new int[]{1, 1, 1, 1, 1}), new String[]{"A", "K", "Q", "J", "10"});
        assertArrayEquals(ReelGameSessionTools.getItemsForDirection(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }), new int[]{2, 2, 2, 2, 2}), new String[]{"K", "Q", "J", "10", "9"});
        assertArrayEquals(ReelGameSessionTools.getItemsForDirection(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }), new int[]{0, 1, 2, 1, 0}), new String[]{"A", "K", "J", "J", "Q"});
        assertArrayEquals(ReelGameSessionTools.getItemsForDirection(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }), new int[]{2, 0, 1, 2, 0}), new String[]{"K", "A", "Q", "10", "Q"});
    }

    @Test
    public void getWinningLinesIdsTest() {
        ReelGameSessionLinesDirectionDataImpl directions = new ReelGameSessionLinesDirectionDataImpl(
                Map.of(
                        0, new int[]{1, 1, 1},
                        1, new int[]{0, 0, 0},
                        2, new int[]{2, 2, 2},
                        3, new int[]{0, 1, 2},
                        4, new int[]{2, 1, 0}
                )
        );

        int[][] patterns = ReelGameSessionTools.createLinesPatterns(3);

        assertArrayEquals(ReelGameSessionTools.getWinningLinesIds(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A"},
                {"K", "Q", "J"},
                {"K", "Q", "J"},
        }), directions, patterns), new int[]{1});

        assertArrayEquals(ReelGameSessionTools.getWinningLinesIds(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A"},
                {"K", "Q", "J"},
                {"A", "A", "A"},
        }), directions, patterns), new int[]{1, 2});
        assertArrayEquals(ReelGameSessionTools.getWinningLinesIds(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"K", "Q", "J"},
                {"A", "A", "A"},
                {"K", "Q", "J"},
        }), directions, patterns), new int[]{0});
        assertArrayEquals(ReelGameSessionTools.getWinningLinesIds(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A"},
                {"A", "A", "A"},
                {"K", "Q", "J"},
        }), directions, patterns), new int[]{0, 1, 3});
        assertArrayEquals(ReelGameSessionTools.getWinningLinesIds(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"K", "Q", "J"},
                {"A", "A", "A"},
                {"A", "A", "A"},
        }), directions, patterns), new int[]{0, 2, 4});
        assertArrayEquals(ReelGameSessionTools.getWinningLinesIds(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A"},
                {"A", "A", "A"},
                {"A", "A", "A"},
        }), directions, patterns), new int[]{0, 1, 2, 3, 4});
    }

    @Test
    public void setGameStateTest() {
        assertDoesNotThrow(() -> winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "A", "A"},
                {"A", "A", "A", "A", "A"},
                {"A", "A", "A", "A", "A"},
        })));
        assertThrows(Exception.class, () -> winningCalculator.setGameState(0, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "A", "A"},
                {"A", "A", "A", "A", "A"},
                {"A", "A", "A", "A", "A"},
        })));
    }

    @Test
    public void calculateWinningLinesAfterUpdateStateTest() throws Exception {
        Arrays.stream(config.getAvailableBets()).forEach(bet -> Arrays.stream(config.getAvailableItems()).forEach(item -> {
            if (!config.isItemWild(item) && !config.isItemScatter(item)) {
                try {
                    winningCalculator.setGameState(bet, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                            {item, item, item, item, item},
                            {item, item, item, item, item},
                            {item, item, item, item, item},
                    }));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lines = winningCalculator.getWinningLines();
                assertEquals(lines.keySet().size(), 3);
                assertTrue(lines.keySet().contains(0));
                assertTrue(lines.keySet().contains(1));
                assertTrue(lines.keySet().contains(2));
                testWinning(bet, lines);
                testItemsPositions(lines.get(0), 5);
                testItemsPositions(lines.get(1), 5);
                testItemsPositions(lines.get(2), 5);
            }
        }));

        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        lines = winningCalculator.getWinningLines();
        assertEquals(lines.keySet().size(), 1);
        assertTrue(lines.keySet().contains(0));
        assertFalse(lines.keySet().contains(1));
        assertFalse(lines.keySet().contains(2));
        testWinning(1, lines);
        testItemsPositions(lines.get(0), 3);
    }

    @Test
    public void calculateWinningLinesWithWildsAfterUpdateStateTest() throws Exception {
        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "W", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        lines = winningCalculator.getWinningLines();
        assertEquals(lines.keySet().size(), 1);
        assertTrue(lines.keySet().contains(0));
        assertFalse(lines.keySet().contains(1));
        assertFalse(lines.keySet().contains(2));
        testWinning(1, lines);
        testWildItemsPositions(lines.get(0), 1);
        testItemsPositions(lines.get(0), 3);

        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "W", "W", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        lines = winningCalculator.getWinningLines();
        assertEquals(lines.keySet().size(), 1);
        assertTrue(lines.keySet().contains(0));
        assertFalse(lines.keySet().contains(1));
        assertFalse(lines.keySet().contains(2));
        testWinning(1, lines);
        testWildItemsPositions(lines.get(0), 2);
        testItemsPositions(lines.get(0), 3);

        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "W", "W", "W", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        lines = winningCalculator.getWinningLines();
        assertEquals(lines.keySet().size(), 1);
        assertTrue(lines.keySet().contains(0));
        assertFalse(lines.keySet().contains(1));
        assertFalse(lines.keySet().contains(2));
        testWinning(1, lines);
        testWildItemsPositions(lines.get(0), 3);
        testItemsPositions(lines.get(0), 4);

        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "W", "W", "W", "W"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        lines = winningCalculator.getWinningLines();
        assertEquals(lines.keySet().size(), 1);
        assertTrue(lines.keySet().contains(0));
        assertFalse(lines.keySet().contains(1));
        assertFalse(lines.keySet().contains(2));
        testWinning(1, lines);
        testWildItemsPositions(lines.get(0), 4);
        testItemsPositions(lines.get(0), 5);

        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"W", "W", "W", "W", "A"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        lines = winningCalculator.getWinningLines();
        assertEquals(lines.keySet().size(), 1);
        assertTrue(lines.keySet().contains(0));
        assertFalse(lines.keySet().contains(1));
        assertFalse(lines.keySet().contains(2));
        testWinning(1, lines);
        testWildItemsPositions(lines.get(0), 4);
        testItemsPositions(lines.get(0), 5);

        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"W", "W", "A", "W", "W"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        lines = winningCalculator.getWinningLines();
        assertEquals(lines.keySet().size(), 1);
        assertTrue(lines.keySet().contains(0));
        assertFalse(lines.keySet().contains(1));
        assertFalse(lines.keySet().contains(2));
        testWinning(1, lines);
        testWildItemsPositions(lines.get(0), 4);
        testItemsPositions(lines.get(0), 5);

        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"W", "W", "W", "W", "W"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        lines = winningCalculator.getWinningLines();
        assertEquals(lines.keySet().size(), 0);
    }

    @Test
    public void calculateWinningScattersAfterUpdateStateTest() throws Exception {
        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "S", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        Map<String, ReelGameSessionWinningScatterModel> scatters = winningCalculator.getWinningScatters();
        assertEquals(scatters.size(), 0);

        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "S", "A", "K", "Q"},
                {"A", "K", "S", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        scatters = winningCalculator.getWinningScatters();
        assertEquals(scatters.size(), 0);

        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "S", "A", "K", "Q"},
                {"A", "K", "S", "J", "10"},
                {"K", "Q", "J", "10", "S"},
        }));
        scatters = winningCalculator.getWinningScatters();
        assertEquals(scatters.size(), 1);
        assertEquals(scatters.get("S").getWinningAmount(), config.getPaytable().getWinningAmountForItem(scatters.get("S").getItemId(), scatters.get("S").getItemsPositions().length, 1));
    }

    @Test
    public void calculateAllLinesWinAmountAfterUpdateStateTest() throws Exception {
        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "A", "A"},
                {"A", "A", "A", "K", "Q"},
                {"A", "A", "K", "Q", "J"},
        }));
        assertTrue(winningCalculator.getLinesWinning() > 0);
    }

    @Test
    public void calculateAllScattersWinAmountAfterUpdateStateTest() throws Exception {
        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "A", "A"},
                {"A", "A", "A", "S", "S"},
                {"A", "A", "K", "Q", "S"},
        }));
        assertTrue(winningCalculator.getScattersWinning() > 0);
    }

    @Test
    public void calculateTotalWinAmountAfterUpdateStateTest() throws Exception {
        winningCalculator.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "A", "A"},
                {"A", "A", "A", "S", "S"},
                {"A", "A", "K", "Q", "S"},
        }));
        assertTrue(winningCalculator.getWinningAmount() > 0);
    }

}
