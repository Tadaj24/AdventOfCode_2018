package adventofcode.tasks;

import java.util.List;

public class Day6 extends ExerciseBaseClass{        
    
    private final int SIZE = 20;
    private int[][] table;
    List<Point> points;
    
    public Day6(String fileName)
    {
        super(fileName);
        prepareElements();
    }  

    @Override
    protected final void firstTask() throws Exception{
    }

    @Override
    protected final void secondTask() throws Exception{
    }
    
    private void prepareElements()
    {
        table = new int[SIZE][SIZE];
        
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="Task 1">
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Task 2">
    
    // </editor-fold>    
}

class Point{
    int x,y;
}