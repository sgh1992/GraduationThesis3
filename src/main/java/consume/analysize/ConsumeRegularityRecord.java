package consume.analysize;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sghipr on 2016/12/6.
 */
public class ConsumeRegularityRecord {

    private String studentID;

    private String term;

    private HashMap<String,Integer> timeCountMap;

    private Cycle cycle;

    public ConsumeRegularityRecord(String studentID, String term, List<String> timeList,Cycle cycle){

        this.studentID = studentID;
        this.term = term;

        timeCountMap = new HashMap<>();
        for(String time : timeList)
            timeCountMap.put(time,0);

        this.cycle = cycle;
    }

    public boolean exist(String studentID,String term){
        if(this.studentID.equals(studentID) && this.term.equals(term))
            return true;
        else
            return false;
    }

    public boolean update(String studentID, String term, String time, String type){
        if(exist(studentID,term)){

        }
    }
}
