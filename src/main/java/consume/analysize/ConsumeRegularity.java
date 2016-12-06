package consume.analysize;

import consume.tool.ConsumeRecord;

import java.io.*;
import java.util.List;

/**
 * Created by sghipr on 2016/12/6.
 * 计算消费数据的行为规律性.
 * 包括学生的早餐，中午餐，晚餐，洗澡，洗衣服的行为规律性.
 *
 */
public class ConsumeRegularity {

    private String consumeClean; //"D:/GraduationThesis/consume_clean_step1_sorted_removeDeuplicate.csv";

    private static String median = "media.csv"; //中间处理文件.

    private Cycle cycle;

    public ConsumeRegularity(String consumeClean,Cycle cycle){
        this.consumeClean = consumeClean;
        this.cycle = cycle;
    }

    public File regularityMedian(String consumeClean,String median) throws IOException {

        File root = new File(consumeClean);
        File target = new File(root.getParent(),median);
        BufferedWriter writer = new BufferedWriter(new FileWriter(target));

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(target)));

        String str = null;
        ConsumeRecord parser = new ConsumeRecord();
        ConsumeRegularityRecord regularityRecord = null;
        while ((str = reader.readLine()) != null){
            if(parser.parser(str)){
                //inital ,read first
                if(regularityRecord == null)
                    regularityRecord = new ConsumeRegularityRecord(parser.getStudentID(),
                            String.valueOf(parser.getTerm()),cycle);

                if(!regularityRecord.exist(parser.getStudentID(), String.valueOf(parser.getTerm())))
                    write(regularityRecord,writer);
                else
                    regularityRecord = new ConsumeRegularityRecord(parser.getStudentID(),
                            String.valueOf(parser.getTerm()),cycle);
                regularityRecord.update(parser.getStudentID(),String.valueOf(parser.getTerm()),
                        parser.getTime(),parser.getType());

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
            int sum = 0 ;
            for(String interal : cycle.getInteral()){
                int count = regularityRecord.getInteralType(interal,type);
                sum += count;
                result.append(sum).append(",");
            }
            if(sum > 0){
                writer.write(result.substring(0,result.length() - 1).toString());
                writer.newLine();
            }
            result.setLength(0);
        }
    }
}
