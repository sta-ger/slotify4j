package slotify4j.session.videogames.reelgames;

import java.util.HashMap;
import java.util.Map;

public interface ReelGameSessionLinesDirectionData {

    int[] getVerticalItemsPositionsForLineId(int lineId);

    int[] getLinesIds();

}
