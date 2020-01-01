package slotify4j.session.videogames.reelgames.wincalculator;

import org.junit.jupiter.api.Test;
import slotify4j.session.UnableToPlayException;
import slotify4j.session.videogames.reelgames.DefaultReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSessionTools;
import slotify4j.session.videogames.reelgames.ReelGameSessionWinningLineModel;
import slotify4j.session.videogames.reelgames.ReelGameSessionWinningLineModelImpl;

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
        String[][] items = new String[][]{
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
    public void getLinesWithWinningItem() {
    }

    @Test
    public void getLinesWithDifferentItems() {
    }
}