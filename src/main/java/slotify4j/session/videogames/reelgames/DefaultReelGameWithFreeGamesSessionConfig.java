package slotify4j.session.videogames.reelgames;

import java.util.HashMap;
import java.util.Map;

public class DefaultReelGameWithFreeGamesSessionConfig
        extends DefaultReelGameSessionConfig
        implements ReelGameWithFreeGamesSessionConfig {
    
    public static final int DEFAULT_FREE_GAMES_FOR_SCATTERS_NUM = 10;

    private final Map<String, Map<Integer, Integer>> freeGamesForScattersMap;

    public DefaultReelGameWithFreeGamesSessionConfig() {
        this.freeGamesForScattersMap = ReelGameWithFreeGamesSessionConfig.createDefaultFreeGamesForScattersMap();
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
