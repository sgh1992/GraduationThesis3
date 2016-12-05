package consume.dataClean;

import consume.tool.ConsumeNameAndType;

import java.io.IOException;

/**
 * Created by sghipr on 2016/12/4.
 */
public class Driver {

    private static String origialConsumeFile = "D:\\GraduationThesis\\consumeCombine2007_2015.csv";
    public static void main(String[] args) throws IOException {
        ConsumeClean consumeClean = new ConsumeClean(origialConsumeFile);
    }


}
