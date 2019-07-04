package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReelGameSessionToolsTest {

    @Test
    public void reelGameSessionToolsTest() {
        assertThrows(IllegalStateException.class, ReelGameSessionTools::new);
    }

}
