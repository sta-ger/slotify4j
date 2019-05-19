package slotify4j.session.videogames.reelgames.reelscontroller;

import org.junit.Test;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ReelGameSessionReelsControllerImplTest {
    private String[] availableItems = new String[]{
            "A",
            "K",
            "Q",
            "J",
            "10",
            "9",
    };

    @Test
    public void transposeMatrixTest() {
        assertArrayEquals(ReelGameSessionReelsControllerImpl.transposeItemsMatrix(new String[][]{
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
        }), new String[][]{
                {"1", "5"},
                {"2", "6"},
                {"3", "7"},
                {"4", "8"},
        });
    }

    @Test
    public void testCreateShuffledSequenceOfSpecifiedItems() {
        assertEquals(ReelGameSessionReelsControllerImpl.createItemsSequence(availableItems).length, availableItems.length);
    }

    @Test
    public void testCreateShuffledSequenceOfSpecifiedItemsAndCountsOfItems() {
        assertEquals(Objects.requireNonNull(ReelGameSessionReelsControllerImpl.createItemsSequence(availableItems, new HashMap<>() {{
            put("A", 2);
        }})).length, availableItems.length + 1);

        assertEquals(Objects.requireNonNull(ReelGameSessionReelsControllerImpl.createItemsSequence(availableItems, new HashMap<>() {{
            put("A", 0);
        }})).length, availableItems.length - 1);

        HashMap<String, Integer> counts = new HashMap<>() {{
            put("A", 10);
            put("K", 20);
            put("Q", 30);
            put("J", 40);
            put("10", 50);
            put("9", 60);
        }};

        assertEquals(Optional.of(ReelGameSessionReelsControllerImpl.createItemsSequence(availableItems, counts).length), counts.values().stream().reduce((Integer s, Integer item) -> s + item));
        assertEquals(ReelGameSessionReelsControllerImpl.createItemsSequence(availableItems, 10).length, 10 * availableItems.length);
    }

}
