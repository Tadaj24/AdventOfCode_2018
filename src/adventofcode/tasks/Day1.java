package adventofcode.tasks;

import java.util.ArrayList;
import java.util.List;

public class Day1 extends ExerciseBaseClass{ 
    
    public Day1(String fileName)
    {
        super(fileName);
    }  

    @Override
    protected final void firstTask() throws Exception{
        List<Integer> integerList = getLineListInt();        
        int sum = integerList.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);
    }

    @Override
    protected final void secondTask() throws Exception{    
        int value = 0;
        List<Integer> integerList = getLineListInt();
        List<Integer> alreadyOccurValues = new ArrayList<>(0);
        
        int i = 0;
        while(true)
        {
            value += integerList.get(i++);
            //System.out.println(value);
            if(alreadyOccurValues.contains(value))
            {
                System.out.println("Reapeated value: " + value);
                break;
            }
            
            alreadyOccurValues.add(value);
            
            i = i < integerList.size() ? i : 0;
        }
    }
}
