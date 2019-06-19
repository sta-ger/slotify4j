package slotify4j.session.videogames.reelgames;

import slotify4j.session.GameSessionConfig;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerConfig;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public interface ReelGameSessionConfig extends GameSessionConfig, ReelGameSessionReelsControllerConfig, ReelGameSessionWinCalculatorConfig {

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
