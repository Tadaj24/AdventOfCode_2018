package adventofcode.tasks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class ExerciseBaseClass {
    private String pathToFile;
    private final String PATH_PREFIX = "C:\\Szkolenie Java\\AdventOfCode\\Files";    
    public void executeExercise(){
        try {            
            firstTask();
            secondTask();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ExerciseBaseClass(String fileName) {
        this.pathToFile = new File(PATH_PREFIX, String.join(".",fileName, "txt")).toString();
        System.out.println("Used file:" + pathToFile);
    }
       
    protected BufferedReader readFile()
    {
        BufferedReader br = null;
        try
        {            
            br = new BufferedReader(new FileReader(pathToFile));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File " + pathToFile + " has been not found!");
            System.exit(0);
        }
        
        return br;
    }
    
    protected void setPathToFile(String pathToFile)
    {
        if(pathToFile.isEmpty())
        {
            System.out.println("Path to file could not be empty!");
        }
        
        this.pathToFile = pathToFile;
    }
    
    protected List<String> getLineListString()
    {        
        BufferedReader br = readFile();
        List<String> temp = new ArrayList<>();
        
        try 
        {   
            String line = br.readLine();
            while(line !=null)
            {
                temp.add(line);
                line = br.readLine();
            }
        }
        catch (IOException e)
        {
            System.out.println("Problem with read lines");            
            System.exit(0);
        }
        return temp;
    }
    
    protected List<Integer> getLineListInt()
    {        
        List<String> lineList = getLineListString();
        List<Integer> temp = new ArrayList<>();
        
        try 
        {   
            for (String line:lineList) {
                temp.add(Integer.parseInt(line));
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("Problem with parse value to int \n" + e.getMessage());            
            System.exit(0);
        }
        return temp;
    }
    
    protected abstract void firstTask() throws Exception;
    protected abstract void secondTask() throws Exception;
}
