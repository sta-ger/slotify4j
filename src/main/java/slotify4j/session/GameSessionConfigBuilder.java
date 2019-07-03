package slotify4j.session;

public interface GameSessionConfigBuilder {

    GameSessionConfigBuilder withAvailableBets(long[] value);

    GameSessionConfigBuilder withCreditsAmount(long value);

    GameSessionConfigBuilder withBet(long value);

    GameSessionConfig build();

}
