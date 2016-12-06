package consume.analysize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by sghipr on 2016/12/6.
 * 以天为周期，以每半个小时为时间段.
 */
public class DayCycle implements Cycle{

    private List<String> timeList;

    public List<String> getInteral(){
        return timeList;
    }

    public DayCycle(){
        timeList = divide();
    }

    @Override
    public List<String> divide() {

        timeList = new ArrayList<>();
        timeList.add("0000");
        timeList.add("0030");
        timeList.add("0100");
        timeList.add("0130");
        timeList.add("0200");
        timeList.add("0230");
        timeList.add("0300");
        timeList.add("0330");
        timeList.add("0400");
        timeList.add("0430");
        timeList.add("0500");
        timeList.add("0530");
        timeList.add("0600");
        timeList.add("0630");
        timeList.add("0700");
        timeList.add("0730");
        timeList.add("0800");
        timeList.add("0830");
        timeList.add("0900");
        timeList.add("0930");
        timeList.add("1000");
        timeList.add("1030");
        timeList.add("1100");
        timeList.add("1130");
        timeList.add("1200");
        timeList.add("1230");
        timeList.add("1300");
        timeList.add("1330");
        timeList.add("1400");
        timeList.add("1430");
        timeList.add("1500");
        timeList.add("1530");
        timeList.add("1600");
        timeList.add("1630");
        timeList.add("1700");
        timeList.add("1730");
        timeList.add("1800");
        timeList.add("1830");
        timeList.add("1900");
        timeList.add("1930");
        timeList.add("2000");
        timeList.add("2030");
        timeList.add("2100");
        timeList.add("2130");
        timeList.add("2200");
        timeList.add("2230");
        timeList.add("2300");
        timeList.add("2330");

        return timeList;
    }

    @Override
    public String getInteral(String time) {
        String[] args = time.split(" ", -1);
        if(args.length == 2)
            timeList.add(args[1].trim());
        else
            return null;
        Collections.sort(timeList);
        int index = timeList.indexOf(args[1].trim());
        index = index % 48;
        timeList.remove(index);
        if(index == 0)
            return timeList.get(47) + "-" + timeList.get(0);
        else
            return timeList.get(index - 1) + "-" + timeList.get(index);
    }
}