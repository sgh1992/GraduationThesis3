package consume.analysize;

import consume.tool.ConsumeRecord;
import consume.tool.Tool;

import java.io.*;
import java.util.HashMap;

/**
 * Created by sghipr on 2016/12/6.
 * 计算消费数据的行为规律性.
 * 包括学生的早餐，中午餐，晚餐，洗澡，洗衣服的行为规律性.
 *
 */
public class ConsumeRegularity {

    private String consumeClean; //"D:/GraduationThesis/consume_clean_step1_sorted_removeDeuplicate.csv";

    private static String median = "media.csv"; //中间处理文件.

    private static String RegularityWithWork = "consumeRegularityWithWork_DaySingleCount.csv";

    private Cycle cycle;

    public ConsumeRegularity(String consumeClean,Cycle cycle){
        this.consumeClean = consumeClean;
        this.cycle = cycle;
    }

    public File regularity() throws IOException {
        File medianFile = regularityMedian();
        return regularityWithWork(medianFile);
    }

    public File regularityMedian() throws IOException {
        return regularityMedian(consumeClean,median);
    }


    public File regularityWithWork(File medianFile) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(medianFile)));
        File target = new File(medianFile.getParent(),RegularityWithWork);

        BufferedWriter writer = new BufferedWriter(new FileWriter(target));

        HashMap<String,String> works = Tool.getStudentWork();

        String str = null;

        while ((str = reader.readLine()) != null){
            String studentID = str.split(",", -1)[0];
            if(works.containsKey(studentID)){
                String work = works.get(studentID);
                writer.write(str + "," + work);
                writer.newLine();
            }
        }

        reader.close();
        writer.close();

        return target;

    }

    public File regularityMedian(String consumeClean,String median) throws IOException {

        File root = new File(consumeClean);
        File target = new File(root.getParent(),median);
        BufferedWriter writer = new BufferedWriter(new FileWriter(target));

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(root)));

        String str = null;
        ConsumeRecord parser = new ConsumeRecord();
        ConsumeRegularityRecord regularityRecord = null;

        //连续的相同的行为，则以第一次刷卡的时间为准.
        String beforeType = null;
        String beforeTime = null;

        while ((str = reader.readLine()) != null){
            if(parser.parser(str)){
                //inital ,read first
                if(regularityRecord == null)
                    regularityRecord = new ConsumeRegularityRecord(parser.getStudentID(),
                            String.valueOf(parser.getTerm()), cycle);

                if(!regularityRecord.exist(parser.getStudentID(), String.valueOf(parser.getTerm()))) {
                    write(regularityRecord, writer);

                    regularityRecord = new ConsumeRegularityRecord(parser.getStudentID(),
                            String.valueOf(parser.getTerm()),cycle);

                    beforeTime = null;
                    beforeType = null;
                }

                //连续的相同的行为，则以第一次刷卡的时间为准.
                if(beforeTime != null && parser.getType().equals(beforeType) && Tool.hoursBetween(beforeTime,parser.getTime()) < 1)
//                    regularityRecord.update(parser.getStudentID(),String.valueOf(parser.getTerm()),
//                        beforeTime,parser.getType()) //根据策略的不同而有所不同.
                      continue;
                else {
                    regularityRecord.update(parser.getStudentID(), String.valueOf(parser.getTerm()),
                            parser.getTime(), parser.getType());
                    beforeTime = parser.getTime();
                    beforeType = parser.getType();
                }
            }
        }
        write(regularityRecord,writer);
        writer.close();
        reader.close();
        return target;
    }

    public void write(ConsumeRegularityRecord regularityRecord,BufferedWriter writer) throws IOException {

        StringBuilder result = new StringBuilder();
        String studentID = regularityRecord.getStudentID();
        String term = regularityRecord.getTerm();

        for(String type : regularityRecord.getTypes()){
            result.append(studentID).append(",");
            result.append(term).append(",");
            result.append(type).append(",");
            int sum = 0 ;
            for(String interal : cycle.getInteral()){
                int count = regularityRecord.getInteralType(interal,type);
                sum += count;
                result.append(count).append(",");
            }
            if(sum > 0){
                writer.write(result.substring(0,result.length() - 1).toString());
                writer.newLine();
            }
            result.setLength(0);
        }
    }

    public static void main(String[] args) throws IOException {

        String consumeClean = "D:/GraduationThesis/consume_clean_step1_sorted_removeDeuplicate.csv";
        ConsumeRegularity regularity = new ConsumeRegularity(consumeClean,new DayCycle());
        //regularity.regularityMedian();
        //regularity.regularityWithWork(new File("D:\\GraduationThesis\\media.csv"));
        regularity.regularity();
    }
}
