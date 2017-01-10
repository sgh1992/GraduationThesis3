package grade.tool;

import consume.tool.SortBase;

/**
 * Created by sghipr on 2017/1/10.
 * 学生成绩记录重构.
 */
public class StudentGradeRefactor implements SortBase{

    private String studentID;

    private String userName;

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

    public StudentGradeRefactor(){}

    public StudentGradeRefactor(StudentGradeRefactor o){
        this.studentID = o.studentID;
        this.userName = o.userName;
        this.courseNo = o.courseNo;
        this.teacherNo = o.teacherNo;
        this.className = o.className;
        this.classNature = o.classNature;
        this.credit = o.credit;
        this.grade = o.grade;
        this.renovateGrade = o.renovateGrade;
        this.resitGrade = o.resitGrade;
        this.term = o.term;
        this.year = o.year;
    }

    public boolean parser(String record){
        String[] args = record.split(",", -1);
        if(args.length < 12)
            return false;
        studentID = args[0];
        userName = args[1];
        courseNo = args[2];
        teacherNo = args[3];
        className = args[4];
        classNature = args[5];
        try {
            credit = Double.parseDouble(args[6]);
            grade = Double.parseDouble(args[7]);
            renovateGrade = Double.parseDouble(args[8]);
            resitGrade = Double.parseDouble(args[9]);
            term = Integer.parseInt(args[10]);
        }catch (Exception e){
            return false;
        }
        year = args[11];
        if(term < 0 || courseNo == null || teacherNo == null || courseNo.length() == 0 || teacherNo.length() == 0)
            return false;
        return true;
    }

    @Override
    public SortBase create(SortBase o) {
        StudentGradeRefactor other = (StudentGradeRefactor)o;
        return new StudentGradeRefactor(other);
    }

    public String getStudentID(){
        return studentID;
    }

    @Override
    public String getType() {
        return "grade";
    }

    @Override
    public String getTime() {
        return null;
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
        return studentID + "," + userName + "," +  courseNo + "," + teacherNo + "," + className + ","
                + classNature + "," + credit + "," + grade + "," + renovateGrade + "," + resitGrade + "," + term + "," + year;
    }

    @Override
    public int compareTo(SortBase o) {
        StudentGradeRefactor other = (StudentGradeRefactor)o;
        return this.studentID.equals(other.studentID) ? term - other.term : this.studentID.compareTo(other.studentID);
    }
}
