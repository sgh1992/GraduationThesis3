package consume.analysize;

import java.util.*;

/**
 * Created by sghipr on 2016/12/6.
 */
public class ConsumeRegularityRecord {

    private String studentID;

    private String term;

    private HashMap<String,ConsumeType> timeCounts;

    private Cycle cycle;

    public ConsumeRegularityRecord(String studentID, String term, Cycle cycle){

        this.studentID = studentID;
        this.term = term;
        timeCounts = new HashMap<>();
        this.cycle = cycle;
    }

    /**
     * 前提条件是studentID与term是相同的.
     * @param interal
     * @return
     */
    public boolean contains(String interal){
        return timeCounts.containsKey(interal) ? true : false;
    }

    public String getTerm(){
        return term;
    }

    public String getStudentID(){
        return studentID;
    }

    /**
     * 前提条件是StudentID 与 Term 是相同的.
     * @param interal
     * @param type
     * @return
     */
    public int getInteralType(String interal,String type){
        if(timeCounts.containsKey(interal))
            return timeCounts.get(interal).count(type);
        else
            return 0;
    }

    public Set<String> getTypes(){
        Set<String> types = new TreeSet<>();
        for(ConsumeType consumeType : timeCounts.values())
            for(String type : consumeType.typeSet())
                types.add(type);
        return types;
    }

    public boolean exist(String studentID,String term){
        if(this.studentID.equals(studentID) && this.term.equals(term))
            return true;
        else
            return false;
    }

    public boolean update(String studentID, String term, String time, String type){
        if(exist(studentID,term)){
            String interal = cycle.getInteral(time);
            if(interal != null){
                if(!timeCounts.containsKey(interal))
                    timeCounts.put(interal,new ConsumeType());
                timeCounts.get(interal).update(type);
                return true;
            }
            else
                return false;
        }
        return false;
    }
}
