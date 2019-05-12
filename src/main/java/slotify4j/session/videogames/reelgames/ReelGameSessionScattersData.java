package slotify4j.session.videogames.reelgames;

import org.apache.commons.math3.util.Pair;

public interface ReelGameSessionScattersData {

    String[] getScattersItemsIds();

    Pair<String, Integer> getMinimumItemsNumForScatterWin();

}
