package slotify4j.session.videogames.reelgames;

public interface ReelGameSessionWinningLineModel {

    ReelGameSessionLinesDirectionData getDirection();

    String getItemId();

    String getLineId();

    int[] getItemsPositions();

    int[] getWildItemsPositions();

    long getWinningAmount();

}
