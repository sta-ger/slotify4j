package slotify4j.session.videogames.reelgames.reelscontroller;

import org.junit.jupiter.api.Test;
import slotify4j.session.videogames.reelgames.DefaultReelGameSessionConfig;

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

    private final int reelsNumber = 5;
    private final int reelsItemsNumber = 3;

    ReelGameSessionReelsControllerImplTest() {
        String[][] sequences = ReelGameSessionReelsController.createItemsSequences(5, availableItems, 10);
        sequences[2] = Arrays.stream(sequences[2]).filter(item -> !"A".equals(item)).toArray(String[]::new);
        DefaultReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        conf.setReelsNumber(reelsNumber);
        conf.setReelsItemsNumber(reelsItemsNumber);
        conf.setAvailableItems(availableItems);
        conf.setReelsItemsSequences(sequences);
        reelsController = new ReelGameSessionReelsControllerImpl(conf);
    }

    @Test
    public void transposeMatrixTest() {
        assertArrayEquals(ReelGameSessionReelsController.transposeItemsMatrix(new String[][]{
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
        assertEquals(ReelGameSessionReelsController.createItemsSequence(availableItems).length, availableItems.length);
    }

    @Test
    public void testCreateShuffledSequenceOfSpecifiedItemsAndNumbersOfItems() {
        assertEquals(Objects.requireNonNull(ReelGameSessionReelsController.createItemsSequence(availableItems, Map.of("A", 2))).length, availableItems.length + 1);
        assertEquals(Objects.requireNonNull(ReelGameSessionReelsController.createItemsSequence(availableItems, Map.of("A", 0))).length, availableItems.length - 1);

        Map<String, Integer> numbersOfItems = Map.of("A", 10, "K", 20, "Q", 30, "J", 40, "10", 50, "9", 60);

        assertEquals(Optional.of(ReelGameSessionReelsController.createItemsSequence(availableItems, numbersOfItems).length), numbersOfItems.values().stream().reduce(Integer::sum));
        assertEquals(ReelGameSessionReelsController.createItemsSequence(availableItems, 10).length, 10 * availableItems.length);
    }

    @Test
    public void testCreateShuffledSequencesForSpecifiedNumberOfReels() {
        assertEquals(ReelGameSessionReelsController.createItemsSequences(5, availableItems).length, 5);
        assertEquals(ReelGameSessionReelsController.createItemsSequences(3, availableItems).length, 3);
        Arrays.stream(ReelGameSessionReelsController.createItemsSequences(3, availableItems)).forEach(curItems -> assertEquals(curItems.length, availableItems.length));
    }

    @Test
    public void testCcreateShuffledSequencesForSpecifiedNumberOfReelsAndNumbersOfItems() {
        String[][] items = ReelGameSessionReelsController.createItemsSequences(5, availableItems, new HashMap<>() {{
            put(0, new HashMap<>() {{
                put("A", 0);
            }});
            put(1, new HashMap<>() {{
                put("A", 0);
            }});
            put(3, new HashMap<>() {{
                put("A", 0);
            }});
            put(4, new HashMap<>() {{
                put("A", 0);
            }});
        }});

        assertEquals(items.length, 5);

        IntStream.range(0, items.length).forEach((i) -> {
            String[] curItems = items[i];
            if (i == 2) {
                assertEquals(curItems.length, availableItems.length);
            } else {
                assertEquals(curItems.length, availableItems.length - 1);
            }
        });

        Map<Integer, Map<String, Integer>> itemsNumbersMap = new HashMap<>() {{
            put(0, new HashMap<>());
            put(1, new HashMap<>());
            put(2, new HashMap<>());
            put(3, new HashMap<>());
            put(4, new HashMap<>());
        }};

        itemsNumbersMap.forEach((reelId, sequenceMap) -> {
            sequenceMap.put("A", 10);
            sequenceMap.put("K", 20);
            sequenceMap.put("Q", 30);
            sequenceMap.put("J", 40);
            sequenceMap.put("10", 50);
            sequenceMap.put("9", 60);
        });

        String[][] itemsForMapOfNumbers = ReelGameSessionReelsController.createItemsSequences(5, availableItems, itemsNumbersMap);
        IntStream.range(0, items.length).forEach((i) -> {
            String[] curItems = itemsForMapOfNumbers[i];
            assertEquals(Optional.of(curItems.length), itemsNumbersMap.get(i).values().stream().reduce((sum, num) -> sum += num));
        });

        String[][] itemsForNumberOfNumbers = ReelGameSessionReelsController.createItemsSequences(5, availableItems, 10);
        IntStream.range(0, items.length).forEach((i) -> {
            String[] curItems = itemsForNumberOfNumbers[i];
            assertEquals(curItems.length, availableItems.length * 10);
        });
    }

    @Test
    public void getRandomItemTest() {
        for (int i = 0; i < reelsNumber; i++) {
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
        for (int i = 0; i < reelsNumber; i++) {
            //For each reel
            for (int j = 0; j < 1000; j++) {
                String[] items = reelsController.getRandomReelItems(i);
                assertEquals(items.length, reelsItemsNumber);
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
        assertEquals(items.length, reelsNumber);
        for (int i = 0; i < reelsNumber; i++) {
            //For each reel
            for (int j = 0; j < 1000; j++) {
                assertEquals(items[i].length, reelsItemsNumber);
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
