package slotify4j.session.videogames.reelgames;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class DefaultReelGameSessionConfigTest {

    @Test
    public void testCreateDefaultConfig() {
        ReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        assertEquals(conf.getWildItemId(), "W");

        assertEquals(conf.getWildsMultipliers().getMultiplierValueForWildsNum(0), 1);
        assertEquals(conf.getWildsMultipliers().getMultiplierValueForWildsNum(1), 2);
        assertEquals(conf.getWildsMultipliers().getMultiplierValueForWildsNum(2), 4);
        assertEquals(conf.getWildsMultipliers().getMultiplierValueForWildsNum(3), 8);
        assertEquals(conf.getWildsMultipliers().getMultiplierValueForWildsNum(4), 16);
        assertEquals(conf.getWildsMultipliers().getMultiplierValueForWildsNum(5), 32);

        assertEquals(conf.getScattersData().length, 1);
        assertEquals(conf.getScattersData()[0].getItemId(), "S");
        assertEquals(conf.getScattersData()[0].getMinimumItemsNumForScatterWin(), 3);

        assertEquals(conf.getReelsNumber(), 5);
        assertEquals(conf.getReelsItemsNumber(), 3);

        assertArrayEquals(conf.getLinesDirections().getVerticalItemsPositionsForLineId(0), new int[]{0, 0, 0, 0, 0});
        assertArrayEquals(conf.getLinesDirections().getVerticalItemsPositionsForLineId(1), new int[]{1, 1, 1, 1, 1});
        assertArrayEquals(conf.getLinesDirections().getVerticalItemsPositionsForLineId(2), new int[]{2, 2, 2, 2, 2});

        assertEquals(conf.getReelsItemsSequences().length, conf.getReelsNumber());
        Stream.of(conf.getReelsItemsSequences()).forEach(seq -> {
            Stream.of(conf.getAvailableItems()).forEach(item -> {
                    //Check if every of available items exists on each sequence
                    assertTrue(Arrays.asList(seq).contains(item));
            });
        });

        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("A"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("K"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("Q"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("J"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("10"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("9"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("W"));
        assertTrue(Arrays.asList(conf.getAvailableItems()).contains("S"));

        /*expect(conf.paytable).toEqual({
                "1":
        {
            "9": {"3": 1, "4": 2, "5": 3},
            "10": {"3": 1, "4": 2, "5": 3},
            A: {"3": 1, "4": 2, "5": 3},
            K: {"3": 1, "4": 2, "5": 3},
            Q: {"3": 1, "4": 2, "5": 3},
            J: {"3": 1, "4": 2, "5": 3},
            S: {"3": 1, "4": 2, "5": 3}
        },
        "2":
        {
            "9": {"3": 2, "4": 4, "5": 6},
            "10": {"3": 2, "4": 4, "5": 6},
            A: {"3": 2, "4": 4, "5": 6},
            K: {"3": 2, "4": 4, "5": 6},
            Q: {"3": 2, "4": 4, "5": 6},
            J: {"3": 2, "4": 4, "5": 6},
            S: {"3": 2, "4": 4, "5": 6}
        },
        "3":
        {
            "9": {"3": 3, "4": 6, "5": 9},
            "10": {"3": 3, "4": 6, "5": 9},
            A: {"3": 3, "4": 6, "5": 9},
            K: {"3": 3, "4": 6, "5": 9},
            Q: {"3": 3, "4": 6, "5": 9},
            J: {"3": 3, "4": 6, "5": 9},
            S: {"3": 3, "4": 6, "5": 9}
        },
        "4":
        {
            "9": {"3": 4, "4": 8, "5": 12},
            "10": {"3": 4, "4": 8, "5": 12},
            A: {"3": 4, "4": 8, "5": 12},
            K: {"3": 4, "4": 8, "5": 12},
            Q: {"3": 4, "4": 8, "5": 12},
            J: {"3": 4, "4": 8, "5": 12},
            S: {"3": 4, "4": 8, "5": 12}
        },
        "5":
        {
            "9": {"3": 5, "4": 10, "5": 15},
            "10": {"3": 5, "4": 10, "5": 15},
            A: {"3": 5, "4": 10, "5": 15},
            K: {"3": 5, "4": 10, "5": 15},
            Q: {"3": 5, "4": 10, "5": 15},
            J: {"3": 5, "4": 10, "5": 15},
            S: {"3": 5, "4": 10, "5": 15}
        },
        "10":
        {
            "9": {"3": 10, "4": 20, "5": 30},
            "10": {"3": 10, "4": 20, "5": 30},
            A: {"3": 10, "4": 20, "5": 30},
            K: {"3": 10, "4": 20, "5": 30},
            Q: {"3": 10, "4": 20, "5": 30},
            J: {"3": 10, "4": 20, "5": 30},
            S: {"3": 10, "4": 20, "5": 30}
        },
        "20":
        {
            "9": {"3": 20, "4": 40, "5": 60},
            "10": {"3": 20, "4": 40, "5": 60},
            A: {"3": 20, "4": 40, "5": 60},
            K: {"3": 20, "4": 40, "5": 60},
            Q: {"3": 20, "4": 40, "5": 60},
            J: {"3": 20, "4": 40, "5": 60},
            S: {"3": 20, "4": 40, "5": 60}
        },
        "30":
        {
            "9": {"3": 30, "4": 60, "5": 90},
            "10": {"3": 30, "4": 60, "5": 90},
            A: {"3": 30, "4": 60, "5": 90},
            K: {"3": 30, "4": 60, "5": 90},
            Q: {"3": 30, "4": 60, "5": 90},
            J: {"3": 30, "4": 60, "5": 90},
            S: {"3": 30, "4": 60, "5": 90}
        },
        "40":
        {
            "9": {"3": 40, "4": 80, "5": 120},
            "10": {"3": 40, "4": 80, "5": 120},
            A: {"3": 40, "4": 80, "5": 120},
            K: {"3": 40, "4": 80, "5": 120},
            Q: {"3": 40, "4": 80, "5": 120},
            J: {"3": 40, "4": 80, "5": 120},
            S: {"3": 40, "4": 80, "5": 120}
        },
        "50":
        {
            "9": {"3": 50, "4": 100, "5": 150},
            "10": {"3": 50, "4": 100, "5": 150},
            A: {"3": 50, "4": 100, "5": 150},
            K: {"3": 50, "4": 100, "5": 150},
            Q: {"3": 50, "4": 100, "5": 150},
            J: {"3": 50, "4": 100, "5": 150},
            S: {"3": 50, "4": 100, "5": 150}
        },
        "100":
        {
            "9": {"3": 100, "4": 200, "5": 300},
            "10": {"3": 100, "4": 200, "5": 300},
            A: {"3": 100, "4": 200, "5": 300},
            K: {"3": 100, "4": 200, "5": 300},
            Q: {"3": 100, "4": 200, "5": 300},
            J: {"3": 100, "4": 200, "5": 300},
            S: {"3": 100, "4": 200, "5": 300}
        }
        });*/
    }

    @Test
    public void testIsItemWild() {
        ReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        assertTrue(conf.isItemWild("W"));
        assertFalse(conf.isItemWild("A"));
    }

    @Test
    public void testIsItemScatter() {
        ReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        assertTrue(conf.isItemScatter("S"));
        assertFalse(conf.isItemScatter("A"));
    }

}
