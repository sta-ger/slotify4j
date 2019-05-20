package slotify4j.session.videogames.reelgames.wincalculator;

import slotify4j.session.videogames.reelgames.ReelGameSessionWinningLineModel;
import slotify4j.session.videogames.reelgames.ReelGameSessionWinningScatterModel;

import java.util.Map;

public interface ReelGameSessionWinCalculator {

    void setGameState(long bet, String[][] items) throws Exception;

    Map<String, ReelGameSessionWinningLineModel> getWinningLines();

    Map<String, ReelGameSessionWinningScatterModel> getWinningScatters();

    long getWinningAmount();

    long getLinesWinning();

    long getScattersWinning();

}
