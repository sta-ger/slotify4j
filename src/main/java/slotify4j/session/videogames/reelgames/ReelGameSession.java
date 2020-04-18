package slotify4j.session.videogames.reelgames;

import slotify4j.session.GameSession;

import java.util.Map;

public interface ReelGameSession extends GameSession {

    ReelGameSessionPaytableData getPaytable();

    String[][] getReelsItems();

    Map<Integer, ReelGameSessionWinningLineModel> getWinningLines();

    Map<String, ReelGameSessionWinningScatterModel> getWinningScatters();

    String[][] getReelsItemsSequences();

    int getReelsItemsNumber();

    int getReelsNumber();

    String[] getAvailableItems();

}
