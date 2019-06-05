package slotify4j.session.videogames.reelgames;

import java.util.HashMap;
import java.util.Map;

public class DefaultReelGameWithFreeGamesSessionConfig extends DefaultReelGameSessionConfig implements ReelGameWithFreeGamesSessionConfig {
    public static int DEFAULT_FREE_GAMES_FOR_SCATTERS_NUM = 10;

    public static Map<String, Map<Integer, Integer>> createDefaultFreeGamesForScattersMap() {
        Map<String, Map<Integer, Integer>> rv = new HashMap<>();
        Map<Integer, Integer> entry = new HashMap<>();
        entry.put(3, DEFAULT_FREE_GAMES_FOR_SCATTERS_NUM);
        rv.put(DefaultReelGameSessionConfig.DEFAULT_SCATTER_ITEM_ID, entry);
        return rv;
    }

    private Map<String, Map<Integer, Integer>> freeGamesForScattersMap;

    public DefaultReelGameWithFreeGamesSessionConfig() {
        this.freeGamesForScattersMap = createDefaultFreeGamesForScattersMap();
    }

    @Override
    public int getFreeGamesForScatters(String itemId, int numberOfItems) {
        return freeGamesForScattersMap.getOrDefault(itemId, new HashMap<>()).getOrDefault(numberOfItems, 0);
    }

    @Override
    public void setFreeGamesForScatters(String itemId, int numberOfItems, int freeGamesNum) {
        if (!freeGamesForScattersMap.containsKey(itemId)) {
            freeGamesForScattersMap.put(itemId, new HashMap<>());
        }
        freeGamesForScattersMap.get(itemId).put(numberOfItems, freeGamesNum);
    }

}
