package slotify4j.session.videogames.reelgames.wincalculator;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.CollectionUtils;
import slotify4j.session.videogames.reelgames.*;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ReelGameSessionWinCalculatorImplTest {
    private ReelGameSessionConfig config = new DefaultReelGameSessionConfig();
    private ReelGameSessionWinCalculator winningCalculator = new ReelGameSessionWinCalculatorImpl(config);
    private Map<Integer, ReelGameSessionWinningLineModel> lines;
    private Map<String, ReelGameSessionWinningScatterModel> scatters;

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
    void createLinesPatternsTest() {
        int[][] patterns = ReelGameSessionWinCalculatorImpl.createLinesPatterns(5);
        assertArrayEquals(patterns, new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 0, 0},
                {1, 1, 0, 0, 0},
        });
    }

    @Test
    void getItemsMatchingPatternTest() {
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}), new String[]{"A", "A", "A"});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{0, 1, 1, 1, 0}), new String[]{"A", "A", "K"});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{0, 0, 1, 1, 1}), new String[]{"A", "K", "Q"});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, new int[]{0, 1, 0, 1, 0}), new String[]{"A", "K"});
    }

    @Test
    void isMatchPatternTest() {
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
    void getWinningItemIdTest() {
        assertEquals(ReelGameSessionWinCalculatorImpl.getWinningItemId(new String[]{"A", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}), "A");
        assertEquals(ReelGameSessionWinCalculatorImpl.getWinningItemId(new String[]{"A", "W", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
        assertEquals(ReelGameSessionWinCalculatorImpl.getWinningItemId(new String[]{"A", "W", "W", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
        assertEquals(ReelGameSessionWinCalculatorImpl.getWinningItemId(new String[]{"W", "W", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
        assertEquals(ReelGameSessionWinCalculatorImpl.getWinningItemId(new String[]{"W", "A", "A", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
        assertEquals(ReelGameSessionWinCalculatorImpl.getWinningItemId(new String[]{"W", "A", "W", "K", "Q",}, new int[]{1, 1, 1, 0, 0}, "W"), "A");
    }

    @Test
    void getMatchingPatternTest() {
        int[][] patterns = ReelGameSessionWinCalculatorImpl.createLinesPatterns(5);
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"A", "A", "K", "Q", "J",}, patterns), new int[]{1, 1, 0, 0, 0});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"A", "A", "A", "K", "Q",}, patterns), new int[]{1, 1, 1, 0, 0});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"A", "A", "A", "A", "Q",}, patterns), new int[]{1, 1, 1, 1, 0});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"A", "A", "A", "A", "A",}, patterns), new int[]{1, 1, 1, 1, 1});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"A", "W", "A", "W", "A",}, patterns, "W"), new int[]{1, 1, 1, 1, 1});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"W", "W", "A", "W", "K",}, patterns, "W"), new int[]{1, 1, 1, 1, 0});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getMatchingPattern(new String[]{"A", "W", "A", "W", "K",}, patterns, "W"), new int[]{1, 1, 1, 1, 0});
    }

    @Test
    void getWildItemsPositionsTest() {
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWildItemsPositions(new String[]{"A", "W", "K", "Q", "J",}, new int[]{1, 1, 0, 0, 0}, "W"), new int[]{1});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWildItemsPositions(new String[]{"W", "A", "K", "Q", "J",}, new int[]{1, 1, 0, 0, 0}, "W"), new int[]{0});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWildItemsPositions(new String[]{"A", "W", "W", "Q", "J",}, new int[]{1, 1, 1, 0, 0}, "W"), new int[]{1, 2});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWildItemsPositions(new String[]{"A", "W", "W", "W", "J",}, new int[]{1, 1, 1, 1, 0}, "W"), new int[]{1, 2, 3});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWildItemsPositions(new String[]{"A", "W", "W", "W", "W",}, new int[]{1, 1, 1, 1, 1}, "W"), new int[]{1, 2, 3, 4});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWildItemsPositions(new String[]{"W", "W", "K", "Q", "J",}, new int[]{1, 1, 1, 0, 0}, "W"), new int[]{0, 1});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWildItemsPositions(new String[]{"W", "W", "W", "K", "Q",}, new int[]{1, 1, 1, 1, 0}, "W"), new int[]{0, 1, 2});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWildItemsPositions(new String[]{"W", "W", "W", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"), new int[]{0, 1, 2, 3});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWildItemsPositions(new String[]{"W", "W", "K", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"), new int[]{0, 1, 3});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWildItemsPositions(new String[]{"K", "W", "K", "W", "K",}, new int[]{1, 1, 1, 1, 1}, "W"), new int[]{1, 3});
    }

    @Test
    void getScatterItemsPositionsTest() {
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getScatterItemsPositions(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
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
    void getItemsForDirectionTest() {
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsForDirection(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }), new int[]{0, 0, 0, 0, 0}), new String[]{"A", "A", "A", "K", "Q"});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsForDirection(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }), new int[]{1, 1, 1, 1, 1}), new String[]{"A", "K", "Q", "J", "10"});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsForDirection(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }), new int[]{2, 2, 2, 2, 2}), new String[]{"K", "Q", "J", "10", "9"});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsForDirection(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }), new int[]{0, 1, 2, 1, 0}), new String[]{"A", "K", "J", "J", "Q"});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getItemsForDirection(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }), new int[]{2, 0, 1, 2, 0}), new String[]{"K", "A", "Q", "10", "Q"});
    }

    @Test
    void getWinningLinesIdsTest() {
        ReelGameSessionLinesDirectionDataImpl directions = new ReelGameSessionLinesDirectionDataImpl(
                new HashMap<>() {{
                    put(0, new int[]{1, 1, 1});
                    put(1, new int[]{0, 0, 0});
                    put(2, new int[]{2, 2, 2});
                    put(3, new int[]{0, 1, 2});
                    put(4, new int[]{2, 1, 0});
                }}
        );

        int[][] patterns = ReelGameSessionWinCalculatorImpl.createLinesPatterns(3);

        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWinningLinesIds(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "A", "A"},
                {"K", "Q", "J"},
                {"K", "Q", "J"},
        }), directions, patterns), new int[]{1});

        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWinningLinesIds(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "A", "A"},
                {"K", "Q", "J"},
                {"A", "A", "A"},
        }), directions, patterns), new int[]{1, 2});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWinningLinesIds(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"K", "Q", "J"},
                {"A", "A", "A"},
                {"K", "Q", "J"},
        }), directions, patterns), new int[]{0});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWinningLinesIds(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "A", "A"},
                {"A", "A", "A"},
                {"K", "Q", "J"},
        }), directions, patterns), new int[]{0, 1, 3});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWinningLinesIds(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"K", "Q", "J"},
                {"A", "A", "A"},
                {"A", "A", "A"},
        }), directions, patterns), new int[]{0, 2, 4});
        assertArrayEquals(ReelGameSessionWinCalculatorImpl.getWinningLinesIds(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "A", "A"},
                {"A", "A", "A"},
                {"A", "A", "A"},
        }), directions, patterns), new int[]{0, 1, 2, 3, 4});
    }

    @Test
    void setGameStateTest() {
        assertDoesNotThrow(() -> winningCalculator.setGameState(1, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "A", "A"},
                {"A", "A", "A", "A", "A"},
                {"A", "A", "A", "A", "A"},
        })));
        assertThrows(Exception.class, () -> winningCalculator.setGameState(0, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "A", "A", "A", "A"},
                {"A", "A", "A", "A", "A"},
                {"A", "A", "A", "A", "A"},
        })));
    }

    @Test
    void calculateWinningLinesAfterUpdateStateTest() throws Exception {
        Arrays.stream(config.getAvailableBets()).forEach(bet -> {
            Arrays.stream(config.getAvailableItems()).forEach(item -> {
                if (!config.isItemWild(item) && !config.isItemScatter(item)) {
                    try {
                        winningCalculator.setGameState(bet, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
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
            });
        });

        winningCalculator.setGameState(1, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
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
    void calculateWinningLinesWithWildsAfterUpdateStateTest() throws Exception {
        winningCalculator.setGameState(1, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
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

        winningCalculator.setGameState(1, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
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

        winningCalculator.setGameState(1, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
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

        winningCalculator.setGameState(1, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
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

        winningCalculator.setGameState(1, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
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

        winningCalculator.setGameState(1, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
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

        winningCalculator.setGameState(1, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"W", "W", "W", "W", "W"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        lines = winningCalculator.getWinningLines();
        assertEquals(lines.keySet().size(), 0);
    }

    @Test
    void calculateWinningScattersAfterUpdateStateTest() throws Exception {
        winningCalculator.setGameState(1, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "S", "A", "K", "Q"},
                {"A", "K", "Q", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        scatters = winningCalculator.getWinningScatters();
        assertEquals(scatters.size(), 0);

        winningCalculator.setGameState(1, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "S", "A", "K", "Q"},
                {"A", "K", "S", "J", "10"},
                {"K", "Q", "J", "10", "9"},
        }));
        scatters = winningCalculator.getWinningScatters();
        assertEquals(scatters.size(), 0);

        winningCalculator.setGameState(1, ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"A", "S", "A", "K", "Q"},
                {"A", "K", "S", "J", "10"},
                {"K", "Q", "J", "10", "S"},
        }));
        scatters = winningCalculator.getWinningScatters();
        assertEquals(scatters.size(), 1);
        assertEquals(scatters.get("S").getWinningAmount(), config.getPaytable().getWinningAmountForItem(scatters.get("S").getItemId(), scatters.get("S").getItemsPositions().length, 1));
    }

}
