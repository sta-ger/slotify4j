package slotify4j.simulation;

import slotify4j.session.UnableToPlayException;

public interface GameSessionSimulationPlayStrategy {

    void run() throws UnableToPlayException;

}
