package slotify4j.session.videogames.reelgames;

import org.apache.commons.math3.util.Pair;

public class ReelGameSessionScattersDataImpl implements ReelGameSessionScattersData {
    private final String[] scattersItemsIds;
    private final Pair<String, Integer> minimumItemsNumForScatterWin;

    public ReelGameSessionScattersDataImpl(String[] scattersItemsIds, Pair<String, Integer> minimumItemsNumForScatterWin) {
        this.scattersItemsIds = scattersItemsIds;
        this.minimumItemsNumForScatterWin = minimumItemsNumForScatterWin;
    }

    @Override
    public String[] getScattersItemsIds() {
        return scattersItemsIds;
    }

    @Override
    public Pair<String, Integer> getMinimumItemsNumForScatterWin() {
        return minimumItemsNumForScatterWin;
    }
}
