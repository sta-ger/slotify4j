package slotify4j.session.videogames.reelgames;

public interface ReelGameSessionPaytableData {

    long getWinningAmountForItem(String itemId, int numberOfItems, long bet);

}
