package slotify4j.session.videogames.reelgames;

import slotify4j.session.UnableToPlayException;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsController;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculator;

import java.util.Map;

public class ReelGameWithFreeGamesSessionImpl implements ReelGameWithFreeGamesSession {
    private final ReelGameSession adaptee;
    private final ReelGameWithFreeGamesSessionConfig config;
    private final ReelGameSessionWinCalculator winningCalculator;

    private int freeGamesNum;
    private int freeGamesSum;
    private long freeBank;

    public ReelGameWithFreeGamesSessionImpl(ReelGameWithFreeGamesSessionConfig config, ReelGameSessionReelsController reelsController, ReelGameSessionWinCalculator winningCalculator) {
        this.config = config;
        this.winningCalculator = winningCalculator;
        this.adaptee = new ReelGameSessionImpl(config, reelsController, winningCalculator);
    }

    @Override
    public int getWonFreeGamesNumber() {
        final int[] rv = {0};
        Map<String, ReelGameSessionWinningScatterModel> wonScatters = this.getWinningScatters();
        wonScatters.forEach((String key, ReelGameSessionWinningScatterModel scatterModel) -> {
            if (this.config.getFreeGamesForScatters(scatterModel.getItemId(), scatterModel.getItemsPositions().length) > 0) {
                rv[0] += this.config.getFreeGamesForScatters(scatterModel.getItemId(), scatterModel.getItemsPositions().length);
            }
        });
        return rv[0];
    }

    @Override
    public int getFreeGameNum() {
        return freeGamesNum;
    }

    @Override
    public void setFreeGameNum(int value) {
        freeGamesNum = value;
    }

    @Override
    public int getFreeGameSum() {
        return freeGamesSum;
    }

    @Override
    public void setFreeGameSum(int value) {
        freeGamesSum = value;
    }

    @Override
    public long getFreeGameBank() {
        return freeBank;
    }

    @Override
    public void setFreeGameBank(long value) {
        freeBank = value;
    }

    @Override
    public ReelGameSessionPaytableData getPaytable() {
        return config.getPaytable();
    }

    @Override
    public String[][] getReelsItems() {
        return adaptee.getReelsItems();
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
        return adaptee.getReelsItemsSequences();
    }

    @Override
    public int getReelsItemsNumber() {
        return adaptee.getReelsItemsNumber();
    }

    @Override
    public int getReelsNumber() {
        return adaptee.getReelsNumber();
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
        return adaptee.getWinningAmount();
    }

    @Override
    public long[] getAvailableBets() {
        return adaptee.getAvailableBets();
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
        long creditsBeforePlay;
        int wonFreeGames;
        if (this.freeGamesNum == this.freeGamesSum) {
            this.freeBank = 0;
            this.freeGamesNum = 0;
            this.freeGamesSum = 0;
        }
        creditsBeforePlay = this.getCreditsAmount();
        this.adaptee.play();
        if (this.freeGamesSum > 0 && this.freeGamesNum < this.freeGamesSum) {
            this.freeGamesNum++;
            this.freeBank += this.getWinningAmount();
            this.setCreditsAmount(creditsBeforePlay);
        }
        if (this.freeGamesSum > 0 && this.freeGamesNum == this.freeGamesSum) {
            this.setCreditsAmount(this.getCreditsAmount()+ this.freeBank);
        }
        wonFreeGames = this.getWonFreeGamesNumber();
        if (wonFreeGames > 0) {
            this.freeGamesSum += wonFreeGames;
        }
    }
}
