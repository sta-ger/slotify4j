package slotify4j.session.videogames.reelgames;

import java.util.HashMap;
import java.util.Map;

public interface ReelGameSessionLinesDirectionData {

    static Map<Integer, int[]> createDefaultLinesDirectionsMap(int reelsNumber, int reelsItemsNumber) {
        HashMap<Integer, int[]> r = new HashMap<>();
        for (int i = 0; i < reelsItemsNumber; i++) {
            r.put(i, new int[reelsNumber]);
            for (int j = 0; j < reelsNumber; j++) {
                r.get(i)[j] = i;
            }
        }
        return r;
    }

    int[] getVerticalItemsPositionsForLineId(int lineId);

    int[] getLinesIds();

}
