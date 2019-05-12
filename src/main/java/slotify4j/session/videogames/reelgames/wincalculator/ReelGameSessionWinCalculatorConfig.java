package slotify4j.session.videogames.reelgames.wincalculator;

import slotify4j.session.videogames.reelgames.ReelGameSessionLinesDirectionData;
import slotify4j.session.videogames.reelgames.ReelGameSessionPaytableData;
import slotify4j.session.videogames.reelgames.ReelGameSessionScatterData;
import slotify4j.session.videogames.reelgames.ReelGameSessionWildsMultipliersData;

public interface ReelGameSessionWinCalculatorConfig {

    ReelGameSessionPaytableData getPaytable();
    void setPaytable(ReelGameSessionPaytableData paytable);

    String getWildItemId();
    void setWildItemId(String wildItemId);

    ReelGameSessionScatterData[] getScattersData();
    void setScattersData(ReelGameSessionScatterData[] scattersData);

    int getReelsNumber();
    void setReelsNumber(int reelsNumber);

    int getReelsItemsNumber();
    void setReelsItemsNumber(int reelsItemsNumber);

    ReelGameSessionLinesDirectionData getLinesDirections();
    void setLinesDirections(ReelGameSessionLinesDirectionData linesDirections);

    ReelGameSessionWildsMultipliersData getWildsMultipliers();
    void setWildsMultipliers(ReelGameSessionWildsMultipliersData wildsMultipliers);

}
