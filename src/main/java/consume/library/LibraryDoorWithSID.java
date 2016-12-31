package consume.library;

import consume.tool.Tool;
import consume.tool.YearAndTerm;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sghipr on 2016/12/30.
 */
public class LibraryDoorWithSID {

    class StudentInfoRecord{

        private String year;

        private String studentID;

        private String userName;

        private String college;

        public boolean parser(String record){

            String[] args = record.trim().split(",", -1);
            if(args.length < 4)
                return false;

            year = args[0];
            studentID = args[1];
            userName = args[2];
            college = args[3];
            return true;
        }

        public String getYear(){
            return year;
        }

        public String getStudentID(){
            return studentID;
        }

        public String getUserName(){
            return userName;
        }

        public String getCollege(){
            return college;
        }

        public String toString(){
            return year + "," + studentID + "," + userName + "," + college;
        }
    }

    private String studentInfoFile = "D:\\GraduationThesis\\studentnameinfo.txt";
    private String libraryDoorFile = "D:\\GraduationThesis\\librarydoor2009_2014_refactor.csv";
    private static String LIBRARYDOORWITHID = "librarydoorWithStudentID.csv";

    public LibraryDoorWithSID(){}

    public File getLibraryDoorWithStudentID() throws IOException {
        return getLibraryDoorWithStudentID(studentInfoFile,libraryDoorFile);
    }

    public File getLibraryDoorWithStudentID(String studentInfoFile, String libraryDoorFile) throws IOException {
        HashMap<String,Set<String>> collegeAndIDMap = getIDInfo(studentInfoFile);
        LibraryDoorRecord parser = new LibraryDoorRecord();
        File root = new File(libraryDoorFile);
        File dir = new File(root.getParent(),LIBRARYDOORWITHID);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(root)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(dir));
        String str = null;
        StringBuilder result = new StringBuilder();
        YearAndTerm yearAndTerm = new YearAndTerm();
        Set<String> inSet = new HashSet<>();
        for(int i = 5; i < 10; i++)
            inSet.add(String.valueOf(i));

        Set<String> outSet = new HashSet<>();
        for(int i = 1; i < 5; i++)
            outSet.add(String.valueOf(i));

        while ((str = reader.readLine()) != null){
            String studentID = null;
            if(parser.parser(str.trim())){

                String time = parser.getPasstime().replace("-","").substring(0,8);//日
                String hourTime = parser.getPasstime().replace("-","").replace(":","").substring(0,15);//小时
                if(parser.getStudentID().length() != 0){
                    studentID = parser.getStudentID();
                }
                else if(parser.getRank().equals("本科生")){
                    String key = parser.getCollege() + "_" + parser.getUserName();
                    studentID = getStudentID(key, time, collegeAndIDMap);
                    }
                if(studentID != null){
                    if(Tool.getYearTerm(studentID,time,yearAndTerm)) {
                        String passWay = parser.getPassway().trim();
                        if(inSet.contains(passWay) || passWay.contains("进门"))
                            passWay = "in";
                        else if(outSet.contains(passWay) || passWay.contains("出门"))
                            passWay = "out";

                        else
                            continue;
                        result.append(studentID).append(",").append(parser.getUserName()).append(",")
                                .append(passWay).append(",").append(hourTime).append(",").append(parser.getCollege()).append(",")
                                .append(yearAndTerm.getYear()).append(",").append(yearAndTerm.getTerm());
                        writer.write(result.toString());
                        writer.newLine();
                        result.setLength(0);
                    }
                }
            }
        }

        reader.close();
        writer.close();
        return dir;
    }

    public String getStudentID(String key, String time, HashMap<String,Set<String>> map){
        int nums = 0;
        String SID = null;
        if(map.containsKey(key)){
            Set<String> studentIDSet = map.get(key);
            for(String studentID : studentIDSet){
                String year = studentID.startsWith("2010") ? "20100901" : "20090901";
                String endYear = studentID.startsWith("2010") ? "20140801" : "20130801";
                if(time.compareTo(year) < 0 || time.compareTo(endYear) > 0)
                    continue;
                nums++;
                SID = studentID;
            }
        }
        if(nums > 1)
            return null;
        else
            return SID;
    }

    public HashMap<String,Set<String>> getIDInfo(String studentInfoFile) throws IOException {

        HashMap<String,Set<String>> map = new HashMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(studentInfoFile)));

        String str = null;

        StudentInfoRecord parser = new StudentInfoRecord();

        String studentID = null;
        Set<String> set;

        while ((str = reader.readLine()) != null){

            if(parser.parser(str)){
                String key = parser.getCollege() + "_" + parser.getUserName();
                studentID = parser.getStudentID();
                if(map.containsKey(key))
                    set = map.get(key);
                else{
                    set = new HashSet<>();
                    set.add(studentID);
                }
                map.put(key,set);
            }
        }
        reader.close();
        return map;
    }

    public static void main(String[] args) throws IOException {
        LibraryDoorWithSID libraryDoorWithSID = new LibraryDoorWithSID();
        File result = libraryDoorWithSID.getLibraryDoorWithStudentID();
    }

}
