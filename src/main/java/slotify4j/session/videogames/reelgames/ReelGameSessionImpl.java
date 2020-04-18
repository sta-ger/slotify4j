package slotify4j.session.videogames.reelgames;

import slotify4j.session.GameSessionImpl;
import slotify4j.session.UnableToPlayException;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsController;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculator;

import java.util.Map;

public class ReelGameSessionImpl implements ReelGameSession {
    private final GameSessionImpl adaptee;
    private final ReelGameSessionConfig config;
    private final ReelGameSessionReelsController reelsController;
    private final ReelGameSessionWinCalculator winningCalculator;
    private long winningAmount;
    private String[][] reelsItems = new String[0][0];

    public ReelGameSessionImpl(
            ReelGameSessionConfig config,
            ReelGameSessionReelsController reelsController,
            ReelGameSessionWinCalculator winningCalculator
    ) {
        this.config = config;
        this.reelsController = reelsController;
        this.winningCalculator = winningCalculator;
        adaptee = new GameSessionImpl(config);
    }

    @Override
    public ReelGameSessionPaytableData getPaytable() {
        return this.config.getPaytable();
    }

    @Override
    public String[][] getReelsItems() {
        return reelsItems.clone();
    }

    @Override
    public Map<Integer, ReelGameSessionWinningLineModel> getWinningLines() {
        return winningCalculator.getWinningLines();
    }

    @Override
    public Map<String, ReelGameSessionWinningScatterModel> getWinningScatters() {
        return winningCalculator.getWinningScatters();
    }

    @Override
    public String[][] getReelsItemsSequences() {
        return config.getReelsItemsSequences();
    }

    @Override
    public int getReelsItemsNumber() {
        return config.getReelsItemsNumber();
    }

    @Override
    public int getReelsNumber() {
        return config.getReelsNumber();
    }

    @Override
    public String[] getAvailableItems() {
        return config.getAvailableItems().clone();
    }

    @Override
    public long getCreditsAmount() {
        return adaptee.getCreditsAmount();
    }

    @Override
    public void setCreditsAmount(long creditsAmount) {
        adaptee.setCreditsAmount(creditsAmount);
    }

    @Override
    public long getWinningAmount() {
        return winningAmount;
    }

    @Override
    public long[] getAvailableBets() {
        return config.getAvailableBets();
    }

    @Override
    public boolean isBetAvailable(long bet) {
        return adaptee.isBetAvailable(bet);
    }

    @Override
    public long getBet() {
        return adaptee.getBet();
    }

    @Override
    public void setBet(long bet) {
        adaptee.setBet(bet);
    }

    @Override
    public boolean canPlayNextGame() {
        return adaptee.canPlayNextGame();
    }

    @Override
    public void play() throws UnableToPlayException {
        adaptee.play();
        reelsItems = reelsController.getRandomItemsCombination();
        winningCalculator.setGameState(this.getBet(), reelsItems);
        winningAmount = winningCalculator.getWinningAmount();
        this.setCreditsAmount(this.getCreditsAmount() + winningAmount);
    }
}
