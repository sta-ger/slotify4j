package slotify4j.session.videogames.reelgames;

public interface ReelGameWithFreeGamesSessionConfig extends ReelGameSessionConfig {

    int getFreeGamesForScatters(String itemId, int numberOfItems);

}
