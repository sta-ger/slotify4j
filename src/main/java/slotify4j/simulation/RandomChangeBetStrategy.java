package slotify4j.simulation;

import slotify4j.session.GameSession;

import java.util.Random;

public class RandomChangeBetStrategy implements ChangeBetStrategy {

    @Override
    public void setBetForPlay(GameSession session) {
        long[] bets = session.getAvailableBets();
        long bet = bets[new Random().nextInt(bets.length)];
        session.setBet(bet);
    }

}
