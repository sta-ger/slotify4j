package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ReelGameSessionToolsTest {

    @Test
    public void findSectorsWithItemsOnSequence() {
        String[] sequence = {
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
        };

        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"3", "4"}, 3), new int[]{2, 3});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"4", "3"}, 3), new int[]{2, 3});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"6", "7", "8"}, 3), new int[]{6});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"8", "7", "6"}, 3), new int[]{6});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"8", "9"}, 3), new int[]{7, 8});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"9", "8"}, 3), new int[]{7, 8});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"9", "8", "7"}, 3), new int[]{7});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"0", "2"}, 3), new int[]{0});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"2", "0"}, 3), new int[]{0});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"4", "6"}, 3), new int[]{4});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"6", "4"}, 3), new int[]{4});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"7", "9"}, 3), new int[]{7});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"9", "7"}, 3), new int[]{7});

        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"0", "1"}, 3), new int[]{0, 9});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"1", "0"}, 3), new int[]{0, 9});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"0", "9"}, 3), new int[]{8, 9});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"9", "0"}, 3), new int[]{8, 9});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"1", "9"}, 3), new int[]{9});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"9", "1"}, 3), new int[]{9});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"0", "8"}, 3), new int[]{8});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"8", "0"}, 3), new int[]{8});

        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"9", "8", "7", "6"}, 3), new int[]{});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"0", "3"}, 3), new int[]{});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"3", "0"}, 3), new int[]{});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"3", "7"}, 3), new int[]{});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"7", "3"}, 3), new int[]{});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"9", "6"}, 3), new int[]{});
        assertArrayEquals(ReelGameSessionTools.findSectorsWithItemsOnSequence(sequence, new String[]{"6", "9"}, 3), new int[]{});
    }

}
