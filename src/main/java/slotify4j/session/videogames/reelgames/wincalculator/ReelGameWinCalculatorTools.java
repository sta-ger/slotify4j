package slotify4j.session.videogames.reelgames.wincalculator;

import slotify4j.session.videogames.reelgames.ReelGameSessionTools;
import slotify4j.session.videogames.reelgames.ReelGameSessionWinningLineModel;

import java.util.ArrayList;

public class ReelGameWinCalculatorTools {

    public static boolean isAllLinesHasSameItemId(ReelGameSessionWinningLineModel[] lines) {
        String id = null;
        boolean r = true;
        for (ReelGameSessionWinningLineModel line : lines) {
            if (id == null) {
                id = line.getItemId();
                continue;
            }
            if (lines.length > 1 && !id.equals(line.getItemId())) {
                r = false;
                break;
            }
        }
        return r;
    }

    public static ReelGameSessionWinningLineModel[] getLinesWithItem(
            ReelGameSessionWinningLineModel[] lines,
            String[][] reelsItems,
            String itemId
    ) {
        ArrayList<ReelGameSessionWinningLineModel> r = new ArrayList<>();
        for (ReelGameSessionWinningLineModel line : lines) {
            String[] lineItems = ReelGameSessionTools.getItemsForDirection(reelsItems, line.getDirection());
            for (String lineItem : lineItems) {
                if (lineItem.equals(itemId)) {
                    r.add(line);
                    break;
                }
            }
        }
        return r.toArray(new ReelGameSessionWinningLineModel[0]);
    }

    public static ReelGameSessionWinningLineModel[] getLinesWithWinningItem(
            ReelGameSessionWinningLineModel[] lines,
            String itemId
    ) {
        ArrayList<ReelGameSessionWinningLineModel> r = new ArrayList<>();
        for (ReelGameSessionWinningLineModel line : lines) {
            if (line.getItemId().equals(itemId)) {
                r.add(line);
            }
        }
        return r.toArray(new ReelGameSessionWinningLineModel[0]);
    }

    public static ReelGameSessionWinningLineModel[] getLinesWithDifferentWinningItems(
            ReelGameSessionWinningLineModel[] lines
    ) {
        ArrayList<String> items = new ArrayList<>();
        ArrayList<ReelGameSessionWinningLineModel> r = new ArrayList<>();
        for (ReelGameSessionWinningLineModel line : lines) {
            if (items.indexOf(line.getItemId()) < 0) {
                items.add(line.getItemId());
                r.add(line);
            }
        }
        return r.size() > 1 ?
                r.toArray(new ReelGameSessionWinningLineModel[0]) : new ReelGameSessionWinningLineModel[0];
    }

}
