package slotify4j.session.videogames.reelgames.reelscontroller;

public interface ReelGameSessionReelsControllerConfig {

    int getReelsNumber();
    void setReelsNumber(int reelsNumber);

    int getReelsItemsNumber();
    void setReelsItemsNumber(int reelsItemsNumber);

    String[] getAvailableItems();
    void setAvailableItems(String[] availableItems);

    String[][] getReelsItemsSequences();
    void setReelsItemsSequences(String[][] reelsItemsSequences);

}
