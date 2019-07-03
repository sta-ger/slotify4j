package slotify4j.session.videogames.reelgames;

public interface ReelGameWithFreeGamesSessionConfigBuilder {

    ReelGameWithFreeGamesSessionConfigBuilder withFreeGamesForScatters(
            String itemId,
            int numberOfItems,
            int freeGamesNum
    );

    ReelGameWithFreeGamesSessionConfig build();

}
