package slotify4j.session.videogames.reelgames.reelscontroller;

public class ReelGameSessionReelsController implements ReelsController {
    private final int reelsNumber;
    private final int reelsItemsNumber;
    private final String[][] reelsSequences;

    ReelGameSessionReelsController(ReelsControllerConfig config) {
        reelsNumber = config.getReelsNumber();
        reelsItemsNumber = config.getReelsItemsNumber();
        reelsSequences = config.getReelsItemsSequences();
    }

    @Override
    public String[] getRandomItemsCombination() {
        return new String[0];
    }

    @Override
    public String[] getRandomReelItems(int reelId) {
        return new String[0];
    }

    @Override
    public String getRandomItem(int reelId) {
        return null;
    }

}
