package slotify4j.simulation;

import slotify4j.session.GameSession;

public class StopOnAnyWinPlayStrategy implements PlayStrategy {

    @Override
    public boolean canPlayNextGame(GameSession session) {
        return session.getWinningAmount() == 0;
    }

}
