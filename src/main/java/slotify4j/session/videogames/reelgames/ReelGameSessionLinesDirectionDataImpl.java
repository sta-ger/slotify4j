package slotify4j.session.videogames.reelgames;

import java.util.Map;

public class ReelGameSessionLinesDirectionDataImpl implements ReelGameSessionLinesDirectionData {
    private Map<Integer, int[]> linesDirectionsPair;

    public ReelGameSessionLinesDirectionDataImpl(Map<Integer, int[]> linesDirectionsPair) {
        this.linesDirectionsPair = linesDirectionsPair;
    }

    @Override
    public int[] getVerticalItemsPositionsForLineId(int lineId) {
        return linesDirectionsPair.get(lineId);
    }

}
