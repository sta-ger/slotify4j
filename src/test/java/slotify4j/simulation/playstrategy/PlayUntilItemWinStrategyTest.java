package slotify4j.simulation.playstrategy;

import org.junit.jupiter.api.Test;
import slotify4j.session.GameSession;
import slotify4j.session.UnableToPlayException;
import slotify4j.session.videogames.reelgames.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayUntilItemWinStrategyTest {

    @Test
    public void canPlayNextGame() {
        Function<String, ReelGameSessionWinningLineModel> createLineWithWinningItem = (String itemId) ->
                new ReelGameSessionWinningLineModelImpl(1, new int[0], 1, new int[0], new int[0], itemId);

        Function<String, ReelGameSessionWinningLineModel> createLineWithWinningItemAndWilds = (String itemId) ->
                new ReelGameSessionWinningLineModelImpl(1, new int[]{0, 0, 0}, 1, new int[0], new int[]{1}, itemId);

        Map<Integer, ReelGameSessionWinningLineModel> noLines = new HashMap<>();

        Map<Integer, ReelGameSessionWinningLineModel> oneWithWinningItemA = Map.of(
                1, createLineWithWinningItem.apply("A")
        );

        Map<Integer, ReelGameSessionWinningLineModel> threeWithWinningItemA = Map.of(
                1, createLineWithWinningItem.apply("A"),
                2, createLineWithWinningItem.apply("A"),
                3, createLineWithWinningItem.apply("A")
        );

        Map<Integer, ReelGameSessionWinningLineModel> withDifferentItemsWithoutA = Map.of(
                1, createLineWithWinningItem.apply("K"),
                2, createLineWithWinningItem.apply("Q"),
                3, createLineWithWinningItem.apply("J")
        );

        Map<Integer, ReelGameSessionWinningLineModel> withDifferentItemsAndOneWithA = Map.of(
                1, createLineWithWinningItem.apply("A"),
                2, createLineWithWinningItem.apply("K"),
                3, createLineWithWinningItem.apply("Q")
        );

        Map<Integer, ReelGameSessionWinningLineModel> withDifferentItemsAndSeveralWithA = Map.of(
                1, createLineWithWinningItem.apply("A"),
                2, createLineWithWinningItem.apply("K"),
                3, createLineWithWinningItem.apply("Q"),
                4, createLineWithWinningItem.apply("A")
        );

        Map<Integer, ReelGameSessionWinningLineModel> oneWithWildsAndA = Map.of(
                1, createLineWithWinningItemAndWilds.apply("A")
        );

        Map<String, ReelGameSessionWinningScatterModel> noScatters = new HashMap<>();

        Map<String, ReelGameSessionWinningScatterModel> oneScatter = Map.of(
                "A", new ReelGameSessionWinningScatterModelImpl("A", new int[0][0], 1)
        );

        Map<String, ReelGameSessionWinningScatterModel> severalScatters = Map.of(
                "A", new ReelGameSessionWinningScatterModelImpl("A", new int[0][0], 1),
                "K", new ReelGameSessionWinningScatterModelImpl("K", new int[0][0], 1)
        );

        AtomicReference<Map<Integer, ReelGameSessionWinningLineModel>> linesForTest = new AtomicReference<>();
        AtomicReference<Map<String, ReelGameSessionWinningScatterModel>> scattersForTest = new AtomicReference<>();
        GameSession sessionMock = new ReelGameSession() {
            @Override
            public ReelGameSessionPaytableData getPaytable() {
                return null;
            }

            @Override
            public String[][] getReelsItems() {
                return new String[][]{
                        {"W", "A", "A", "A", "A"},
                        {"W", "A", "A", "A", "A"},
                        {"W", "A", "A", "A", "A"},
                };
            }

            @Override
            public Map<Integer, ReelGameSessionWinningLineModel> getWinningLines() {
                return linesForTest.get();
            }

            @Override
            public Map<String, ReelGameSessionWinningScatterModel> getWinningScatters() {
                return scattersForTest.get();
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
            public void play() throws UnableToPlayException {

            }
        };

        PlayUntilItemWinStrategy strategy;

        strategy = new PlayUntilItemWinStrategy("A");
        linesForTest.set(noLines);
        scattersForTest.set(noScatters);
        assertTrue(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        linesForTest.set(withDifferentItemsWithoutA);
        scattersForTest.set(noScatters);
        assertTrue(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        linesForTest.set(oneWithWinningItemA);
        scattersForTest.set(oneScatter);
        assertTrue(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        linesForTest.set(oneWithWinningItemA);
        scattersForTest.set(severalScatters);
        assertTrue(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        linesForTest.set(oneWithWinningItemA);
        scattersForTest.set(noScatters);
        assertFalse(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        linesForTest.set(threeWithWinningItemA);
        assertFalse(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        linesForTest.set(withDifferentItemsAndOneWithA);
        assertFalse(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        linesForTest.set(withDifferentItemsAndSeveralWithA);
        assertFalse(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        strategy.setMinLinesNumber(3);
        linesForTest.set(oneWithWinningItemA);
        assertTrue(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        strategy.setMinLinesNumber(3);
        linesForTest.set(threeWithWinningItemA);
        assertFalse(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        strategy.setOnlySameItemId(true);
        linesForTest.set(threeWithWinningItemA);
        assertFalse(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        strategy.setOnlySameItemId(true);
        linesForTest.set(withDifferentItemsAndOneWithA);
        assertTrue(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        strategy.setOnlySameItemId(true);
        linesForTest.set(withDifferentItemsAndSeveralWithA);
        assertTrue(strategy.canPlayNextGame(sessionMock));

        strategy = new PlayUntilItemWinStrategy("A");
        strategy.setAllowWilds(false, "W");
        linesForTest.set(oneWithWildsAndA);
        assertTrue(strategy.canPlayNextGame(sessionMock));
    }

}