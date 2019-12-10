package slotify4j.simulation;

public class StopOnAnyWinPlayStrategy implements PlayStrategy {

    @Override
    public boolean canPlayNextGame() {
        return false;
    }

}
