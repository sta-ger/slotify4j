package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DefaultReelGameSessionConfigTest {

    private void testPaytableItem(ReelGameSessionPaytableData paytable, String itemId, int num, long bet, long expected) {
        assertEquals(paytable.getWinningAmountForItem(itemId, num, bet), expected);
    }

    @Test
    public void testCreateDefaultConfig() {
        ReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        assertEquals(conf.getWildItemId(), DefaultReelGameSessionConfig.DEFAULT_WILD_ITEM_ID);

        assertEquals(conf.getWildsMultipliers().getMultiplierValueForWildsNum(0), 1);
        assertEquals(conf.getWildsMultipliers().getMultiplierValueForWildsNum(1), 2);
        assertEquals(conf.getWildsMultipliers().getMultiplierValueForWildsNum(2), 4);
        assertEquals(conf.getWildsMultipliers().getMultiplierValueForWildsNum(3), 8);
        assertEquals(conf.getWildsMultipliers().getMultiplierValueForWildsNum(4), 16);
        assertEquals(conf.getWildsMultipliers().getMultiplierValueForWildsNum(5), 32);

        assertEquals(conf.getScattersData().length, 1);
        assertEquals(conf.getScattersData()[0].getItemId(), DefaultReelGameSessionConfig.DEFAULT_SCATTER_ITEM_ID);
        assertEquals(conf.getScattersData()[0].getMinimumItemsNumForScatterWin(), 3);

        assertEquals(conf.getReelsNumber(), 5);
        assertEquals(conf.getReelsItemsNumber(), 3);

        assertArrayEquals(conf.getLinesDirections().getVerticalItemsPositionsForLineId(0), new int[]{0, 0, 0, 0, 0});
        assertArrayEquals(conf.getLinesDirections().getVerticalItemsPositionsForLineId(1), new int[]{1, 1, 1, 1, 1});
        assertArrayEquals(conf.getLinesDirections().getVerticalItemsPositionsForLineId(2), new int[]{2, 2, 2, 2, 2});
        assertArrayEquals(conf.getLinesDirections().getLinesIds(), new int[]{0, 1, 2});

        assertEquals(conf.getReelsItemsSequences().length, conf.getReelsNumber());
        Stream.of(conf.getReelsItemsSequences()).forEach(seq -> Stream.of(conf.getAvailableItems()).forEach(item -> {
                //Check if every of available items exists on each sequence
                assertTrue(Arrays.asList(seq).contains(item));
        }));

        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("A"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("K"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("Q"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("J"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("10"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("9"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("W"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("S"));

        LongStream.of(conf.getAvailableBets()).forEach(bet -> Stream.of(conf.getAvailableItems()).forEach(item -> IntStream.rangeClosed(1, 3).forEach((num) -> {
            if (!item.equals(conf.getWildItemId())) {
                testPaytableItem(conf.getPaytable(), item, num + 2, bet, bet * num);
            }
        })));
    }

    @Test
    public void testIsItemWild() {
        ReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        assertTrue(conf.isItemWild(DefaultReelGameSessionConfig.DEFAULT_WILD_ITEM_ID));
        assertFalse(conf.isItemWild("A"));
    }

    @Test
    public void testIsItemScatter() {
        ReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        assertTrue(conf.isItemScatter(DefaultReelGameSessionConfig.DEFAULT_SCATTER_ITEM_ID));
        assertFalse(conf.isItemScatter("A"));
    }

}
