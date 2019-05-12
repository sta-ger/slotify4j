package slotify4j.session.videogames.reelgames;

import slotify4j.session.GameSessionConfig;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerConfig;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorConfig;

public interface ReelGameSessionConfig extends GameSessionConfig, ReelGameSessionReelsControllerConfig, ReelGameSessionWinCalculatorConfig {

    boolean isItemWild(String itemId);

    boolean isItemScatter(String itemId);

}
