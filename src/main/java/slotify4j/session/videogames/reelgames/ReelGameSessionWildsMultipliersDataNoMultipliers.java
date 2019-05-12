package slotify4j.session.videogames.reelgames;

public class ReelGameSessionWildsMultipliersDataNoMultipliers implements ReelGameSessionWildsMultipliersData {

    @Override
    public int getMultiplierValueForWildsNum(int wildNum) {
        return 1;
    }

}
