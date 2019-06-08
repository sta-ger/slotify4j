package slotify4j.session.videogames.reelgames.reelscontroller;

import org.junit.jupiter.api.Test;
import slotify4j.session.videogames.reelgames.DefaultReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSessionConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
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

    public ReelGameSessionReelsControllerImplTest() {
        String[][] sequences = ReelGameSessionReelsControllerImpl.createItemsSequences(5, availableItems, 10);
        sequences[2] = Arrays.stream(sequences[2]).filter(item -> !item.equals("A")).toArray(String[]::new);
        DefaultReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        conf.setReelsNumber(reelsNumber);
        conf.setReelsItemsNumber(reelsItemsNumber);
        conf.setAvailableItems(availableItems);
        conf.setReelsItemsSequences(sequences);
        reelsController = new ReelGameSessionReelsControllerImpl(conf);
    }

    @Test
    void transposeMatrixTest() {
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
    void testCreateShuffledSequenceOfSpecifiedItems() {
        assertEquals(ReelGameSessionReelsControllerImpl.createItemsSequence(availableItems).length, availableItems.length);
    }

    @Test
    void testCreateShuffledSequenceOfSpecifiedItemsAndCountsOfItems() {
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

    @Test
    void testCreateShuffledSequencesForSpecifiedNumberOfReels() {
        assertEquals(ReelGameSessionReelsControllerImpl.createItemsSequences(5, availableItems).length, 5);
        assertEquals(ReelGameSessionReelsControllerImpl.createItemsSequences(3, availableItems).length, 3);
        Arrays.stream(ReelGameSessionReelsControllerImpl.createItemsSequences(3, availableItems)).forEach(curItems -> assertEquals(curItems.length, availableItems.length));
    }

    @Test
    void testCcreateShuffledSequencesForSpecifiedNumberOfReelsAndCountsOfItems() {
        String[][] items = ReelGameSessionReelsControllerImpl.createItemsSequences(5, availableItems, new HashMap<>() {{
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

        HashMap<Integer, HashMap<String, Integer>> itemsNumbersMap = new HashMap<>() {{
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

        String[][] itemsForMapOfNumbers = ReelGameSessionReelsControllerImpl.createItemsSequences(5, availableItems, itemsNumbersMap);
        IntStream.range(0, items.length).forEach((i) -> {
            String[] curItems = itemsForMapOfNumbers[i];
            assertEquals(Optional.of(curItems.length), itemsNumbersMap.get(i).values().stream().reduce((sum, num) -> sum += num));
        });

        String[][] itemsForNumberOfNumbers = ReelGameSessionReelsControllerImpl.createItemsSequences(5, availableItems, 10);
        IntStream.range(0, items.length).forEach((i) -> {
            String[] curItems = itemsForNumberOfNumbers[i];
            assertEquals(curItems.length, availableItems.length * 10);
        });
    }

    @Test
    void getRandomItemTest() {
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
    void getRandomReelItemsTest() {
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
    void getRandomItemsCombinationTest() {
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
