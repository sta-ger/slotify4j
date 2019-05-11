package slotify4j.session;

public interface GameSession extends Playable {

    long getCreditsAmount();

    void setCreditsAmount(long creditsAmount);

    long getWinningAmount();

    long[] getAvailableBets();

    boolean isBetAvailable(long bet);

    long getBet();

    void setBet(long bet);

    boolean canPlayNextGame();

}
