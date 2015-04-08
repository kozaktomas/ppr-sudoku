package cz.mendelu.xkozak.pjj.project.sudoku.resolver;

import java.util.Set;

/**
 * @author Tomas Kozak
 */
public class ResolverThread extends Thread {

    Structure structure;

    Integer x;

    Integer y;

    Set<Integer> set;

    public ResolverThread(Structure structure, Integer x, Integer y) {
        this.structure = structure;
        this.x = x;
        this.y = y;
        this.set = structure.getSet(x, y);
    }

    @Override
    public void run() {
        this.doRow();
        this.doColumn();
        this.doSquare();
        this.checkLast();
    }

    private void doRow() {
        for (Integer i = 0; i < 9; i++) {
            Integer num = this.structure.getNumber(i, this.y);
            if (num > 0) {
                this.set.remove(num);
            }
        }
    }

    private void doColumn() {
        for (Integer i = 0; i < 9; i++) {
            Integer num = this.structure.getNumber(this.x, i);
            if (num > 0) {
                this.set.remove(num);
            }
        }
    }

    private void doSquare() {
        int cx = 3 * ((this.x / 3) + 1) - 2; // center x
        int cy = 3 * ((this.y / 3) + 1) - 2; // center y

        int mx;
        int my;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                mx = cx + i;
                my = cy + j;
                if (mx != this.x && my != this.y) {
                    Integer num = this.structure.getNumber(mx, my);
                    if (num > 0) {
                        structure.setNumber(this.x, this.y, num);
                    }
                }
            }
        }
    }

    private void checkLast() {
        Integer num = this.structure.getNumber(this.x, this.y);
        if (num > 0) {
            structure.setNumber(this.x, this.y, num);
        }
    }

}
