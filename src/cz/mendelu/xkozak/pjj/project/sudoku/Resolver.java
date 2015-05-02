package cz.mendelu.xkozak.pjj.project.sudoku;

import java.util.HashSet;


/**
 * @author Tomas Kozak
 * @version 1
 * Class to resolving sudoku
 */
public final class Resolver implements IResolver{

    private HashSet[][] sudoku = new HashSet[9][9];

    private Boolean[][] done = new Boolean[9][9];

    private boolean state = false;
    /**
     * Initialization of sudoku resolver
     */
    public Resolver(){
        init();
    }

    
    /**
     * @return void
     */
    private void init(){
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 1; i <= 9; i++){
            set.add(i);
        }
        
        for (int i = 0; i <= 8; i++){
            for (int j = 0; j <= 8; j++){
                sudoku[i][j] = set;
                done[i][j] = false;
            }
        }    
    }
    
    /**
     * 
     * @param x vertical position
     * @param y horizontal position
     * @param number between 1-9
     */
    public void setNumber(int x, int y, int number){
    	if(number < 1 || number > 9) return;
    	
    	HashSet<Integer> set = new HashSet<Integer>();
    	set.add(number);
    	sudoku[x][y] = set;
    	done[x][y] = true;
    }
    
    private void removeNumber(int x, int y, int number){
        if(sudoku[x][y].size() > 1){
            HashSet<Integer> set = new HashSet<Integer>();
            for(Object o : sudoku[x][y]){
                if((Integer) o != number)
                    set.add((Integer) o);
            }
            sudoku[x][y] = set;
        }
        if(sudoku[x][y].size() == 1){
            done[x][y] = true;
        }
    }
    
    private void refreshField(int x,int y){
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 1; i <= 9; i++){
            set.add(i);
        }
        sudoku[x][y] = set;
        done[x][y] = false;
    }
    /**
     * 
     * @param x - vertical coordinate 
     * @param y - horizontal coordinate
     * @return	Value on this coordinate
     */
    public int getNumber(int x, int y){
        if(sudoku[x][y].size() == 1){
            HashSet hs = sudoku[x][y];
            for(Object i: hs){
                return (Integer)i;
            }
        } else {
            return 0;
        }
        
        return 0;
    }
    
    
// [CHECK] //
    private void checkColumn(int x, int y){
        int num = 0;
        for(int i = 0; i <= 8; i++){
            if(x != i){
                num = getNumber(i, y);
                if(num != 0){
                    removeNumber(x, y, num);
                }
            }
        }
    }
    
    private void checkRow(int x, int y){
        int num = 200;
        for(int i = 0; i <= 8; i++){
            if(y != i){
                num = getNumber(x, i);
                if(num != 0){
                    removeNumber(x, y, num);
                }
            }
        }
    }
    
    private void checkSubSquare(int x, int y){
        int sx = (x/3)+1; 
        int sy = (y/3)+1;
        sx = (3*sx)-2; 
        sy = (3*sy)-2;
        
        if(getNumber(sx, sy) != 0){
           removeNumber(x, y, getNumber(sx, sy)); 
        }
        if(getNumber(sx-1, sy-1) != 0){
           removeNumber(x, y, getNumber(sx-1, sy-1)); 
        }
        if(getNumber(sx+1, sy-1) != 0){
           removeNumber(x, y, getNumber(sx+1, sy-1)); 
        }
        if(getNumber(sx, sy-1) != 0){
           removeNumber(x, y, getNumber(sx, sy-1)); 
        }
        if(getNumber(sx+1, sy) != 0){
           removeNumber(x, y, getNumber(sx+1, sy)); 
        }
        if(getNumber(sx+1, sy+1) != 0){
           removeNumber(x, y, getNumber(sx+1, sy+1)); 
        }
        if(getNumber(sx, sy+1) != 0){
           removeNumber(x, y, getNumber(sx, sy+1)); 
        }
        if(getNumber(sx-1, sy+1) != 0){
           removeNumber(x, y, getNumber(sx-1, sy+1)); 
        }
        if(getNumber(sx-1, sy) != 0){
           removeNumber(x, y, getNumber(sx-1, sy)); 
        } 
        
    }
    
    
// [/CHECK] //
    
// [QUEST] //
    
    private boolean askNumber(int x, int y, int num){
        boolean b = false;
        if(sudoku[x][y].contains(num))
                b = true;
        return b;
    }
    
    private void questRow(int x, int y){
        boolean change = false;
        int num = 0;
        for(Object o : sudoku[x][y]){
            num = 0;
            num = (Integer) o;
            change = true;
            for(int i = 0 ; i <= 8 ; i++){
                if(y != i){
                    if(askNumber(x, i, num))
                        change = false;
                        //break;
                }
            }
            if(change && !done[x][y]){             
                setNumber(x, y, num);
            }
        }
    }
    
    private void questColumn(int x, int y){
        boolean change = false;
        int num = 0;
        for(Object o : sudoku[x][y]){
            num = 0;
            num = (Integer) o;
            change = true;
            for(int i = 0 ; i <= 8 ; i++){
                if(x != i){
                    if(askNumber(i, y, num))
                        change = false;
                        //break;
                }
            }
            if(change && !done[x][y]){
                setNumber(x, y, num);
            }
        }
    }
    
    private void questSubSquare(int x, int y){
        int poc;
        int num = -1;
        for(Object o : sudoku[x][y]){
            poc = 0;
            num = (Integer) o;
            
            int sx = (x/3)+1; 
            int sy = (y/3)+1;
            sx = (3*sx)-2; 
            sy = (3*sy)-2;
            if(askNumber(sx, sy, num))
                poc++;
            if(askNumber(sx-1, sy-1, num))
                poc++;
            if(askNumber(sx+1, sy-1, num))
                poc++;
            if(askNumber(sx, sy-1, num))
                poc++;
            if(askNumber(sx+1, sy, num))
                poc++;
            if(askNumber(sx+1, sy+1, num))
                poc++;
            if(askNumber(sx, sy+1, num))
                poc++;
            if(askNumber(sx-1, sy+1, num))
                poc++;
            if(askNumber(sx-1, sy, num))
                poc++;
            
            if(poc == 1 && num > 0){
                setNumber(x, y, num);
            }
        }
        

        
    }
   
// [/QUEST] //    
    /**
     * @return True, if sudoku is complete. False, if sudoku isn't complete.
     */
    private Boolean isComplete(){
        Boolean b = true;
        for (int i = 0; i <= 8; i++){
            for (int j = 0; j <= 8; j++){
                if(!done[i][j]){
                    return false;
                }
            }
        }
        if(b){
            for (int i = 0; i <= 8; i++){
                for (int j = 0; j <= 8; j++){
                    if(getNumber(i, j) > 0 && getNumber(i, j) < 10){
                        continue;
                    } else {
                        refreshField(i,j);
                        done[i][j] = false;
                        System.out.println("FILED");
                        return false;
                    }
                }
            } 
        }
        this.state = true;
        return true;
    }
    
    
    public void solve(){
        long start_time = System.nanoTime();
        int it = 0;
        while(!isComplete() && it < 100){
            for (int i = 0; i <= 8; i++){
                for (int j = 0; j <= 8; j++){
                    if(!done[i][j]){
                        this.checkColumn(i, j);
                        this.checkRow(i, j);
                        this.checkSubSquare(i,j);                      
                        
                        this.questColumn(i,j);
                        this.questSubSquare(i, j);
                        this.questRow(i,j);
                    }
                }
            } 
            it++;
        }
        long end_time = System.nanoTime();
        double difference = (end_time - start_time)/1e6;
        System.err.println(difference);
    }    
    /**
     * @return Solved sudoku in matrix of integers
     */
    public Integer[][] getSudoku(){
        Integer[][] pom = new Integer[9][9];
        for (int i = 0; i <= 8; i++){
            for (int j = 0; j <= 8; j++){
                for(Object o : sudoku[i][j]){
                    pom[i][j] = (Integer)o;
                }
            }
        }
        return pom; 
    }
    
    /**
     * @param Matrix of sudoku numbers. Values between 1-9, will be inserted into matrix.
     */
    public void setSudoku(int[][] x){
		for(int i = 0; i < 9 ; i++){
			for(int j = 0; j <= 8 ; j++){
				if(x[i][j] > 0 && x[i][j] < 10)
					 setNumber(i,j,x[i][j]);
			}
		}
    }
    
    /**
     * @return True, if sudoku is finishable. False, if sudoku is unfinishable.
     */
    public boolean isFinishable(){
        solve();
        if(this.state) return true;   
        return false;
    }

}   