package slotify4j.session.videogames.reelgames;

import slotify4j.session.GameSessionConfig;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerConfig;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public interface ReelGameSessionConfig extends GameSessionConfig, ReelGameSessionReelsControllerConfig, ReelGameSessionWinCalculatorConfig {
    String DEFAULT_SCATTER_ITEM_ID = "S";
    int DEFAULT_MINIMUM_ITEMS_NUM_FOR_SCATTER_WIN = 3;

    String[] DEFAULT_AVAILABLE_ITEMS = new String[] {
            "A",
            "K",
            "Q",
            "J",
            "10",
            "9",
            "W",
            "S"
    };

    int DEFAULT_REELS_NUMBER = 5;
    int DEFAULT_REELS_ITEMS_NUMBER = 3;
    String DEFAULT_WILD_ITEM_ID = "W";

    static String[][] createReelsItemsSequences(int reelsNumber, String[] availableItems) {
        String[][] r = new String[reelsNumber][availableItems.length * availableItems.length];
        for (int i = 0; i < reelsNumber; i++) {
            String[] seq = Stream.of(availableItems).reduce("", (a, b) -> a.concat(String.join(",", availableItems) + ",")).split(",");
            Collections.shuffle(Arrays.asList(seq));
            r[i] = seq;
        }
        return r;
    }

    boolean isItemWild(String itemId);

    boolean isItemScatter(String itemId);

}
