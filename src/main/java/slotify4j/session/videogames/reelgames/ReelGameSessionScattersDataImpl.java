package slotify4j.session.videogames.reelgames;

public class ReelGameSessionScattersDataImpl implements ReelGameSessionScattersData {
    private final String itemId;
    private final int minimumItemsNumForScatterWin;

    public ReelGameSessionScattersDataImpl(String itemId, int minimumItemsNumForScatterWin) {
        this.itemId = itemId;
        this.minimumItemsNumForScatterWin = minimumItemsNumForScatterWin;
    }

    @Override
    public String getItemId() {
        return itemId;
    }

    @Override
    public int getMinimumItemsNumForScatterWin() {
        return minimumItemsNumForScatterWin;
    }
}
