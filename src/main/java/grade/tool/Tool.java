package grade.tool;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sghipr on 2017/1/10.
 */
public class Tool {

    private static String Customise = "customiseGrade.csv";//定制的成绩记录.

    public static File customiseGrade(String grades, Set<Integer> termSet,String year) throws IOException {

        StudentGradeRecord parser = new StudentGradeRecord();

        File root = new File(grades);
        File target = new File(root.getParent(),Customise);
        String str = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(root)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(target));

        while ((str = reader.readLine()) != null){
            if(parser.parser(str)){

                int term = parser.getTerm(parser.getStudentID());
                if(termSet.contains(term) && year.equals(parser.getYear(parser.getStudentID()))){
                    if(parser.getRenovateGrade() > 1)
                        System.err.println(parser.getRenovateGrade());
                    StringBuilder resultBuiler = new StringBuilder();
                    resultBuiler
                            .append(parser.getStudentID()).append(",")
                            .append(parser.getUserName()).append(",")
                            .append(parser.getCourseNo()).append(",")
                            .append(parser.getTeacherNo()).append(",")
                            .append(parser.getClassName()).append(",")
                            .append(parser.getClassNature()).append(",")
                            .append(parser.getCredit()).append(",")
                            .append(parser.getGrade()).append(",")
                            .append(parser.getRenovateGrade()).append(",")
                            .append(parser.getResitGrade()).append(",")
                            .append(term).append(",")
                            .append(parser.getYear(parser.getStudentID()));
                    writer.write(resultBuiler.toString());
                    writer.newLine();
                }
            }
        }
        reader.close();
        writer.close();
        return target;
    }

    public static void main(String[] args) throws IOException {

        String grades = "D:\\GraduationThesis\\studentallgrade0.csv";
        Set<Integer> termSet = new HashSet<>();
        for(int term = 1; term <= 6; term++)
            termSet.add(term);
        String year = "2010";
        File result = customiseGrade(grades,termSet,year);
    }


}
