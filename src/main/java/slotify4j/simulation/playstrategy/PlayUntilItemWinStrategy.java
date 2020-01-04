package slotify4j.simulation.playstrategy;

import slotify4j.session.GameSession;
import slotify4j.session.videogames.reelgames.ReelGameSession;
import slotify4j.session.videogames.reelgames.ReelGameSessionWinningLineModel;
import slotify4j.session.videogames.reelgames.ReelGameSessionWinningScatterModel;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameWinCalculatorTools;

import java.util.Map;

public class PlayUntilItemWinStrategy implements PlayStrategy {
    private final String itemId;
    private int minLinesNumber = 1;
    private boolean onlySameItemId = false;
    private boolean allowWilds = true;
    private String wildItemId;

    public PlayUntilItemWinStrategy(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean canPlayNextGame(GameSession session) {
        ReelGameSession rgSession = ((ReelGameSession) session);
        String[][] reelsItems = rgSession.getReelsItems();
        Map<Integer, ReelGameSessionWinningLineModel> winningLines = rgSession.getWinningLines();
        ReelGameSessionWinningLineModel[] winningLinesArray = winningLines.values().toArray(
                new ReelGameSessionWinningLineModel[0]
        );
        Map<String, ReelGameSessionWinningScatterModel> winningScatters = rgSession.getWinningScatters();
        return winningLines.size() < minLinesNumber ||
                winningScatters.size() > 0 ||
                ReelGameWinCalculatorTools.getLinesWithWinningItem(
                        winningLinesArray,
                        itemId
                ).length == 0 ||
                onlySameItemId && !ReelGameWinCalculatorTools.isAllLinesHasSameItemId(winningLinesArray) ||
                !allowWilds && ReelGameWinCalculatorTools.getLinesWithItem(
                        winningLinesArray,
                        reelsItems,
                        wildItemId
                ).length > 0;
    }

    public int getMinLinesNumber() {
        return minLinesNumber;
    }

    public void setMinLinesNumber(int minLinesNumber) {
        this.minLinesNumber = minLinesNumber;
    }

    public boolean isOnlySameItemId() {
        return onlySameItemId;
    }

    public void setOnlySameItemId(boolean onlySameItemId) {
        this.onlySameItemId = onlySameItemId;
    }

    public boolean isAllowWilds() {
        return allowWilds;
    }

    public void setAllowWilds(boolean allowWilds) {
        this.allowWilds = allowWilds;
    }

    public String getWildItemId() {
        return wildItemId;
    }

    public void setWildItemId(String wildItemId) {
        this.wildItemId = wildItemId;
    }

}
