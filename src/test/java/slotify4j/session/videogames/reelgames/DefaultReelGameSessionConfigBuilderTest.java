package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultReelGameSessionConfigBuilderTest {

    public static boolean testBuilderMethods(ReelGameSessionConfigBuilder builder) {
        assertEquals(builder
                        .withReelsNumber(6)
                        .build()
                        .getReelsNumber(),
                6
        );

        assertEquals(builder
                        .withReelsItemsNumber(4)
                        .build()
                        .getReelsItemsNumber(),
                4
        );

        assertArrayEquals(builder
                        .withAvailableItems(new String[]{
                                        "A",
                                        "K",
                                        "Q",
                                        "J",
                                }
                        )
                        .build()
                        .getAvailableItems(),
                new String[]{
                        "A",
                        "K",
                        "Q",
                        "J",
                }
        );

        assertArrayEquals(builder
                        .withReelsItemsSequences(new String[][]{
                                        {"A", "K", "Q", "J"},
                                        {"A", "K", "Q", "J"},
                                        {"A", "K", "Q", "J"},
                                }
                        )
                        .build()
                        .getReelsItemsSequences(),
                new String[][]{
                        {"A", "K", "Q", "J"},
                        {"A", "K", "Q", "J"},
                        {"A", "K", "Q", "J"},
                }

        );

        ReelGameSessionPaytableDataImpl paytable = new ReelGameSessionPaytableDataImpl(
                ReelGameSessionTools.createDefaultPaytableMap(
                        new long[]{1, 2, 3},
                        new String[]{"A", "K", "Q", "W"},
                        5,
                        "W"
                ));
        assertEquals(builder
                        .withPaytable(paytable)
                        .build()
                        .getPaytable(),
                paytable
        );

        assertEquals(builder
                        .withWildItemId("WILD")
                        .build()
                        .getWildItemId(),
                "WILD"
        );

        ReelGameSessionScattersDataImpl[] sData = new ReelGameSessionScattersDataImpl[]{
                new ReelGameSessionScattersDataImpl("S", 3),
                new ReelGameSessionScattersDataImpl("SCTR", 5),
        };
        assertArrayEquals(
                builder
                        .withScattersData(sData)
                        .build()
                        .getScattersData(),
                sData
        );


        ReelGameSessionLinesDirectionDataImpl lData = new ReelGameSessionLinesDirectionDataImpl(
                ReelGameSessionTools.createDefaultLinesDirectionsMap(5, 3)
        );
        assertEquals(builder
                        .withLinesDirections(lData)
                        .build()
                        .getLinesDirections(),
                lData
        );

        ReelGameSessionWildsMultipliersDataPowerOfTwo m = new ReelGameSessionWildsMultipliersDataPowerOfTwo();
        assertEquals(builder
                        .withWildsMultipliers(m)
                        .build()
                        .getWildsMultipliers(),
                m
        );
        return true;
    }

    @Test
    public void testDefaultReelGameSessionConfigBuilder() {
        assertTrue(testBuilderMethods(DefaultReelGameSessionConfig.builder()));
    }

}
