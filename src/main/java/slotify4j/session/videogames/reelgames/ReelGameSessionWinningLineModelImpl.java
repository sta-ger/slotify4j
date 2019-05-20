package slotify4j.session.videogames.reelgames;

public class ReelGameSessionWinningLineModelImpl implements ReelGameSessionWinningLineModel {
    private final long winAmount;
    private final int[] direction;
    private final int lineId;
    private final int[] itemsPositions;
    private final int[] wildItemsPositions;
    private final String itemId;

    public ReelGameSessionWinningLineModelImpl(long winAmount, int[] direction, int lineId, int[] itemsPositions, int[] wildItemsPositions, String itemId) {
        this.winAmount = winAmount;
        this.direction = direction;
        this.lineId = lineId;
        this.itemsPositions = itemsPositions;
        this.wildItemsPositions = wildItemsPositions;
        this.itemId = itemId;
    }

    @Override
    public int[] getDirection() {
        return direction;
    }

    @Override
    public String getItemId() {
        return itemId;
    }

    @Override
    public int getLineId() {
        return lineId;
    }

    @Override
    public int[] getItemsPositions() {
        return itemsPositions;
    }

    @Override
    public int[] getWildItemsPositions() {
        return wildItemsPositions;
    }

    @Override
    public long getWinningAmount() {
        return winAmount;
    }
}
