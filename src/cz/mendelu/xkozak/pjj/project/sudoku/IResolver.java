package cz.mendelu.xkozak.pjj.project.sudoku;

/**
 * @author Tomas Kozak
 */
public interface IResolver {

    public void setSudoku(int[][] x);    
    public Integer[][] getSudoku();
    
    public boolean isFinishable();
    public void solve();
    
    public int getNumber(int x, int y);
    public void setNumber(int x, int y, int number);
}
