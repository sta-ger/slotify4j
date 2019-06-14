package slotify4j.session.videogames.reelgames;

import java.util.Map;
import java.util.Set;

public class ReelGameSessionLinesDirectionDataImpl implements ReelGameSessionLinesDirectionData {
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
