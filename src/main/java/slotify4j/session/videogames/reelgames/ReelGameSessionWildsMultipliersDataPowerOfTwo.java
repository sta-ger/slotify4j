package slotify4j.session.videogames.reelgames;

public class ReelGameSessionWildsMultipliersDataPowerOfTwo implements ReelGameSessionWildsMultipliersData {

    @Override
    public int getMultiplierValueForWildsNum(int wildNum) {
        return (int) Math.pow(2, wildNum);
    }

}
