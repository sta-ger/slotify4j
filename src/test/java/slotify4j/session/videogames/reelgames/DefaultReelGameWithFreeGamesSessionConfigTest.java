package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultReelGameWithFreeGamesSessionConfigTest {

    @Test
    public void getFreeGamesForScatters() {
        DefaultReelGameWithFreeGamesSessionConfig conf = new DefaultReelGameWithFreeGamesSessionConfig();
        assertEquals(conf.getFreeGamesForScatters(DefaultReelGameSessionConfig.DEFAULT_SCATTER_ITEM_ID, DefaultReelGameSessionConfig.DEFAULT_MINIMUM_ITEMS_NUM_FOR_SCATTER_WIN), DefaultReelGameWithFreeGamesSessionConfig.DEFAULT_FREE_GAMES_FOR_SCATTERS_NUM);
        assertEquals(conf.getFreeGamesForScatters(DefaultReelGameSessionConfig.DEFAULT_SCATTER_ITEM_ID, 1), 0);
        assertEquals(conf.getFreeGamesForScatters("A", 3), 0);

        conf.setFreeGamesForScatters("SCTR", 3, 20);
        assertEquals(conf.getFreeGamesForScatters("SCTR", 3), 20);
        assertEquals(conf.getFreeGamesForScatters("SCTR", 2), 0);
    }

}
