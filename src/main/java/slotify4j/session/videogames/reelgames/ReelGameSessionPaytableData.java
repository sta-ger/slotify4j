package slotify4j.session.videogames.reelgames;

import java.util.HashMap;
import java.util.Map;

public interface ReelGameSessionPaytableData {

    long getWinningAmountForItem(String itemId, int numberOfItems, long bet);

}
