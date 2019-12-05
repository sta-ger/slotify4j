package slotify4j.simulation;

import slotify4j.session.GameSession;
import slotify4j.session.UnableToPlayException;

public class GameSessionSimulationImpl implements GameSessionSimulation {
    private final GameSession session;
    private final long numberOfRounds;
    private final GameSessionSimulationChangeBetStrategy changeBetStrategy;
    private final GameSessionSimulationPlayStrategy playStrategy;

    private long totalBet;
    private long totalReturn;
    private double rtp;

    private long currentGameNumber;

    private Runnable beforePlayCallback;
    private Runnable afterPlayCallback;
    private Runnable onFinishedCallback;

    public GameSessionSimulationImpl(GameSession session, GameSessionSimulationConfig config) {
        this.session = session;
        numberOfRounds = config.getNumberOfRounds();
        changeBetStrategy = config.getChangeBetStrategy();
        playStrategy = config.getPlayStrategy();
    }

    @Override
    public void run() throws UnableToPlayException {
        long i;
        for (i = 0; i < this.numberOfRounds; i++) {
            this.doBeforePlay();
            if (this.canPlayNextGame()) {
                this.doPlay();
            } else {
                break;
            }
        }
        this.onFinished();
    }

    private void onFinished() {
        if (this.onFinishedCallback != null) {
            this.onFinishedCallback.run();
        }
    }

    private boolean canPlayNextGame() {
        return this.session.canPlayNextGame();
    }

    private void setBetBeforePlay() {
        this.changeBetStrategy.setBetForPlay(session);
    }

    private void doPlay() throws UnableToPlayException {
        this.currentGameNumber++;
        this.setBetBeforePlay();
        this.totalBet += this.session.getBet();
        this.session.play();
        this.totalReturn += this.session.getWinningAmount();
        this.calculateRtp();
        this.doAfterPlay();
    }

    private void doBeforePlay() {
        if (this.beforePlayCallback != null) {
            this.beforePlayCallback.run();
        }
    }

    private void doAfterPlay() {
        if (this.afterPlayCallback != null) {
            this.afterPlayCallback.run();
        }
    }

    private void calculateRtp() {
        this.rtp = (double) this.totalReturn / this.totalBet;
    }

    @Override
    public double getRtp() {
        return rtp;
    }

    @Override
    public long getTotalBetAmount() {
        return totalBet;
    }

    @Override
    public long getTotalReturn() {
        return totalReturn;
    }

    @Override
    public long getCurrentGameNumber() {
        return currentGameNumber;
    }

    @Override
    public long getTotalGamesToPlayNumber() {
        return numberOfRounds;
    }

    @Override
    public void setBeforePlayCallback(Runnable callback) {
        beforePlayCallback = callback;
    }

    @Override
    public void removeBeforePlayCallback() {
        beforePlayCallback = null;
    }

    @Override
    public void setAfterPlayCallback(Runnable callback) {
        afterPlayCallback = callback;
    }

    @Override
    public void removeAfterPlayCallback() {
        afterPlayCallback = null;
    }

    @Override
    public void setOnFinishedCallback(Runnable callback) {
        onFinishedCallback = callback;
    }

    @Override
    public void removeOnFinishedCallback() {
        onFinishedCallback = null;
    }

}
