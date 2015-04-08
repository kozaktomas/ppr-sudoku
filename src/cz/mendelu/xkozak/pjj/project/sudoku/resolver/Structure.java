package cz.mendelu.xkozak.pjj.project.sudoku.resolver;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tomas Kozak
 */
public class Structure {

    private HashSet[][] matrix = new HashSet[9][9];

    private Boolean[][] finish = new Boolean[9][9];

    public Structure() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.finish[i][j] = false;
                this.matrix[i][j] = new HashSet<>();
                for (int k = 1; k < 10; k++) {
                    this.matrix[i][j].add(k);
                }
            }
        }
    }

    public synchronized Set<Integer> getSet(int x, int y) {
        return this.matrix[x][y];
    }

    public synchronized int getNumber(int x, int y) {
        if (this.matrix[x][y].size() > 1) {
            return 0;
        }
        for (Object i : this.matrix[x][y]) {
            return (Integer) i; // first item from Set
        }
        return 0;
    }

    public synchronized void setNumber(int x, int y, Integer nubmer) {
        this.finish[x][y] = true;
        for (Integer i = 1; i < 10; i++) {
            if (this.matrix[x][y].contains(i) && !i.equals(nubmer)) {
                this.matrix[x][y].remove(i);
            }
        }
    }

    public Boolean isFinished(int x, int y) {
        return this.finish[x][y];
    }

    public synchronized boolean isComplete() {
        boolean b = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.getNumber(i, j) == 0) {
                    b = false;
                }
            }
        }
        return b;
    }
}
