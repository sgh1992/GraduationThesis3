package grade.tool;

/**
 * Created by sghipr on 2017/1/10.
 */
public class StudentGradeRecord {

    private String syear;

    private int term;

    private String studentID;

    private String userName;

    private String electiveCourseNo;

    private String className;

    private double credit;

    private double grade;

    private double renovateGrade; //重考标志位

    private double normalGrade;

    private double terminalGrade;

    private double testGrade;

    private double resitGrade; //补考分数

    private String classNature;

    private double middleGrade;

    private String ID;

    public boolean parser(String record){

        String[] args = record.trim().split(",", -1);
        if(args.length < 16)
            return false;

        syear = args[0];

        studentID = args[2];
        userName = args[3];
        electiveCourseNo = args[4];
        className = args[5];
        try {
            term = Integer.parseInt(args[1]);
            credit = Double.parseDouble(args[6]);
            grade = Double.parseDouble(args[7]);
            renovateGrade = Double.parseDouble(args[8]);
            normalGrade = Double.parseDouble(args[9]);
            terminalGrade = Double.parseDouble(args[10]);
            testGrade = Double.parseDouble(args[11]);
            resitGrade = Double.parseDouble(args[12]);
            middleGrade = Double.parseDouble(args[14]);
        }catch (Exception e){
            System.err.println(record);
            return false;
        }

        classNature = args[13];
        ID = args[15];
        return true;
    }

    public String getSyear(){
        return syear;
    }

    public int getTerm(){
        return term;
    }

    /**
     * 返回studentID下真实的学期.
     * @param studentID
     * @return
     */
    public int getTerm(String studentID){
        int startYear = 0;
        int sterm = 0;
        if(studentID.startsWith("29") || studentID.startsWith("2009"))
            startYear = 2009;

        else if(studentID.startsWith("2010"))
            startYear = 2010;

        else if (studentID.startsWith("2011"))
            startYear = 2011;

        else if(studentID.startsWith("2012"))
            startYear = 2012;

        else if(studentID.startsWith("2013"))
            startYear = 2013;

        else if(studentID.startsWith("2014"))
            startYear = 2014;
        else
            return -1;

        String[] args = syear.split("\\-", -1);
        try {
            if(args.length < 2)
                return -1;
            sterm = (Integer.parseInt(args[0]) - startYear) * 2 + term;

        }catch (Exception e){
            return -1;
        }

        if(sterm <= 0 || sterm > 8)
            return -1;
        else
            return sterm;
    }

    public String getYear(String studentID){

        String startYear = "";

        if(studentID.startsWith("29") || studentID.startsWith("2009"))
            startYear = "2009";

        else if(studentID.startsWith("2010"))
            startYear = "2010";

        else if (studentID.startsWith("2011"))
            startYear = "2011";

        else if(studentID.startsWith("2012"))
            startYear = "2012";

        else if(studentID.startsWith("2013"))
            startYear = "2013";

        else if(studentID.startsWith("2014"))
            startYear = "2014";
        else
            return null;

        return startYear;
    }

    public String getStudentID(){
        return studentID;
    }

    public String getUserName(){
        return userName;
    }

    public String getElectiveCourseNo(){
        return electiveCourseNo;
    }

    public String getCourseNo(){
        if(electiveCourseNo == null)
            return null;
        if(electiveCourseNo.trim().length() < 15)
            return null;
        String[] args = electiveCourseNo.trim().substring(14).split("-", -1);//courseNo-teacherNo-ID
        if(args.length > 0)
            return args[0].trim();
        else
            return null;
    }

    public String getTeacherNo(){
        if(electiveCourseNo == null)
            return null;
        if(electiveCourseNo.trim().length() < 15)
            return null;
        String[] args = electiveCourseNo.trim().substring(14).split("-", -1);//courseNo-teacherNo-ID
        if(args.length > 1)
            return args[1].trim();
        else
            return null;
    }

    public double getCredit(){
        return credit;
    }

    public double getGrade(){
        return grade;
    }

    public double getRenovateGrade(){
        return renovateGrade;
    }

    public double getResitGrade(){
        return resitGrade;
    }

    public String getClassNature(){
        return classNature;
    }

    public String getClassName(){
        return className;
    }

}