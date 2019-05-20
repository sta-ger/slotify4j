package slotify4j.session.videogames.reelgames;

import java.util.HashMap;
import java.util.Map;

public class ReelGameSessionPaytableDataImpl implements ReelGameSessionPaytableData {

    public static Map<Long, Map<String, Map<Integer, Long>>> createDefaultPaytableMap(long[] availableBets, String[] availableItems, int reelsNumber, String wildItemId) {
        HashMap<Long, Map<String, Map<Integer, Long>>> r = new HashMap<>();
        for (int i = 0; i < availableBets.length; i++) {
            long bet = availableBets[i];
            r.put(bet, new HashMap<>());
            for (int j = 0; j < availableItems.length; j++) {
                String itemId = availableItems[j];
                if (!itemId.equals(wildItemId)) {
                    r.get(bet).put(itemId, new HashMap<>());
                    for (int k = 3; k <= reelsNumber; k++) {
                        r.get(bet).get(itemId).put(k, (k - 2) * bet);
                    }
                }
            }
        }
        return r;
    }

    private Map<Long, Map<String, Map<Integer, Long>>> paytableMap;

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
