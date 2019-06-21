package adventofcode.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 extends ExerciseBaseClass{ 
    
    int[][] table;
    final int SIZE = 1000;
    List<Square> squareList;
    
    public Day3(String fileName)
    {
        super(fileName);
        prepareElements();
    }  

    @Override
    protected final void firstTask() throws Exception{
        System.out.println(countClaims());
        // printTable();
    }

    @Override
    protected final void secondTask() throws Exception{
        for(Square square:squareList){
            if (checkUnclaimSquare(square)){
                System.out.println(square.number);
            }
        }
    }
    
    private void prepareElements()
    {        
        List<String> lineList = getLineListString();        
        table = new int[SIZE][SIZE];        
        squareList = new ArrayList<>();
        
        for(String line:lineList){
            squareList.add(getSquareObjectFromString(line));
        }
        
        for(Square square:squareList){
            drawSquare(square);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Task 1">
    
    private Square getSquareObjectFromString(String line)
    {
        Pattern pattern = Pattern.compile("#(?<number>\\d{1,4}) @ (?<y>\\d{1,3}),(?<x>\\d{1,3}): (?<width>\\d{1,2})x(?<height>\\d{1,2})");
        Matcher m = pattern.matcher(line);
        
        if (m.matches()) {
            return new Square(m.group("x"), m.group("y"), m.group("width"), m.group("height"), m.group("number"));
        }
        
        System.out.println(String.format("Pattern \"%s\" do not match line: \"%s\"!", pattern.pattern(), line));
        
        return null;
    }
    
    private void drawSquare(Square square){
        for (int i = square.x; i < square.x + square.height; i++) {
            for (int j = square.y; j < square.y + square.width; j++) {
                table[i][j]++;
            }
        }
    }
    
    private void printTable()
    {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    private int countClaims(){
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (table[i][j] > 1) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Task 2">
    
    private boolean checkUnclaimSquare(Square square){
        for (int i = square.x; i < square.x + square.height; i++) {
            for (int j = square.y; j < square.y + square.width; j++) {
                if (table[i][j] > 1) {
                    return false;
                }
            }
        }
        
        return true;
    }
    // </editor-fold>
    
}

class Square{

    public Square(int x, int y, int width, int height, int number) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.number = number;
    }
    
    public Square(String x, String y, String width, String height, String number) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.width = Integer.parseInt(width);
        this.height = Integer.parseInt(height);
        this.number = Integer.parseInt(number);

    }
        
    int x;
    int y;
    int width;
    int height;
    int number;

}
