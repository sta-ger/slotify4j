package slotify4j.session.videogames.reelgames;

import java.util.HashMap;
import java.util.Map;

public interface ReelGameWithFreeGamesSessionConfig extends ReelGameSessionConfig {

    static Map<String, Map<Integer, Integer>> createDefaultFreeGamesForScattersMap() {
        Map<String, Map<Integer, Integer>> rv = new HashMap<>();
        Map<Integer, Integer> entry = new HashMap<>();
        entry.put(
                DefaultReelGameSessionConfig.DEFAULT_MINIMUM_ITEMS_NUM_FOR_SCATTER_WIN,
                DefaultReelGameWithFreeGamesSessionConfig.DEFAULT_FREE_GAMES_FOR_SCATTERS_NUM
        );
        rv.put(DefaultReelGameSessionConfig.DEFAULT_SCATTER_ITEM_ID, entry);
        return rv;
    }

    int getFreeGamesForScatters(String itemId, int numberOfItems);

    void setFreeGamesForScatters(String itemId, int numberOfItems, int freeGamesNum);

}
