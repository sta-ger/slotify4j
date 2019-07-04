package slotify4j.session.videogames.reelgames;

import slotify4j.session.GameSessionConfigBuilder;

public interface ReelGameSessionConfigBuilder extends GameSessionConfigBuilder {
    ReelGameSessionConfigBuilder withAvailableBets(long[] value);
    ReelGameSessionConfigBuilder withCreditsAmount(long value);
    ReelGameSessionConfigBuilder withBet(long value);


    ReelGameSessionConfigBuilder withReelsNumber(int value);

    ReelGameSessionConfigBuilder withReelsItemsNumber(int value);

    ReelGameSessionConfigBuilder withAvailableItems(String[] value);

    ReelGameSessionConfigBuilder withReelsItemsSequences(String[][] value);

    ReelGameSessionConfigBuilder withPaytable(ReelGameSessionPaytableData value);

    ReelGameSessionConfigBuilder withWildItemId(String value);

    ReelGameSessionConfigBuilder withScattersData(ReelGameSessionScatterData[] value);

    ReelGameSessionConfigBuilder withLinesDirections(ReelGameSessionLinesDirectionData value);

    ReelGameSessionConfigBuilder withWildsMultipliers(ReelGameSessionWildsMultipliersData value);

    ReelGameSessionConfig build();

}
