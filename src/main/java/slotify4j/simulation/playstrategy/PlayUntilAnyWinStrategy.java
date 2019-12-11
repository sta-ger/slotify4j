package slotify4j.simulation.playstrategy;

import slotify4j.session.GameSession;

public class PlayUntilAnyWinStrategy implements PlayStrategy {

    @Override
    public boolean canPlayNextGame(GameSession session) {
        return session.getWinningAmount() == 0;
    }

}
