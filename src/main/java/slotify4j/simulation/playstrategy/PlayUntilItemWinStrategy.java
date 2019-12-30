package slotify4j.simulation.playstrategy;

import slotify4j.session.GameSession;

public class PlayUntilItemWinStrategy implements PlayStrategy {

    public PlayUntilItemWinStrategy() {

    }

    @Override
    public boolean canPlayNextGame(GameSession session) {
        return false;
    }

}
