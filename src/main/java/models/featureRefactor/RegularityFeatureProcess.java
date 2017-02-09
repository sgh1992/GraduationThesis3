package models.featureRefactor;

import java.io.*;
import java.util.*;

/**
 * Created by sghipr on 2017/2/9.
 * 将之前所做的学生行为特征进一步加工处理.
 */
public class RegularityFeatureProcess {

    private String consumeRegularity; // "D:\\GraduationThesis\\consumeRegularityWithWork.csv";

    private String librarydoorRegularity; //D:\GraduationThesis\librarydoor_regularityWithWork_ThreeTimeSelf.csv

    private String refactorResult = "D:/GraduationThesis/consumeRegularity_refactor.csv";

    private Set<String> types;

    public List<String> get48TimeInterals(){

        List<String> timeInterals = new ArrayList<>();
        timeInterals.add("0000-0030");
        timeInterals.add("0030-0100");
        timeInterals.add("0100-0130");
        timeInterals.add("0130-0200");
        timeInterals.add("0200-0230");
        timeInterals.add("0230-0300");
        timeInterals.add("0300-0330");
        timeInterals.add("0330-0400");
        timeInterals.add("0400-0430");
        timeInterals.add("0430-0500");
        timeInterals.add("0500-0530");
        timeInterals.add("0530-0600");
        timeInterals.add("0600-0630");
        timeInterals.add("0630-0700");
        timeInterals.add("0700-0730");
        timeInterals.add("0730-0800");
        timeInterals.add("0800-0830");
        timeInterals.add("0830-0900");
        timeInterals.add("0900-0930");
        timeInterals.add("0930-1000");
        timeInterals.add("1000-1030");
        timeInterals.add("1030-1100");
        timeInterals.add("1100-1130");
        timeInterals.add("1130-1200");

        timeInterals.add("1200-1230");
        timeInterals.add("1230-1300");
        timeInterals.add("1300-1330");
        timeInterals.add("1330-1400");
        timeInterals.add("1400-1430");
        timeInterals.add("1430-1500");
        timeInterals.add("1500-1530");
        timeInterals.add("1530-1600");
        timeInterals.add("1600-1630");
        timeInterals.add("1630-1700");
        timeInterals.add("1700-1730");
        timeInterals.add("1730-1800");
        timeInterals.add("1800-1830");
        timeInterals.add("1830-1900");
        timeInterals.add("1900-1930");
        timeInterals.add("1930-2000");
        timeInterals.add("2000-2030");
        timeInterals.add("2030-2100");
        timeInterals.add("2100-2130");
        timeInterals.add("2130-2200");
        timeInterals.add("2200-2230");
        timeInterals.add("2230-2300");
        timeInterals.add("2300-2330");
        timeInterals.add("2330-0000");

        return timeInterals;
    }

    public Set<String> getBreakFast(){

        Set<String> timeInterals = new HashSet<>();

        timeInterals.add("0600-0630");
        timeInterals.add("0630-0700");
        timeInterals.add("0700-0730");
        timeInterals.add("0730-0800");
        timeInterals.add("0800-0830");
        timeInterals.add("0830-0900");
        timeInterals.add("0900-0930");
        timeInterals.add("0930-1000");

        return timeInterals;
    }

    public Set<String> getLunch(){

        Set<String> timeInterals = new HashSet<>();
        timeInterals.add("1030-1100");
        timeInterals.add("1100-1130");
        timeInterals.add("1130-1200");

        timeInterals.add("1200-1230");
        timeInterals.add("1230-1300");
        timeInterals.add("1300-1330");

        return timeInterals;
    }

    public Set<String> getDinner(){

        Set<String> timeInterals = new HashSet<>();
        timeInterals.add("1630-1700");
        timeInterals.add("1700-1730");
        timeInterals.add("1730-1800");
        timeInterals.add("1800-1830");
        timeInterals.add("1830-1900");

        return timeInterals;
    }

    public TreeMap<String,Integer> getMap(Set<String> set){

        TreeMap<String,Integer> map = new TreeMap<>();
        for(String value : set)
            map.put(value,0);
        return map;
    }


    public RegularityFeatureProcess(String consumeRegularity, String librarydoorRegularity){

        this.consumeRegularity = consumeRegularity;
        this.librarydoorRegularity = librarydoorRegularity;

        types = new HashSet<>();
        types.add("bathing");
        types.add("cleaning");
        types.add("water");
        types.add("mess");
        types.add("supermarket");
        types.add("borrowing");
        types.add("waterInLearning");

        types.add("librarydoor");
    }

    public File refactor() throws IOException {

        return refactor(librarydoorRegularity,consumeRegularity);
    }

    public File refactor(String librarydoorRegularity,String consumeRegularity) throws IOException {

        File result = new File(refactorResult);
        BufferedWriter writer = new BufferedWriter(new FileWriter(result));

        List<BufferedReader> readerList = new ArrayList<>();
        readerList.add(new BufferedReader(new InputStreamReader(new FileInputStream(consumeRegularity))));
        readerList.add(new BufferedReader(new InputStreamReader(new FileInputStream(librarydoorRegularity))));
        String str = null;
        Set<String> breakfast = getBreakFast();
        Set<String> lunch = getLunch();
        Set<String> dinner = getDinner();
        List<String> time48 = get48TimeInterals();

        for(BufferedReader reader : readerList){
            while ((str = reader.readLine()) != null){
                String[] args = str.split(",", -1);
                String studentID = args[0];
                String term = args[1];
                String type = args[2];

                StringBuilder record = new StringBuilder();
                record.append(studentID).append(",").append(term).append(",").append(type).append(",");

                List<Integer> values = new ArrayList<>();
                for(int i = 3; i < args.length - 1; ++i)
                    values.add(Integer.parseInt(args[i]));

                if(types.contains(type)){
                    if(type.equals("mess")){
                        record.append(timeCount(breakfast,values,time48)).append(",");
                        record.append(timeCount(lunch,values,time48)).append(",");
                        record.append(timeCount(dinner,values,time48));
                    }
                    else if(type.equals("librarydoor")){
                        int i = 0;
                        for(; i < values.size() - 1; ++i)
                            record.append(values.get(i)).append(",");
                        record.append(values.get(i));
                    }
                    else
                        record.append(sum(values));
                    writer.write(record.toString());
                    writer.newLine();
                }
            }
            reader.close();
        }
        writer.close();
        return result;
    }

    public double sum(List<Integer> values){

        double sums = 0.0;
        for(int value : values)
            sums += value;
        return sums;
    }

    public String timeCount(Set<String> timeInterals, List<Integer> values, List<String> timeLists){

        StringBuilder result = new StringBuilder();

        TreeMap<String,Integer> map = getMap(timeInterals);
        for(int i = 0; i < values.size(); ++i){
            if(map.containsKey(timeLists.get(i)))
                map.put(timeLists.get(i),values.get(i));
        }

        for(Map.Entry<String,Integer> entry : map.entrySet())
            result.append(entry.getValue()).append(",");

        return result.substring(0,result.length() - 1);
    }

    /**
     * 测试.
     * @param args
     */
    public static void main(String[] args) throws IOException {

        String librarydoorRegularity = "D:\\GraduationThesis\\librarydoor_regularityWithWork_ThreeTimeSelf.csv";
        String consumeRegularity = "D:\\GraduationThesis\\consumeRegularityWithWork.csv";

        RegularityFeatureProcess featureProcess = new RegularityFeatureProcess(consumeRegularity,librarydoorRegularity);
        File result = featureProcess.refactor();
    }

}
