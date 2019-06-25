package adventofcode.tasks;

import java.io.BufferedReader;

public class Day5 extends ExerciseBaseClass{ 
        
    String fileContent;
    public Day5(String fileName)
    {
        super(fileName);
        prepareElements();
    }  

    @Override
    protected final void firstTask() throws Exception{
        reactPolymers(fileContent);
    }

    @Override
    protected final void secondTask() throws Exception{
        removeLetters(fileContent);
    }
    
    private void prepareElements()
    {   
        try {            
            BufferedReader br = readFile();
        
            fileContent = br.readLine();
            System.out.println(fileContent.length());
        } catch (Exception e) {
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Task 1">
    
    private boolean isOpositePolarity(char firstLetter, char secondLetter){
        if (Character.toUpperCase(firstLetter) == Character.toUpperCase(secondLetter)) {
            if(Character.isUpperCase(firstLetter) ^ Character.isUpperCase(secondLetter)){
                return true;
            }
        }        
        return false;
    }
    
    private String reactPolymers(String content){
        boolean end = false;
        while (!end) {
            end = true;
            for (int i = 0; i < content.length() - 1; i++) {
                char firstLetter = content.charAt(i);
                char secondLetter = content.charAt(i+1);
                if(isOpositePolarity(firstLetter, secondLetter)){
                    String toReplace = "" + firstLetter + secondLetter;
                    content = content.replaceFirst(toReplace, "");
                    end=false;
                    break;
                }    
            }
        }
        
        System.out.println(content);
        System.out.println(content.length());
        return content;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Task 2">
    
    private void removeLetters(String content){
        int shortestPolymerLength = 50000;
        for (int i = 65; i <= 90; i++) {            
            String tempContent = content;
            String lettersToRemove = String.format("[%s%s]", Character.toLowerCase((char)i), Character.toUpperCase((char)i));
            tempContent = tempContent.replaceAll(lettersToRemove, "");
            
            int contentAfterReactLength = reactPolymers(tempContent).length();
            shortestPolymerLength = shortestPolymerLength > contentAfterReactLength ? contentAfterReactLength : shortestPolymerLength;
            System.out.println(lettersToRemove + " : " + contentAfterReactLength);
        }
        
        System.out.println("\n" + shortestPolymerLength);
    }
    
    // </editor-fold>
    
}