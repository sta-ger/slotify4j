package slotify4j.session.videogames.reelgames;

import java.util.Map;

public class ReelGameSessionPaytableDataImpl implements ReelGameSessionPaytableData {
    private final Map<Long, Map<String, Map<Integer, Long>>> paytableMap;

    public ReelGameSessionPaytableDataImpl(Map<Long, Map<String, Map<Integer, Long>>> paytableMap) {
        this.paytableMap = paytableMap;
    }

    @Override
    public long getWinningAmountForItem(String itemId, int numberOfItems, long bet) {
        long rv = 0;
        if (
                paytableMap.get(bet) != null &&
                paytableMap.get(bet).get(itemId) != null &&
                paytableMap.get(bet).get(itemId).get(numberOfItems) != null
        ) {
            rv = paytableMap.get(bet).get(itemId).get(numberOfItems);
        }
        return rv;
    }
}
