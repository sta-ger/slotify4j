package slotify4j.session.videogames.reelgames.reelscontroller;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReelGameSessionReelsControllerImplTest {
    String[] availableItems = new String[]{
            "A",
            "K",
            "Q",
            "J",
            "10",
            "9",
    };

    @Test
    public void transposeMatrixTest() {
        assertArrayEquals(ReelGameSessionReelsControllerImpl.transposeMatrix(new Integer[][]{
                new Integer[]{1, 2, 3, 4},
                new Integer[]{5, 6, 7, 8},
        }), new Integer[][]{
                new Integer[]{1, 5},
                new Integer[]{2, 6},
                new Integer[]{3, 7},
                new Integer[]{4, 8},
        });
    }

}
