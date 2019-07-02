package slotify4j.session.videogames.reelgames.reelscontroller;

public interface ReelGameSessionReelsController {

    String[][] getRandomItemsCombination();

    String[] getRandomReelItems(int reelId);

    String getRandomItem(int reelId);

}
