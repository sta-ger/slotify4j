package slotify4j.session.videogames.reelgames.wincalculator;

import org.junit.jupiter.api.Test;
import slotify4j.session.UnableToPlayException;
import slotify4j.session.videogames.reelgames.*;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReelGameWinCalculatorToolsTest {

    @Test
    public void isAllLinesHasSameItemId() {
        assertTrue(ReelGameWinCalculatorTools.isAllLinesHasSameItemId(
                new ReelGameSessionWinningLineModel[]{
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 0, new int[0], new int[0], "A")
                })
        );
        assertTrue(ReelGameWinCalculatorTools.isAllLinesHasSameItemId(
                new ReelGameSessionWinningLineModel[]{
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 0, new int[0], new int[0], "A"),
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 1, new int[0], new int[0], "A")
                })
        );
        assertTrue(ReelGameWinCalculatorTools.isAllLinesHasSameItemId(
                new ReelGameSessionWinningLineModel[]{
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 0, new int[0], new int[0], "A"),
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 1, new int[0], new int[0], "A"),
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 2, new int[0], new int[0], "A")
                })
        );
        assertFalse(ReelGameWinCalculatorTools.isAllLinesHasSameItemId(
                new ReelGameSessionWinningLineModel[]{
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 0, new int[0], new int[0], "A"),
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 1, new int[0], new int[0], "K"),
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 2, new int[0], new int[0], "A")
                })
        );
        assertFalse(ReelGameWinCalculatorTools.isAllLinesHasSameItemId(
                new ReelGameSessionWinningLineModel[]{
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 0, new int[0], new int[0], "K"),
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 1, new int[0], new int[0], "A"),
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 2, new int[0], new int[0], "A")
                })
        );
        assertFalse(ReelGameWinCalculatorTools.isAllLinesHasSameItemId(
                new ReelGameSessionWinningLineModel[]{
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 0, new int[0], new int[0], "K"),
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 1, new int[0], new int[0], "K"),
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 2, new int[0], new int[0], "A")
                })
        );
        assertFalse(ReelGameWinCalculatorTools.isAllLinesHasSameItemId(
                new ReelGameSessionWinningLineModel[]{
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 0, new int[0], new int[0], "A"),
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 1, new int[0], new int[0], "K"),
                        new ReelGameSessionWinningLineModelImpl(1, new int[0], 2, new int[0], new int[0], "K")
                })
        );

    }

    @Test
    public void getLinesWithItem() throws UnableToPlayException {
        ReelGameSessionWinCalculatorImpl calc = new ReelGameSessionWinCalculatorImpl(
                new DefaultReelGameSessionConfig()
        );
        String[][] items = {
                {"A", "A", "A", "K", "Q"},
                {"K", "K", "K", "Q", "J"},
                {"Q", "Q", "Q", "J", "10"},
        };
        calc.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(items));
        Map<Integer, ReelGameSessionWinningLineModel> lines = calc.getWinningLines();
        ReelGameSessionWinningLineModel[] linesWithTen = ReelGameWinCalculatorTools.getLinesWithItem(
                lines.values().toArray(new ReelGameSessionWinningLineModel[0]),
                ReelGameSessionTools.transposeItemsMatrix(items),
                "10"
        );
        assertEquals(linesWithTen.length, 1);
        assertTrue(Arrays.stream(linesWithTen).anyMatch(line -> line.getLineId() == 2));

        ReelGameSessionWinningLineModel[] linesWithQ = ReelGameWinCalculatorTools.getLinesWithItem(
                lines.values().toArray(new ReelGameSessionWinningLineModel[0]),
                ReelGameSessionTools.transposeItemsMatrix(items),
                "Q"
        );
        assertEquals(linesWithQ.length, 3);
        assertTrue(Arrays.stream(linesWithQ).anyMatch(line -> line.getLineId() == 0));
        assertTrue(Arrays.stream(linesWithQ).anyMatch(line -> line.getLineId() == 1));
        assertTrue(Arrays.stream(linesWithQ).anyMatch(line -> line.getLineId() == 2));

        ReelGameSessionWinningLineModel[] linesWithK = ReelGameWinCalculatorTools.getLinesWithItem(
                lines.values().toArray(new ReelGameSessionWinningLineModel[0]),
                ReelGameSessionTools.transposeItemsMatrix(items),
                "K"
        );
        assertEquals(linesWithK.length, 2);
        assertTrue(Arrays.stream(linesWithK).anyMatch(line -> line.getLineId() == 0));
        assertTrue(Arrays.stream(linesWithK).anyMatch(line -> line.getLineId() == 1));

        ReelGameSessionWinningLineModel[] linesWithA = ReelGameWinCalculatorTools.getLinesWithItem(
                lines.values().toArray(new ReelGameSessionWinningLineModel[0]),
                ReelGameSessionTools.transposeItemsMatrix(items),
                "A"
        );
        assertEquals(linesWithA.length, 1);
        assertTrue(Arrays.stream(linesWithA).anyMatch(line -> line.getLineId() == 0));
    }

    @Test
    public void getLinesWithWinningItem() throws UnableToPlayException {
        String[][] items = {
                {"A", "A", "A", "K", "Q"},
                {"K", "K", "K", "Q", "J"},
                {"Q", "Q", "Q", "J", "10"},
        };
        Map<Integer, int[]> linesDirections = ReelGameSessionTools.createDefaultLinesDirectionsMap(
                DefaultReelGameSessionConfig.DEFAULT_REELS_NUMBER,
                DefaultReelGameSessionConfig.DEFAULT_REELS_ITEMS_NUMBER
        );
        linesDirections.put(3, new int[]{1, 1, 1, 0, 0}); //Line of items K
        DefaultReelGameSessionConfig config = new DefaultReelGameSessionConfig();
        config.setLinesDirections(new ReelGameSessionLinesDirectionDataImpl(linesDirections));
        ReelGameSessionWinCalculatorImpl calc = new ReelGameSessionWinCalculatorImpl(config);
        calc.setGameState(1, ReelGameSessionTools.transposeItemsMatrix(items));
        Map<Integer, ReelGameSessionWinningLineModel> lines = calc.getWinningLines();
        ReelGameSessionWinningLineModel[] linesWithTen = ReelGameWinCalculatorTools.getLinesWithWinningItem(
                lines.values().toArray(new ReelGameSessionWinningLineModel[0]),
                "10"
        );
        assertEquals(linesWithTen.length, 0);

        ReelGameSessionWinningLineModel[] linesWithQ = ReelGameWinCalculatorTools.getLinesWithWinningItem(
                lines.values().toArray(new ReelGameSessionWinningLineModel[0]),
                "Q"
        );
        assertEquals(linesWithQ.length, 1);
        assertTrue(Arrays.stream(linesWithQ).anyMatch(line -> line.getLineId() == 2));

        ReelGameSessionWinningLineModel[] linesWithK = ReelGameWinCalculatorTools.getLinesWithWinningItem(
                lines.values().toArray(new ReelGameSessionWinningLineModel[0]),
                "K"
        );
        assertEquals(linesWithK.length, 2);
        assertTrue(Arrays.stream(linesWithK).anyMatch(line -> line.getLineId() == 1));
        assertTrue(Arrays.stream(linesWithK).anyMatch(line -> line.getLineId() == 3));

        ReelGameSessionWinningLineModel[] linesWithA = ReelGameWinCalculatorTools.getLinesWithWinningItem(
                lines.values().toArray(new ReelGameSessionWinningLineModel[0]),
                "A"
        );
        assertEquals(linesWithA.length, 1);
        assertTrue(Arrays.stream(linesWithA).anyMatch(line -> line.getLineId() == 0));
    }

    @Test
    public void getLinesWithDifferentItems() {
    }
}