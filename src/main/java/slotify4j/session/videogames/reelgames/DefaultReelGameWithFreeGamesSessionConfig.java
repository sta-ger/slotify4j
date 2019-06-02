package slotify4j.session.videogames.reelgames;

public class DefaultReelGameWithFreeGamesSessionConfig implements ReelGameWithFreeGamesSessionConfig {

    @Override
    public int getFreeGamesForScatters(String itemId, int numberOfItems) {
        return itemId.equals("S") ? 10 : 0;
    }

}
