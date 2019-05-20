package slotify4j.session.videogames.reelgames;

public interface ReelGameSessionWinningLineModel {

    int[] getDirection();

    String getItemId();

    int getLineId();

    int[] getItemsPositions();

    int[] getWildItemsPositions();

    long getWinningAmount();

}
