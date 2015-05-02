package cz.mendelu.xkozak.pjj.project.sudoku.resolver;

/**
 * @author Tomas Kozak
 */
public class LastChecker {

    private final Structure structure;

    public LastChecker(Structure s) {
        this.structure = s;
    }

    public int check() {
        this.checkRows();
        this.checkColumns();
        this.checkSquares();
        return 1;
    }

    private void checkColumns() {
        for (int x = 0; x < 9; x++) { //rows
            for (int num = 1; num < 10; num++) { // number from 1 to 9                
                int found = 0;
                int last_x = 0;
                int last_y = 0;
                for (int y = 0; y < 9; y++) {
                    if (structure.contains(x, y, num)) {
                        found++;
                        last_x = x;
                        last_y = y;
                    }
                }
                

                if (found == 1 && this.structure.getNumber(last_x, last_y) == 0) {
                    this.structure.setNumber(last_x, last_y, num);
                    System.out.println("["+last_x+";"+last_y+"] - COLUMN");
                }
            }
        }
    }

    private void checkRows() {
        for (int y = 0; y < 9; y++) { //rows
            for (int num = 1; num < 10; num++) { // number from 1 to 9                
                int found = 0;
                int last_x = 0;
                int last_y = 0;
                for (int x = 0; x < 9; x++) {
                    if (structure.contains(x, y, num)) {
                        found++;
                        last_x = x;
                        last_y = y;
                    }
                }
                
                if (found == 1  && this.structure.getNumber(last_x, last_y) == 0) {
                    this.structure.setNumber(last_x, last_y, num);
                    System.out.println("["+last_x+";"+last_y+"] - ROW");
                }
            }
        }
    }

    private void checkSquares() {
        for (int cx = 1; cx < 9; cx = cx + 3) {
            for (int cy = 1; cy < 9; cy = cy + 3) {

                for (int num = 1; num < 10; num++) {
                    int found = 0;
                    int last_x = 0;
                    int last_y = 0;

                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            int mx = cx + i;
                            int my = cy + j;
                            if (structure.contains(mx, my, num)) {
                                found++;
                                last_x = mx;
                                last_y = my;
                            }

                        }
                    }

                    if (found == 1 && this.structure.getNumber(last_x, last_y) == 0) {
                        this.structure.setNumber(last_x, last_y, num);
                        System.out.println("["+last_x+";"+last_y+"] - SQUARE");
                    }
                }
            }
        }
    }

}
