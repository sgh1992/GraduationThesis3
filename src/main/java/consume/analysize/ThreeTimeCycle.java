package consume.analysize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sghipr on 2017/1/3.
 */
public class ThreeTimeCycle implements Cycle{

    private List<String> timeList;

    public List<String> getInteral(){

        List<String> interalList = new ArrayList<>();
        for(int i = 0; i < timeList.size() - 1; ++i){
            String interal = timeList.get(i % timeList.size()) + "-" + timeList.get((i + 1) % timeList.size());
            interalList.add(interal);
        }
        return interalList;
    }

    public ThreeTimeCycle(){
        timeList = divide();
    }

    @Override
    public List<String> divide() {

        timeList = new ArrayList<>();
        timeList.add("0700");
        timeList.add("1200");
        timeList.add("1430");
        timeList.add("1800");
        timeList.add("2200");

        return timeList;
    }

    @Override
    public String getInteral(String time) {
        String[] args = time.split(" ", -1);
        int size = timeList.size();
        if(args.length == 2)
            timeList.add(args[1].trim());
        else
            return null;
        Collections.sort(timeList);
        int index = timeList.indexOf(args[1].trim());
        timeList.remove(index);

        index = index % size;
        if(index == 0)
            return null;
        else
            return timeList.get(index - 1) + "-" + timeList.get(index);
    }

    public static void main(String[] args) {
        ThreeTimeCycle dayCycle = new ThreeTimeCycle();
        System.err.println(dayCycle.getInteral().size());
        for (String string : dayCycle.getInteral())
            System.err.println(string);

        System.err.println("outPut:" + dayCycle.getInteral("201011 070825"));
    }
}
