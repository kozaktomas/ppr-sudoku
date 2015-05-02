package cz.mendelu.xkozak.pjj.project.sudoku.resolver;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tomas Kozak
 */
public class Structure {

    private HashSet[][] matrix = new HashSet[9][9];

    private Boolean[][] finish = new Boolean[9][9];

    private int finish_count = 0;

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

    /**
     *
     * @param x
     * @param y
     * @return
     */
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
        if (this.finish[x][y] == false) {
            this.finish[x][y] = true;
            this.matrix[x][y].clear();
            this.matrix[x][y].add(nubmer);
            this.finish_count++;
        }
    }

    public synchronized Boolean isFinished(int x, int y) {
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

    public synchronized boolean contains(int x, int y, int num) {
        return this.matrix[x][y].contains(num);
    }
    
    public boolean isDone(){
        return (this.finish_count == 81);
    }
}
