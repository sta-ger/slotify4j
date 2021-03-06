package slotify4j.session.videogames.reelgames.reelscontroller;

import java.util.Random;

public class ReelGameSessionReelsControllerImpl implements ReelGameSessionReelsController {
    private final int reelsNumber;
    private final int reelsItemsNumber;
    private final String[][] reelsSequences;

    public ReelGameSessionReelsControllerImpl(ReelGameSessionReelsControllerConfig config) {
        reelsNumber = config.getReelsNumber();
        reelsItemsNumber = config.getReelsItemsNumber();
        reelsSequences = config.getReelsItemsSequences();
    }

    @Override
    public String[][] getRandomItemsCombination() {
        String[][] rv = new String[reelsNumber][];
        for (int i = 0; i < reelsNumber; i++) {
            rv[i] = getRandomReelItems(i);
        }
        return rv;
    }

    @Override
    public String[] getRandomReelItems(int reelId) {
        String[] rv = new String[reelsItemsNumber];
        String[] sequence = reelsSequences[reelId];
        int placeOnSequence = new Random().nextInt(sequence.length);
        int j = 0;
        for (int i = placeOnSequence; i < placeOnSequence + reelsItemsNumber; i++) {
            String item;
            if (i > sequence.length - 1) {
                item = sequence[i - sequence.length];
            } else {
                item = sequence[i];
            }
            rv[j] = item;
            j++;
        }
        return rv;
    }

    @Override
    public String getRandomItem(int reelId) {
        String[] sequence = reelsSequences[reelId];
        return sequence[new Random().nextInt(sequence.length)];
    }

}
