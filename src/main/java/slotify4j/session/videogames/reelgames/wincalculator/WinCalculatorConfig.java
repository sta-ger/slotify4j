package slotify4j.session.videogames.reelgames.wincalculator;

import slotify4j.session.videogames.reelgames.ReelGameSessionLinesDirectionData;
import slotify4j.session.videogames.reelgames.ReelGameSessionPaytableData;
import slotify4j.session.videogames.reelgames.ReelGameSessionScattersData;
import slotify4j.session.videogames.reelgames.ReelGameSessionWildsMultipliersData;

public interface WinCalculatorConfig {

    ReelGameSessionPaytableData getPaytable();
    void setPaytable(ReelGameSessionPaytableData paytable);

    String getWildItemId();
    void setWildItemId(String wildItemId);

    ReelGameSessionScattersData getScattersData();
    void setScattersData(ReelGameSessionScattersData scattersData);

    int getReelsNumber();
    int setReelsItemsNumber();

    ReelGameSessionLinesDirectionData getLinesDirections();
    void setLinesDirections(ReelGameSessionLinesDirectionData linesDirections);

    ReelGameSessionWildsMultipliersData getWildsMultipliers();
    void getWildsMultipliers(ReelGameSessionWildsMultipliersData wildsMultipliers);

}
