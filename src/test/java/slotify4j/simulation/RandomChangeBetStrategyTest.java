package slotify4j.simulation;

import org.junit.jupiter.api.Test;
import slotify4j.session.GameSession;
import slotify4j.session.UnableToPlayException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class RandomChangeBetStrategyTest {

    @Test
    public void setBetForPlay() throws UnableToPlayException {
        ArrayList<Long> betsDuringPlay = new ArrayList<>();
        GameSession sessionMock = new GameSession() {
            @Override
            public void play() {
                /* no-op */
            }

            @Override
            public long getCreditsAmount() {
                return 1;
            }

            @Override
            public void setCreditsAmount(long creditsAmount) {
                /* no-op */
            }

            @Override
            public long getWinningAmount() {
                return 0;
            }

            @Override
            public long[] getAvailableBets() {
                return IntStream.range(1, 100).mapToLong(Long::valueOf).toArray();
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
                if (betsDuringPlay.stream().noneMatch(value -> value == bet)) {
                    betsDuringPlay.add(bet);
                }
            }

            @Override
            public boolean canPlayNextGame() {
                return true;
            }
        };
        GameSessionSimulationImpl simulation = new GameSessionSimulationImpl(
                sessionMock,
                DefaultGameSessionSimulationConfig
                        .builder()
                        .withNumberOfRounds(1000)
                        .withChangeBetStrategy(new RandomChangeBetStrategy())
                        .build()
        );

        simulation.run();

        long[] betsArrayOfSet = betsDuringPlay.stream().mapToLong(Long::valueOf).toArray();

        // Contents of betsDuringPlay after simulation should contain shuffled array of all possible bets
        assertFalse(Arrays.equals(
                betsArrayOfSet,
                sessionMock.getAvailableBets()
        ));
        assertArrayEquals(
                Arrays.stream(betsArrayOfSet).sorted().toArray(),
                sessionMock.getAvailableBets()
        );
    }

}
