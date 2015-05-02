package cz.mendelu.xkozak.pjj.project.sudoku.resolver;

import cz.mendelu.xkozak.pjj.project.sudoku.IResolver;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomas Kozak
 */
public class Resolver implements IResolver {

    Structure structure = new Structure();

    LastChecker checker;

    public Resolver() {
        this.checker = new LastChecker(this.structure);
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
        return true;
    }

    @Override
    public void solve() {
        long start_time = System.nanoTime();
        
        Vector threads = new Vector();
        boolean resolver = true;
        int iterator = 0;
        
        while (resolver && iterator < 500) {
            iterator++;
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if (!this.structure.isFinished(x, y)) {
                        ResolverThread t = new ResolverThread(this.structure, x, y);
                        t.run();
                        threads.addElement(t);
                    }
                }
            }

            for (Object x : threads) {
                try {
                    ResolverThread th = (ResolverThread) x;
                    th.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Resolver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            threads.clear();
            
            resolver = !this.structure.isDone();
        }
        System.out.println("COUNT OF ITERATIONS: " + iterator);
        long end_time = System.nanoTime();
        double difference = (end_time - start_time)/1e6;
        System.err.println("THREADS: " + difference); 
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
