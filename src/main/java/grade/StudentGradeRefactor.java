package grade;

/**
 * Created by sghipr on 2017/1/10.
 * 学生成绩记录重构.
 */
public class StudentGradeRefactor {

    private String studentID;

    private String courseNo;

    private String teacherNo;

    private String className;

    private String classNature;

    private double credit;

    private double grade;

    private double renovateGrade;

    private double resitGrade;

    private int term;

    private String year;

    public boolean parser(String record){

        String[] args = record.split(",", -1);
        if(args.length < 11)
            return false;
        studentID = args[0];
        courseNo = args[1];
        teacherNo = args[2];
        className = args[3];
        classNature = args[4];
        try {
            credit = Double.parseDouble(args[5]);
            grade = Double.parseDouble(args[6]);
            renovateGrade = Double.parseDouble(args[7]);
            resitGrade = Double.parseDouble(args[8]);
            term = Integer.parseInt(args[9]);
        }catch (Exception e){
            return false;
        }

        year = args[10];
        if(term < 0 || courseNo == null || teacherNo == null || courseNo.length() == 0 || teacherNo.length() == 0)
            return false;
        return true;
    }

    public String getStudentID(){
        return studentID;
    }
    public String getCourseNo(){
        return courseNo;
    }

    public String getTeacherNo(){
        return teacherNo;
    }

    public String getClassName(){
        return className;
    }

    public String getClassNature(){
        return classNature;
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

    public int getTerm(){
        return term;
    }

    public String getYear(){
        return year;
    }

    public String toString(){
        return studentID + "," + courseNo + "," + teacherNo + "," + className + ","
                + classNature + "," + credit + "," + grade + "," + renovateGrade + "," + resitGrade + "," + term + "," + year;
    }
}
