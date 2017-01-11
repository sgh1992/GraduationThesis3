package grade.tool;

import consume.dataClean.SortCleanData;
import consume.tool.SortBase;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by sghipr on 2017/1/10.
 */
public class Tool {

    private static String Customise = "customiseGrade.csv";//定制的成绩记录.

    private static String CLEANGRADES = "cleanGrades.csv";

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

    public static HashMap<String,Integer> getCourseMap(File refactorFile,int minNums) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(refactorFile)));
        HashMap<String,Integer> counts = new HashMap<>();
        String str = null;
        StudentGradeRefactor parser = new StudentGradeRefactor();
        while ((str = reader.readLine()) != null){
            if(parser.parser(str) && parser.getRenovateGrade() == 0) {
                int count = 1;
                if (counts.containsKey(parser.getCourseNo()))
                    count += counts.get(parser.getCourseNo());
                counts.put(parser.getCourseNo(), count);
            }
        }
        reader.close();
        HashMap<String, Integer> courseIDs = new HashMap<>();
        int id = 0;
        for(Map.Entry<String,Integer> entry : counts.entrySet()){
            if(entry.getValue() >= minNums)
                courseIDs.put(entry.getKey(),id++);
        }
        return courseIDs;
    }

    public static HashMap<String,Set<Integer>> getStudentNums(File file,HashMap<String,Integer> courseMap) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String str = null;
        HashMap<String,Set<Integer>> studentsMap = new HashMap<>();
        Set<Integer> set = null;
        StudentGradeRefactor parser = new StudentGradeRefactor();
        while ((str = reader.readLine()) != null){
            if(parser.parser(str)){
                if(studentsMap.containsKey(parser.getStudentID()))
                    set = studentsMap.get(parser.getStudentID());
                else
                    set = new HashSet<>();
                if(courseMap.containsKey(parser.getCourseNo())){
                    set.add(courseMap.get(parser.getCourseNo()));
                    studentsMap.put(parser.getStudentID(),set);
                }
                else{
                    System.err.println("Course missing : " + parser.getCourseNo());
                    continue;
                }
            }
        }
        reader.close();
        return studentsMap;
    }

    public static File cleaned(File refactor,int minNums) throws IOException {

        HashMap<String,Integer> courseMap = Tool.getCourseMap(refactor,minNums);

        File cleaned = new File(refactor.getParent(),CLEANGRADES);
        BufferedWriter writer = new BufferedWriter(new FileWriter(cleaned));

        String str = null;
        StudentGradeRefactor parser = new StudentGradeRefactor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(refactor)));
        while ((str = reader.readLine()) != null){
            if(parser.parser(str)){
                if(courseMap.containsKey(parser.getCourseNo())){
                    writer.write(str);
                    writer.newLine();
                }
            }
        }

        reader.close();
        return cleaned;

    }

    public static File sorted(File cleanedFile, String tempDir) throws IOException {

        SortCleanData sortCleanData = new SortCleanData(cleanedFile.getAbsolutePath(),tempDir,new StudentGradeRefactor());
        return sortCleanData.sort();

    }

    /**
     * 对数据进行排序处理.
     * @param grades
     * @param termSet
     * @param year
     * @param minNums
     * @param tempDir
     * @return
     * @throws IOException
     */
    public static File sorted(String grades, Set<Integer> termSet, String year, int minNums,String tempDir) throws IOException {

        File refactors = customiseGrade(grades,termSet,year);
        File cleaned = cleaned(refactors,minNums);
        return sorted(cleaned,tempDir);
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
