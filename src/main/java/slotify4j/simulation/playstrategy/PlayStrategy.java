package slotify4j.simulation.playstrategy;

import slotify4j.session.GameSession;

public interface PlayStrategy {

    boolean canPlayNextGame(GameSession session);

}
