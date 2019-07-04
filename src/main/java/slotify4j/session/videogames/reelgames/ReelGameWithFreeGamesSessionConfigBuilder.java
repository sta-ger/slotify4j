package slotify4j.session.videogames.reelgames;

public interface ReelGameWithFreeGamesSessionConfigBuilder extends ReelGameSessionConfigBuilder {

    ReelGameWithFreeGamesSessionConfigBuilder withFreeGamesForScatters(
            String itemId,
            int numberOfItems,
            int freeGamesNum
    );

    ReelGameWithFreeGamesSessionConfig build();

}
