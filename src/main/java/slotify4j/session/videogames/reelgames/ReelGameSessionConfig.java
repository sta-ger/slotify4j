package slotify4j.session.videogames.reelgames;

import slotify4j.session.GameSessionConfig;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelsControllerConfig;
import slotify4j.session.videogames.reelgames.wincalculator.WinCalculatorConfig;

public interface ReelGameSessionConfig extends GameSessionConfig, ReelsControllerConfig, WinCalculatorConfig {

    boolean isItemWild(String itemId);

    boolean isItemScatter(String itemId);

}
