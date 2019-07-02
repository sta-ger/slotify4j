package slotify4j.session.videogames.reelgames;

import java.util.HashMap;
import java.util.Map;

public interface ReelGameWithFreeGamesSessionConfig extends ReelGameSessionConfig {

    int getFreeGamesForScatters(String itemId, int numberOfItems);

    void setFreeGamesForScatters(String itemId, int numberOfItems, int freeGamesNum);

}
