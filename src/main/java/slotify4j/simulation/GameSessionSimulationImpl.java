package slotify4j.simulation;

import slotify4j.session.GameSession;
import slotify4j.session.UnableToPlayException;

public class GameSessionSimulationImpl implements GameSessionSimulation {
    private final GameSession session;
    private final long numberOfRounds;
    private final ChangeBetStrategy changeBetStrategy;
    private final PlayStrategy playStrategy;

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
        for (i = 0; i < numberOfRounds; i++) {
            doBeforePlay();
            if (canPlayNextGame()) {
                doPlay();
            } else {
                break;
            }
        }
        onFinished();
    }

    private void onFinished() {
        if (onFinishedCallback != null) {
            onFinishedCallback.run();
        }
    }

    private boolean canPlayNextGame() {
        return session.canPlayNextGame();
    }

    private void setBetBeforePlay() {
        if (changeBetStrategy != null) {
            changeBetStrategy.setBetForPlay(session);
        }
    }

    private void doPlay() throws UnableToPlayException {
        currentGameNumber++;
        setBetBeforePlay();
        totalBet += session.getBet();
        session.play();
        totalReturn += session.getWinningAmount();
        calculateRtp();
        doAfterPlay();
    }

    private void doBeforePlay() {
        if (beforePlayCallback != null) {
            beforePlayCallback.run();
        }
    }

    private void doAfterPlay() {
        if (afterPlayCallback != null) {
            afterPlayCallback.run();
        }
    }

    private void calculateRtp() {
        rtp = (double) totalReturn / totalBet;
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
