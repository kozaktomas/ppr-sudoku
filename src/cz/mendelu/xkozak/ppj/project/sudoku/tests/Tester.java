package cz.mendelu.xkozak.ppj.project.sudoku.tests;

import cz.mendelu.xkozak.pjj.project.sudoku.IResolver;

/**
 *
 * @author Lukas Zachoval
 */
public class Tester {

    public static void main(String[] args) {
        int count = 1000;

        // THREADS
        long start_time = System.nanoTime();
        for (int i = 0; i < count; i++) {
            cz.mendelu.xkozak.pjj.project.sudoku.resolver.Resolver r = new cz.mendelu.xkozak.pjj.project.sudoku.resolver.Resolver();
            Tester.loadSudoku(r);
            r.solve();

        }
        long end_time = System.nanoTime();
        double avgT = ((end_time - start_time) / 1e6) / count;
        System.out.println("THREADS: " + avgT);
        
        // PROC
        start_time = System.nanoTime();
        for (int i = 0; i < count; i++) {
            cz.mendelu.xkozak.pjj.project.sudoku.Resolver r = new cz.mendelu.xkozak.pjj.project.sudoku.Resolver();
            Tester.loadSudoku(r);
            r.solve();

        }
        end_time = System.nanoTime();
        double avgP = ((end_time - start_time) / 1e6) / count;
        System.out.println("PROC: " + avgP);

        System.out.println("");
        System.out.println(Math.round((avgP / avgT * 100) - 100)  + " % faster");
    }

    public static void loadSudoku(IResolver r) {
        r.setNumber(0, 1, 8);
        r.setNumber(0, 7, 6);
        r.setNumber(1, 0, 9);
        r.setNumber(1, 8, 8);
        r.setNumber(2, 4, 3);
        r.setNumber(2, 5, 4);
        r.setNumber(2, 6, 9);
        r.setNumber(3, 0, 3);
        r.setNumber(3, 1, 6);
        r.setNumber(3, 2, 9);
        r.setNumber(3, 4, 8);
        r.setNumber(3, 7, 5);
        r.setNumber(3, 8, 4);
        r.setNumber(4, 0, 2);
        r.setNumber(4, 2, 8);
        r.setNumber(4, 3, 7);
        r.setNumber(5, 0, 1);
        r.setNumber(5, 1, 4);
        r.setNumber(5, 2, 7);
        r.setNumber(5, 4, 6);
        r.setNumber(5, 7, 8);
        r.setNumber(5, 8, 2);
        r.setNumber(6, 4, 2);
        r.setNumber(6, 5, 1);
        r.setNumber(6, 6, 5);
        r.setNumber(7, 0, 6);
        r.setNumber(7, 8, 1);
        r.setNumber(8, 1, 3);
        r.setNumber(8, 7, 4);
    }

}
