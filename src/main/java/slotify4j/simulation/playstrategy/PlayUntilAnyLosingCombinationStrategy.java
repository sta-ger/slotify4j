package slotify4j.simulation.playstrategy;

import slotify4j.session.GameSession;

public class PlayUntilAnyLosingCombinationStrategy implements PlayStrategy {

    @Override
    public boolean canPlayNextGame(GameSession session) {
        return session.getWinningAmount() != 0;
    }

}
