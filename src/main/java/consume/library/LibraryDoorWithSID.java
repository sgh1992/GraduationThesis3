package consume.library;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sghipr on 2016/12/30.
 */
public class LibraryDoorWithSID {

    class SIDWithName {

        private String studentID;

        private String year;

        public SIDWithName(String studentID, String year){

            this.studentID = studentID;
            this.year = year;

        }

        public int hashCode(){
            return studentID.hashCode() + year.hashCode()*7;
        }

        public boolean equals(SIDWithName others){
            return studentID.equals(others.studentID) && year.equals(others.year);
        }

        public String getStudentID(){
            return studentID;
        }

        public String getName(){
            return year;
        }

        public String toString(){
            return studentID + "," + year;
        }
    }

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
    private String libraryDoorFile = "D:\\GraduationThesis\\librarydoor2013_2014.txt";
    private static String SIDFile = "D:\\GraduationThesis\\librarydoorWithStudentID.csv";

    public String getLibraryDoorWithStudentID(String studentInfoFile, String libraryDoorFile) throws IOException {

        HashMap<String,Set<SIDWithName>> collegeAndIDMap = getIDInfo(studentInfoFile);

        LibraryDoorRecord parser = new LibraryDoorRecord();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(libraryDoorFile)));

        String str = null;
        StringBuilder result = new StringBuilder();
        int wrongNums = 0;

        while ((str = reader.readLine()) != null){
            if(parser.parser(str.trim())){
                if(parser.getStudentID().length() != 0 && parser.getStudentID().startsWith("2010")){
                    result.append(parser.getStudentID()).append(",").append(parser.getUserName()).append(",")
                            .append(parser.getPassway()).append(",").append(parser.getPasstime()).append(",").append("2010");
                }
                else if(parser.getRank().equals("本科生")){
                    String key = parser.getCollege() + "_" + parser.getUserName();

                }
            }

        }
    }

    public boolean

    public HashMap<String,Set<SIDWithName>> getIDInfo(String studentInfoFile) throws IOException {

        HashMap<String,Set<SIDWithName>> map = new HashMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(studentInfoFile)));

        String str = null;

        StudentInfoRecord parser = new StudentInfoRecord();

        SIDWithName sidWithName = null;
        Set<SIDWithName> set;

        while ((str = reader.readLine()) != null){

            if(parser.parser(str)){
                String key = parser.getCollege() + "_" + parser.getUserName();
                sidWithName = new SIDWithName(parser.getStudentID(),parser.getYear());
                if(map.containsKey(key))
                    set = map.get(key);
                else{
                    set = new HashSet<>();
                    set.add(sidWithName);
                }
                map.put(key,set);
            }

        }

        reader.close();
        return map;
    }
}
