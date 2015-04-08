package cz.mendelu.xkozak.pjj.project.sudoku.resolver;

import cz.mendelu.xkozak.pjj.project.sudoku.IResolver;

/**
 *
 * @author Tomas Kozak
 */
public class Resolver implements IResolver {

    Structure structure = new Structure();

    public Resolver() {

    }

    @Override
    public void setSudoku(int[][] matrix) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (matrix[x][y] > 0 && matrix[x][y] < 10) {
                    setNumber(x, y, matrix[x][y]);
                }
            }
        }
    }

    @Override
    public Integer[][] getSudoku() {
        Integer[][] matrix = new Integer[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                matrix[x][y] = this.getNumber(x, y);
            }
        }
        return matrix;
    }

    @Override
    public boolean isFinishable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void solve() {
        int cores = Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < 90; i++) {
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    ResolverThread t = new ResolverThread(this.structure, x, y);
                    t.run();
                }
            }
        }
    }

    @Override
    public int getNumber(int x, int y) {
        return this.structure.getNumber(x, y);
    }

    @Override
    public void setNumber(int x, int y, int number) {
        this.structure.setNumber(x, y, number);
    }

}
