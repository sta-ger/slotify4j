package slotify4j.session.videogames.reelgames;

import java.util.Map;

public class ReelGameSessionPaytableDataImpl implements ReelGameSessionPaytableData {
    private Map<Long, Map<String, Map<Integer, Long>>> paytableMap;

    public ReelGameSessionPaytableDataImpl(Map<Long, Map<String, Map<Integer, Long>>> paytableMap) {
        this.paytableMap = paytableMap;
    }

    @Override
    public long getWinningAmountForItem(String itemId, int numberOfItems) {
        return 0;
    }

}
