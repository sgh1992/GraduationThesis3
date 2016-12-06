package consume.analysize;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by sghipr on 6/12/16.
 */
public class ConsumeType {

    private HashMap<String,Integer> counts;

    public ConsumeType(){
        counts = new HashMap<>();
    }

    public boolean update(String type){
        int count = 1;
        if(counts.containsKey(type))
            count += counts.get(type);
        counts.put(type,count);
        return true;
    }

    public Set<String> typeSet(){
        return counts.keySet();
    }

    public int count(String type){
        if(counts.containsKey(type))
            return counts.get(type);
        else
            return 0;
    }
}
