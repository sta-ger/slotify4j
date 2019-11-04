package slotify4j.simulation;

import slotify4j.session.UnableToPlayException;

public interface GameSessionSimulationStrategy {

    void run() throws UnableToPlayException;

}
