package slotify4j.session.videogames.reelgames.reelscontroller;

import org.junit.jupiter.api.Test;
import slotify4j.session.videogames.reelgames.DefaultReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSessionTools;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ReelGameSessionReelsControllerImplTest {
    private final ReelGameSessionReelsControllerImpl reelsController;

    private final String[] availableItems = new String[]{
            "A",
            "K",
            "Q",
            "J",
            "10",
            "9",
    };

    private static final int REELS_NUMBER = 5;
    private static final int REELS_ITEMS_NUMBER = 3;

    ReelGameSessionReelsControllerImplTest() {
        String[][] sequences = ReelGameSessionTools.createItemsSequences(5, availableItems, 10);
        sequences[2] = Arrays.stream(sequences[2]).filter(item -> !"A".equals(item)).toArray(String[]::new);
        DefaultReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        conf.setReelsNumber(REELS_NUMBER);
        conf.setReelsItemsNumber(REELS_ITEMS_NUMBER);
        conf.setAvailableItems(availableItems);
        conf.setReelsItemsSequences(sequences);
        reelsController = new ReelGameSessionReelsControllerImpl(conf);
    }

    @Test
    public void transposeMatrixTest() {
        assertArrayEquals(ReelGameSessionTools.transposeItemsMatrix(new String[][]{
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
        assertEquals(ReelGameSessionTools.createItemsSequence(availableItems).length, availableItems.length);
    }

    @Test
    public void testCreateShuffledSequenceOfSpecifiedItemsAndNumbersOfItems() {
        assertEquals(Objects.requireNonNull(ReelGameSessionTools.createItemsSequence(availableItems, Map.of("A", 2))).length, availableItems.length + 1);
        assertEquals(Objects.requireNonNull(ReelGameSessionTools.createItemsSequence(availableItems, Map.of("A", 0))).length, availableItems.length - 1);

        Map<String, Integer> numbersOfItems = Map.of("A", 10, "K", 20, "Q", 30, "J", 40, "10", 50, "9", 60);

        assertEquals(Optional.of(ReelGameSessionTools.createItemsSequence(availableItems, numbersOfItems).length), numbersOfItems.values().stream().reduce(Integer::sum));
        assertEquals(ReelGameSessionTools.createItemsSequence(availableItems, 10).length, 10 * availableItems.length);
    }

    @Test
    public void testCreateShuffledSequencesForSpecifiedNumberOfReels() {
        assertEquals(ReelGameSessionTools.createItemsSequences(5, availableItems).length, 5);
        assertEquals(ReelGameSessionTools.createItemsSequences(3, availableItems).length, 3);
        Arrays.stream(ReelGameSessionTools.createItemsSequences(3, availableItems)).forEach(curItems -> assertEquals(curItems.length, availableItems.length));
    }

    @Test
    public void testCcreateShuffledSequencesForSpecifiedNumberOfReelsAndNumbersOfItems() {
        String[][] items = ReelGameSessionTools.createItemsSequences(5, availableItems, Map.of(
                0, Map.of("A", 0),
                1, Map.of("A", 0),
                3, Map.of("A", 0),
                4, Map.of("A", 0)
        ));

        assertEquals(items.length, 5);

        IntStream.range(0, items.length).forEach(i -> {
            String[] curItems = items[i];
            if (i == 2) {
                assertEquals(curItems.length, availableItems.length);
            } else {
                assertEquals(curItems.length, availableItems.length - 1);
            }
        });

        Map<Integer, Map<String, Integer>> itemsNumbersMap = Map.of(
                0, new HashMap<>(),
                1, new HashMap<>(),
                2, new HashMap<>(),
                3, new HashMap<>(),
                4, new HashMap<>()
        );

        itemsNumbersMap.forEach((reelId, sequenceMap) -> {
            sequenceMap.put("A", 10);
            sequenceMap.put("K", 20);
            sequenceMap.put("Q", 30);
            sequenceMap.put("J", 40);
            sequenceMap.put("10", 50);
            sequenceMap.put("9", 60);
        });

        String[][] itemsForMapOfNumbers = ReelGameSessionTools.createItemsSequences(5, availableItems, itemsNumbersMap);
        IntStream.range(0, items.length).forEach(i -> {
            String[] curItems = itemsForMapOfNumbers[i];
            assertEquals(Optional.of(curItems.length), itemsNumbersMap.get(i).values().stream().reduce((sum, num) -> sum + num));
        });

        String[][] itemsForNumberOfNumbers = ReelGameSessionTools.createItemsSequences(5, availableItems, 10);
        IntStream.range(0, items.length).forEach(i -> {
            String[] curItems = itemsForNumberOfNumbers[i];
            assertEquals(curItems.length, availableItems.length * 10);
        });
    }

    @Test
    public void getRandomItemTest() {
        for (int i = 0; i < REELS_NUMBER; i++) {
            //For each reel
            for (int j = 0; j < 1000; j++) {
                //Check is returned item one of available items
                String item = reelsController.getRandomItem(i);
                assertTrue(Arrays.asList(availableItems).contains(item));
                if (i == 2) {
                    //and is not equal to symbol "A" removed from third reel's sequence
                    assertNotEquals(item, "A");
                }
            }
        }
    }

    @Test
    public void getRandomReelItemsTest() {
        for (int i = 0; i < REELS_NUMBER; i++) {
            //For each reel
            for (int j = 0; j < 1000; j++) {
                String[] items = reelsController.getRandomReelItems(i);
                assertEquals(items.length, REELS_ITEMS_NUMBER);
                int finalI = i;
                Arrays.stream(items).forEach(item -> {
                    //Check is returned item one of available items
                    assertTrue(Arrays.asList(availableItems).contains(item));
                    if (finalI == 2) {
                        //and is not equal to symbol "A" removed from third reel's sequence
                        assertNotEquals(item, "A");
                    }
                });
            }
        }
    }

    @Test
    public void getRandomItemsCombinationTest() {
        String[][] items = reelsController.getRandomItemsCombination();
        assertEquals(items.length, REELS_NUMBER);
        for (int i = 0; i < REELS_NUMBER; i++) {
            //For each reel
            for (int j = 0; j < 1000; j++) {
                assertEquals(items[i].length, REELS_ITEMS_NUMBER);
                int finalI = i;
                Arrays.stream(items[i]).forEach(item -> {
                    //Check is returned item one of available items
                    assertTrue(Arrays.asList(availableItems).contains(item));
                    if (finalI == 2) {
                        //and is not equal to symbol "A" removed from third reel's sequence
                        assertNotEquals(item, "A");
                    }
                });
            }
        }
    }

}
