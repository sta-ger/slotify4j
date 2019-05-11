package slotify4j.session;

public interface GameSessionConfig {

    void setAvailableBets(long[] availableBets);

    long[] getAvailableBets();

    void setCreditsAmount(long creditsAmount);

    long getCreditsAmount();

    void setBet(long bet);

    long getBet();

}
