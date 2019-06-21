package adventofcode.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2 extends ExerciseBaseClass{ 
    
    public Day2(String fileName)
    {
        super(fileName);
    }  

    @Override
    protected final void firstTask() throws Exception{
        List<String> lineList = getLineListString();
        Map<Integer,Integer> duplicatedNumbers = new HashMap<Integer,Integer>();
        
        for (String line : lineList) {
            List<Integer> repeatList = prepareListWithDuplications(line);
            
            for (Integer element : repeatList) {
                int value = duplicatedNumbers.containsKey(element) ? duplicatedNumbers.get(element) + 1 : 1;
                duplicatedNumbers.put(element, value);
            }
        }
        
        int result  = 1;
        for(Integer number : duplicatedNumbers.values())
        {
            result *= number;
        }
        System.out.println(result);
    }

    @Override
    protected final void secondTask() throws Exception{        
        List<String> lineList = getLineListString();
        
        boolean res = oldCompareLines("pbykrmjmizwhxlqnmasfgtycdv", "pbykrmjmizwhxlqnwasfgtycdv");
        
        int count = 0;
        for (int i = 0; i < lineList.size()-1; i++) {
            for (int j = i+1; j < lineList.size(); j++) {
                if(compareLines(lineList.get(i), lineList.get(j)))
                {
                    System.out.println("Found key:\n" + lineList.get(i) + " and " + lineList.get(j));
                    compareLines(lineList.get(i), lineList.get(j));
                    count++;
                }
            }
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Task 1">
    private List<Integer> prepareListWithDuplications(String line)
    {
        Collection<Integer> repeatsNumberList = countReapetingLetters(line).values();
        List<Integer> temp = new ArrayList<>();
        
        for(Integer value:repeatsNumberList)
        {
            if (!temp.contains(value) && value > 1) {
                temp.add(value);
            }
        }
        
        return temp;
    }
    
    private Map<Character,Integer> countReapetingLetters(String line)
    {
        Map<Character,Integer> tempList = new HashMap<Character,Integer>();
        
        for (int i = 0; i < line.length(); i++)
        {
            char letter = line.charAt(i);
            int value = tempList.containsKey(letter) ? tempList.get(letter) + 1 : 1;
            tempList.put(letter, value);
        }
        
        return  tempList;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Task 2">
    
    private boolean compareLines(String line1, String line2)
    {
        int count = 0;
        for (int i = 0; i < line1.length(); i++) {
            if(line1.charAt(i) != line2.charAt(i))
            {
                count ++;
            }
            
            if(count > 1)
            {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean oldCompareLines(String line1, String line2)
    {        
        Map<Character,Integer> lineCharDiagram1 = countReapetingLetters(line1);
        Map<Character,Integer> lineCharDiagram2 = countReapetingLetters(line2);
        
        int countOfCharDiff = 0;
        
        for (Map.Entry<Character, Integer> entry : lineCharDiagram1.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            
            if (lineCharDiagram2.containsKey(key)) {
                countOfCharDiff += Math.abs(value - lineCharDiagram2.get(key));
            }
            else{
                countOfCharDiff += value;
            }
            
            if (countOfCharDiff > 1) {
                return false;
            }
        } 
        
        return true;
    }
    
    // </editor-fold>
    
}
