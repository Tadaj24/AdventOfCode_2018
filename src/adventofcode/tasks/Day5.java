package adventofcode.tasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day5 extends ExerciseBaseClass{ 
    
    Map<Integer, Guard> guardsHarmonogram = new HashMap();
    
    int debugg= 0;
    
    public Day5(String fileName)
    {
        super(fileName);
        prepareElements();
    }  

    @Override
    protected final void firstTask() throws Exception{
        guardsHarmonogram.forEach((k,v) -> { v.calculateSleepTime(); System.out.println(k); debugg = k;});
        findBestGuardTaskFirst();
    }

    @Override
    protected final void secondTask() throws Exception{
        findBestGuardTaskSecond();
    }
    
    private void prepareElements()
    {        
        List<String> lineList = getLineListString();
        java.util.Collections.sort(lineList);
        int guardNumber = 0;
        
        //interprete input lines
        for (String line : lineList) {
            if (line.contains("Guard")) {
                guardNumber = getGuardNumber(line);
                continue;
            }
            
            //Adding guard to list, if not exists
            if(!guardsHarmonogram.containsKey(guardNumber)){
                guardsHarmonogram.put(guardNumber, new Guard());
            }
            
            guardsHarmonogram.get(guardNumber).commandList.add(new Command(line));
        }
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="Task 1">
    
    private int getGuardNumber(String command){
        Pattern pattern = Pattern.compile("\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}\\] Guard #(?<number>\\d+) begins shift");
        Matcher m = pattern.matcher(command);
        
        if (m.matches()) {
            return Integer.parseInt(m.group("number"));
        }
        
        return 0;
    }
    
    private void findBestGuardTaskFirst(){
        Integer guardNumber = 0;
        
        for (Map.Entry<Integer, Guard> entry : guardsHarmonogram.entrySet()) {
            Integer number = entry.getKey();
            Guard guard = entry.getValue();
            
            if (guardNumber == 0) {
                guardNumber = number;
                continue;
            }
            
            if(guard.sleepTime > guardsHarmonogram.get(guardNumber).sleepTime){
                guardNumber = number;
            }
        }
        
        System.out.println("Best guard:" + guardNumber);
        System.out.println("Total sleep time:" + guardsHarmonogram.get(guardNumber).sleepTime);
        System.out.println("Result: " + calculateTask1Result(guardNumber) + "\n");
        
    }
    
    private void findBestGuardTaskSecond(){
        Integer guardNumber = 0;
        int maxValue = 0;
        
        for (Map.Entry<Integer, Guard> entry : guardsHarmonogram.entrySet()) {
            Integer number = entry.getKey();
            Guard guard = entry.getValue();
            
            int currentValue = IntStream.of(guard.harmonogram).max().getAsInt();
            
            if (guardNumber == 0) {
                guardNumber = number;
                currentValue = maxValue;
                continue;
            }
            
            if(currentValue > maxValue){                
                maxValue = currentValue;
                guardNumber = number;
            }
        }
        
        System.out.println("Best guard:" + guardNumber);
        System.out.println("Result: " + calculateTask2Result(guardNumber));
        
    }
    
    private int calculateTask1Result(int guardNumber){
        int bestMinute = guardsHarmonogram.get(guardNumber).getBestMinuteForTask();
        System.out.println("Best minute for guard " + guardNumber + ": " + bestMinute);
        return guardNumber * bestMinute;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Task 2">
    
    private int calculateTask2Result(int guardNumber){
        int bestMinute = guardsHarmonogram.get(guardNumber).getBestMinuteForTask();
        System.out.println("Best minute for guard " + guardNumber + ": " + bestMinute);
        return guardNumber * bestMinute;
    }
    
    // </editor-fold>
    
}

class Guard{
    int sleepTime = 0;    
    int[] harmonogram = new int[60];

    List<Command> commandList = new ArrayList();
    
    public void calculateSleepTime()
    {
        int i = 0;
        while (i < commandList.size()) {
            Command asleepCommand = commandList.get(i++);
            Command wakeCommand = commandList.get(i++);
            
            long diffMin = TimeUnit.MINUTES.convert(wakeCommand.date.getTime() - asleepCommand.date.getTime(), TimeUnit.MILLISECONDS);
            setSleepTime(asleepCommand.date, diffMin);
        }
        
        sleepTime = IntStream.of(harmonogram).sum();
        int dupa = 1;
    }
    
    private void setSleepTime(Date startDate, long diffMin)
    {
        Integer hour = startDate.getHours(), minute = startDate.getMinutes();
        
        for (int j = 0; j < diffMin; j++) {
            harmonogram[minute++]++;
            updateTime(hour, minute);
        }
    }
    
    private void updateTime(Integer hour, Integer minute){
        minute++;
        if(minute >= 60){            
            hour++;
            minute = 0;
        }
        
        if(hour >= 24)
            hour = 0;
    }
    
    private int getIndexOfMaxValueInTable(int[] table){        
        int index = 0;
        int maxValue = 0;
        for (int i = 0; i < table.length; i++) {            
            if(table[i] > maxValue){
                index = i;
                maxValue = table[i];
            }
        }
        
        return index;
    }
    
    public int getBestMinuteForTask(){
        return getIndexOfMaxValueInTable(harmonogram);
    }
}

class Command{
    Date date = new Date();
    CommandType commandType;  
    private final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public Command(String content) {
        prepareCommand(content);
    }
    
    private void prepareCommand(String content){
        Pattern pattern = Pattern.compile("\\[(?<time>\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2})\\] (?<content>[\\w\\s]+)");
        Matcher m = pattern.matcher(content);
        
        if (m.matches()) {
            commandType = getCommandType(m.group("content"));
            date = calculateDate(m.group("time"));
        }
    }
    
    private Date calculateDate(String dateString){
        Date data = new Date();
        try {
            data = dataFormat.parse(dateString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return data;
    }
    
    private CommandType getCommandType(String command){
        if (command.contains("falls")) {
            return CommandType.asleep;            
        }
        if (command.contains("wakes")) {
            return CommandType.wakes;            
        }
        
        return null;
    }
}

enum CommandType{
    asleep,
    wakes
}
