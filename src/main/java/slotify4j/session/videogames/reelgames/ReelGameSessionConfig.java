package slotify4j.session.videogames.reelgames;

import slotify4j.session.videogames.reelgames.reelscontroller.ReelsControllerConfig;

public interface ReelGameSessionConfig extends ReelsControllerConfig {

    boolean isItemWild(String itemId);

    boolean isItemScatter(String itemId);

}
