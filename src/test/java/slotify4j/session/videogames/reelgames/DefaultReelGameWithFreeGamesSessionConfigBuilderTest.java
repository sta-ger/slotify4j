package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultReelGameWithFreeGamesSessionConfigBuilderTest {

    @Test
    public void withFreeGamesForScatters() {
        assertEquals(
                DefaultReelGameWithFreeGamesSessionConfig
                        .builder()
                        .withFreeGamesForScatters("S", 3, 10)
                        .build()
                        .getFreeGamesForScatters("S", 3),
                10
        );
    }

    @Test
    public void withAvailableBetsTest() {
        assertArrayEquals(DefaultReelGameWithFreeGamesSessionConfig.builder()
                        .withAvailableBets(new long[]{1, 2, 3, 4, 5})
                        .build()
                        .getAvailableBets(),
                new long[]{1, 2, 3, 4, 5}
        );
    }

    @Test
    public void withCreditsAmountTest() {
        assertEquals(DefaultReelGameWithFreeGamesSessionConfig.builder()
                        .withCreditsAmount(999)
                        .build()
                        .getCreditsAmount(),
                999
        );
    }

    @Test
    public void withBetTest() {
        assertEquals(DefaultReelGameWithFreeGamesSessionConfig.builder()
                        .withBet(100)
                        .build()
                        .getBet(),
                100
        );
    }

    @Test
    public void withReelsNumber() {
        assertEquals(DefaultReelGameWithFreeGamesSessionConfig.builder()
                        .withReelsNumber(6)
                        .build()
                        .getReelsNumber(),
                6
        );
    }

    @Test
    public void withReelsItemsNumber() {
        assertEquals(DefaultReelGameWithFreeGamesSessionConfig.builder()
                        .withReelsItemsNumber(4)
                        .build()
                        .getReelsItemsNumber(),
                4
        );
    }

    @Test
    public void withAvailableItems() {
        assertArrayEquals(DefaultReelGameWithFreeGamesSessionConfig.builder()
                        .withAvailableItems(new String[]{
                                "A",
                                "K",
                                "Q",
                                "J",
                        })
                        .build()
                        .getAvailableItems(),
                new String[]{
                        "A",
                        "K",
                        "Q",
                        "J",
                }
        );
    }

    @Test
    public void withReelsItemsSequences() {
        assertArrayEquals(DefaultReelGameWithFreeGamesSessionConfig.builder()
                        .withReelsItemsSequences(new String[][]{
                                {"A", "K", "Q", "J"},
                                {"A", "K", "Q", "J"},
                                {"A", "K", "Q", "J"},
                        })
                        .build()
                        .getReelsItemsSequences(),
                new String[][]{
                        {"A", "K", "Q", "J"},
                        {"A", "K", "Q", "J"},
                        {"A", "K", "Q", "J"},
                }
        );
    }

    @Test
    public void withPaytable() {
        ReelGameSessionPaytableDataImpl paytable = new ReelGameSessionPaytableDataImpl(
                ReelGameSessionTools.createDefaultPaytableMap(
                        new long[]{1, 2, 3},
                        new String[]{"A", "K", "Q", "W"},
                        5,
                        "W"
                ));
        assertEquals(DefaultReelGameWithFreeGamesSessionConfig.builder().withPaytable(paytable).build().getPaytable(), paytable);
    }

    @Test
    public void withWildItemId() {
        assertEquals(
                DefaultReelGameWithFreeGamesSessionConfig
                        .builder()
                        .withWildItemId("WILD")
                        .build()
                        .getWildItemId(),
                "WILD"
        );
    }

    @Test
    public void withScattersData() {
        ReelGameSessionScattersDataImpl[] sData = new ReelGameSessionScattersDataImpl[]{
                new ReelGameSessionScattersDataImpl("S", 3),
                new ReelGameSessionScattersDataImpl("SCTR", 5),
        };
        assertArrayEquals(
                DefaultReelGameWithFreeGamesSessionConfig
                        .builder()
                        .withScattersData(sData)
                        .build()
                        .getScattersData(),
                sData
        );
    }

    @Test
    public void withLinesDirections() {
        ReelGameSessionLinesDirectionDataImpl lData = new ReelGameSessionLinesDirectionDataImpl(
                ReelGameSessionTools.createDefaultLinesDirectionsMap(5, 3)
        );
        assertEquals(
                DefaultReelGameWithFreeGamesSessionConfig
                        .builder()
                        .withLinesDirections(lData)
                        .build()
                        .getLinesDirections(),
                lData
        );
    }

    @Test
    public void withWildsMultipliers() {
        ReelGameSessionWildsMultipliersDataPowerOfTwo m = new ReelGameSessionWildsMultipliersDataPowerOfTwo();
        assertEquals(
                DefaultReelGameWithFreeGamesSessionConfig
                        .builder()
                        .withWildsMultipliers(m)
                        .build()
                        .getWildsMultipliers(),
                m
        );
    }

    @Test
    public void buildTest() {
        assertDoesNotThrow(() -> DefaultReelGameWithFreeGamesSessionConfig.builder().build());
    }

}
