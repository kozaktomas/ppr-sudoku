package cz.mendelu.xkozak.pjj.project.sudoku.resolver;

/**
 * @author Tomas Kozak
 */
public class Structure {

    private Integer[][] matrix = new Integer[9][9];

    public Structure() {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                this.matrix[i][j] = 0;
            }
        }
    }

}
