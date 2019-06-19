package slotify4j.session.videogames.reelgames;

import java.util.HashMap;
import java.util.Map;

public interface ReelGameSessionPaytableData {

    static Map<Long, Map<String, Map<Integer, Long>>> createDefaultPaytableMap(
            long[] availableBets,
            String[] availableItems,
            int reelsNumber,
            String wildItemId
    ) {
        HashMap<Long, Map<String, Map<Integer, Long>>> r = new HashMap<>();
        for (long bet : availableBets) {
            r.put(bet, new HashMap<>());
            for (String itemId : availableItems) {
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

    long getWinningAmountForItem(String itemId, int numberOfItems, long bet);

}
