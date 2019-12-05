package slotify4j.simulation;

import slotify4j.session.GameSession;

import java.util.Arrays;

public class GameSessionSimulationConstantChangeBetStrategy implements GameSessionSimulationChangeBetStrategy {

    @Override
    public void setBetForPlay(GameSession session) {
        long[] bets = session.getAvailableBets();
        if (session.canPlayNextGame()) {
            Arrays.sort(bets);
            session.setBet(bets[0]);
        }
    }

}
