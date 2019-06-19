package slotify4j.session.videogames.reelgames;

public class ReelGameSessionWinningScatterModelImpl implements ReelGameSessionWinningScatterModel {
    private final String itemId;
    private final int[][] itemsPositions;
    private final long winningAmount;

    public ReelGameSessionWinningScatterModelImpl(String itemId, int[][] itemsPositions, long winningAmount) {
        this.itemId = itemId;
        this.itemsPositions = itemsPositions.clone();
        this.winningAmount = winningAmount;
    }

    @Override
    public String getItemId() {
        return itemId;
    }

    @Override
    public int[][] getItemsPositions() {
        return itemsPositions.clone();
    }

    @Override
    public long getWinningAmount() {
        return winningAmount;
    }
}
