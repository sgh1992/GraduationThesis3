package consume.dataClean;

import consume.tool.ConsumeNameAndType;
import consume.tool.ConsumeParser;
import consume.tool.Tool;
import consume.tool.YearAndTerm;

import java.io.*;
import java.util.HashMap;

/**
 * Created by sghipr on 2016/12/1.
 */
public class ConsumeClean {

    //private String origialConsumeFile = "D:\\GraduationThesis\\consumeCombine2007_2015.csv";

    private String cleanStep1Result = "D:\\GraduationThesis\\consume_clean_step1.csv";

    public ConsumeClean(String origialConsumeFile) throws IOException {
        cleanStep1(origialConsumeFile,cleanStep1Result);
    }

    public void cleanStep1(String origialConsumeFile,String cleanStep1Result) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(origialConsumeFile)));

        BufferedWriter writer = new BufferedWriter(new FileWriter(cleanStep1Result));

        String str = null;

        StringBuilder result = new StringBuilder();

        YearAndTerm yearAndTerm = new YearAndTerm();
        HashMap<String,ConsumeNameAndType> nameAndTypeHashMap = Tool.getSimplePlace();

        ConsumeParser parser = new ConsumeParser();

        while ((str = reader.readLine()) != null){
            if(str.startsWith("cardNo"))
                continue;

            if(parser.parser(str.trim())){
                if(nameAndTypeHashMap.containsKey(parser.getDeviceName().trim())){
                    ConsumeNameAndType nameAndType = nameAndTypeHashMap.get(parser.getDeviceName());
                    String simpleName = nameAndType.getName();
                    String type = nameAndType.getType();

                    if(Tool.getYearTerm(parser.getStudentID(),parser.getTransDate(),yearAndTerm)){
                        String year = yearAndTerm.getYear();
                        int term = yearAndTerm.getTerm();
                        String time = parser.getTransDate().trim() + " " + parser.getTransTime().trim();
                        result.append(parser.getStudentID()).append(",").append(type).append(",").append(simpleName).append(",")
                            .append(parser.getDevphyid()).append(",").append(time).append(",").append(parser.getAmount()).append(",")
                                .append(parser.getBalance()).append(",").append(term).append(",").append(year);
                        writer.write(result.toString());
                        writer.newLine();
                    }
                }
            }
            result.setLength(0);
        }
        writer.close();
        reader.close();
    }
}
