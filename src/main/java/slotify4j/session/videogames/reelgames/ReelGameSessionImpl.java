package slotify4j.session.videogames.reelgames;

import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsController;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculator;

import java.util.Map;

public class ReelGameSessionImpl implements ReelGameSession {

    public ReelGameSessionImpl(ReelGameSessionConfig config, ReelGameSessionReelsController reelsController, ReelGameSessionWinCalculator winningCalculator) {

    }

    @Override
    public ReelGameSessionPaytableData getPaytable() {
        return null;
    }

    @Override
    public String[][] getReelsItems() {
        return new String[0][];
    }

    @Override
    public Map<Integer, ReelGameSessionWinningLineModel> getWinningLines() {
        return null;
    }

    @Override
    public Map<String, ReelGameSessionWinningScatterModel> getWinningScatters() {
        return null;
    }

    @Override
    public String[][] getReelsItemsSequences() {
        return new String[0][];
    }

    @Override
    public int getReelsItemsNumber() {
        return 0;
    }

    @Override
    public int getReelsNumber() {
        return 0;
    }

    @Override
    public long getCreditsAmount() {
        return 0;
    }

    @Override
    public void setCreditsAmount(long creditsAmount) {

    }

    @Override
    public long getWinningAmount() {
        return 0;
    }

    @Override
    public long[] getAvailableBets() {
        return new long[0];
    }

    @Override
    public boolean isBetAvailable(long bet) {
        return false;
    }

    @Override
    public long getBet() {
        return 0;
    }

    @Override
    public void setBet(long bet) {

    }

    @Override
    public boolean canPlayNextGame() {
        return false;
    }

    @Override
    public void play() {

    }
}
