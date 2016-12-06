package consume.analysize;

import consume.tool.ConsumeRecord;

import java.io.*;

/**
 * Created by sghipr on 2016/12/6.
 * 计算消费数据的行为规律性.
 * 包括学生的早餐，中午餐，晚餐，洗澡，洗衣服的行为规律性.
 *
 */
public class ConsumeRegularity {

    private String consumeClean; //"D:/GraduationThesis/consume_clean_step1_sorted_removeDeuplicate.csv";

    private static String median = "media.csv"; //中间处理文件.

    public ConsumeRegularity(String consumeClean){
        this.consumeClean = consumeClean;
    }

    public File regularityMedian(String consumeClean) throws IOException {

        File root = new File(consumeClean);
        File target = new File(root.getParent(),median);

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(target)));

        String str = null;

        ConsumeRecord parser = new ConsumeRecord();

        while ((str = reader.readLine()) != null){

            if(parser.parser(str)){

            }
        }
    }





}
