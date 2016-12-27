package consume.analysize;

import consume.tool.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sghipr on 2016/12/26.
 * 以周为周期，以天为单位进行分隔
 */
public class WeekCycle implements Cycle{


    private List<String> timeList;

    public WeekCycle(){
        timeList = divide();
    }

    @Override
    public List<String> getInteral() {
        return new ArrayList<>(timeList);
    }

    @Override
    public List<String> divide() {

        List<String> timeList = new ArrayList<>();
        timeList.add("Monday");
        timeList.add("Tuesday");
        timeList.add("Wednesday");
        timeList.add("Thursday");
        timeList.add("Friday");
        timeList.add("Saturday");
        timeList.add("Sunday");

        return timeList;
    }

    @Override
    public String getInteral(String time) {
        String[] args = time.split(" ", -1);
        if(args.length != 2)
            return null;
        return Tool.getDayOfWeek(args[0]);
    }

    public static void main(String[] args){

        WeekCycle weekCycle = new WeekCycle();
        System.out.println(weekCycle.getInteral("20161226 115100"));

    }
}
