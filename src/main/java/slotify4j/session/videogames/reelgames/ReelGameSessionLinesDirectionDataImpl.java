package slotify4j.session.videogames.reelgames;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReelGameSessionLinesDirectionDataImpl implements ReelGameSessionLinesDirectionData {

    public static Map<Integer, int[]> createDefaultLinesDirectionsMap(int reelsNumber, int reelsItemsNumber) {
        HashMap<Integer, int[]> r = new HashMap<>();
        for (int i = 0; i < reelsItemsNumber; i++) {
            r.put(i, new int[reelsNumber]);
            for (int j = 0; j < reelsNumber; j++) {
                r.get(i)[j] = i;
            }
        }
        return r;
    }

    private final Map<Integer, int[]> linesDirectionsMap;

    public ReelGameSessionLinesDirectionDataImpl(Map<Integer, int[]> linesDirectionsMap) {
        this.linesDirectionsMap = linesDirectionsMap;
    }

    @Override
    public int[] getVerticalItemsPositionsForLineId(int lineId) {
        return linesDirectionsMap.get(lineId);
    }

    @Override
    public int[] getLinesIds() {
        Set<Integer> keysSet = linesDirectionsMap.keySet();
        int[] intKeys = new int[keysSet.size()];
        int i = 0;
        for(Integer element : keysSet) intKeys[i++] = element;
        return intKeys;
    }

}
