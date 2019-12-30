package slotify4j.session.videogames.reelgames.wincalculator;

public class ReelGameWinCalculatorTools {

    public static isAllLinesHasSameItemId(lines: {}): boolean {
        let id: string;
        let r: boolean = true;
        for (let lineId in lines) {
            let line: WinningLineModel = lines[lineId];
            if (!id) {
                id = line.itemId;
                continue;
            }
            if (Object.keys(line).length > 1 && id !== line.itemId) {
                r = false;
                break;
            }
        }
        return r;
    }

    public static getLinesContainingItem(lines: {}, items: string[][], itemId: string): WinningLineModel[] {
        let r: WinningLineModel[] = [];
        for (let lineId in lines) {
            let line: WinningLineModel = lines[lineId];
            let lineItems: string[] = ReelGameSessionWinCalculator.getItemsForDirection(items, line.direction);
            for (let i: number = 0; i < lineItems.length; i++) {
                if (lineItems[i] === itemId) {
                    r.push(line);
                    break;
                }
            }
        }
        return r;
    }

    public static getLinesWithSymbol(lines: {}, symbolId: string): WinningLineModel[] {
        let r: WinningLineModel[] = [];
        for (let lineId in lines) {
            let line: WinningLineModel = lines[lineId];
            if (line.itemId === symbolId) {
                r.push(line);
            }
        }
        return r;
    }

    public static getLinesWithDifferentSymbols(lines: {}): WinningLineModel[] {
        let symbols: string[] = [];
        let r: WinningLineModel[] = [];
        for (let lineId in lines) {
            let line: WinningLineModel = lines[lineId];
            if (symbols.indexOf(line.itemId) < 0) {
                symbols.push(line.itemId);
                r.push(line);
            }
        }
        return r;
    }

}
