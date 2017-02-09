package grade;
import grade.tool.StudentGradeRefactor;
import grade.tool.Tool;
import org.apache.mahout.math.*;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sghipr on 10/01/17.
 * 成绩矩阵进行非負矩阵分解.
 * 并利用梯度下降法亚求解参数.
 */
public class GradeNMF {


    class PiAndQj{

        private Vector pi;

        private Vector qj;

        private double error;

        public PiAndQj(Vector pi, Vector qj, double error){
            this.pi = pi;
            this.qj = qj;
            this.error = error;
        }

        public Vector getPi(){
            return pi;
        }

        public Vector getQj(){
            return qj;
        }

        public double getError(){
            return error;
        }

    }


    /**
     * 原始成绩文件.
     */
    private String origialGrades;

    /**
     * 排序时的临时目录.
     */
    private String tempDir;

    /**
     * 默认每门课程学生人数的下限.
     */
    private int minNums;

    /**
     * 需要哪几个学期的数据.
     */
    private Set<Integer> termSet;

    /**
     * 需要哪一学年的数据.
     */
    private String year;

    /**
     * 矩阵分解的潜在矩阵的列数.
     * 专业技能的种数.
     */
    private int K;

    /**
     * 用户个数
     */
    private int U;

    /**
     * 课程门数
     */
    private int C;

    /**
     * 梯度下降更新的循环次数.
     */
    private int MAX = 500;

    /**
     * 梯度更新时的幅度.
     */
    private double alpha = 0.5;

    /**
     * 正则化系数.
     */
    private double lamba = 0.5;

    private String USERSKILL = "studentSkill.csv";

    public GradeNMF(String origialGrades, String tempDir, int minNums, Set<Integer> termSet, String year, int K){

        this.origialGrades = origialGrades;
        this.tempDir = tempDir;
        this.minNums = minNums;
        this.termSet = termSet;
        this.year = year;
        this.K = K;
    }

    public void setAlpha(double alpha){
        this.alpha = alpha;
    }

    public void setLamba(double lamba){
        this.lamba = lamba;
    }

    public File NMF() throws IOException {
        return NMF(origialGrades,tempDir,minNums,termSet,year);
    }


    /**
     * 非负矩阵分解.
     * @param origialGrades
     * @param tempDir
     * @param minNums
     * @param termSet
     * @param year
     * @return
     * @throws IOException
     */
    public File NMF(String origialGrades, String tempDir, int minNums, Set<Integer> termSet, String year) throws IOException {

        File sorted = Tool.sorted(origialGrades,termSet,year,minNums,tempDir);
        File target = new File(sorted.getParent(),USERSKILL);

        HashMap<String,Integer> courseMap = Tool.getCourseMap(sorted,minNums);
        C = courseMap.size();

        HashMap<String,Set<Integer>> studentsMap = Tool.getStudentNums(sorted,courseMap);
        U = studentsMap.size();

        HashMap<String,Vector> PMatrix = new HashMap<>();
        DenseMatrix QMatrix = new DenseMatrix(C,K); //这里将之转置了.
        for(int row = 0; row < QMatrix.rowSize(); row++)
            for(int column = 0; column < QMatrix.columnSize(); column++)
                QMatrix.set(row,column,Math.random()); //初始化为0~1之间的数值.

        //inital PMatrix
        for(String studentID : studentsMap.keySet()){
            DenseVector vector = new DenseVector(K);
            for(int i = 0; i < K; i++)
                vector.set(i,Math.random());
            PMatrix.put(studentID,vector);
        }

        String str = null;
        BufferedReader reader = null;
        StudentGradeRefactor parser = new StudentGradeRefactor();
        int iters = 0;
        double sumErrors = Double.MAX_VALUE;
        while (iters++ < MAX && sumErrors > 1e-6){
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(sorted)));
            sumErrors = 0.0;
            while ((str = reader.readLine()) != null){
                if(parser.parser(str) && courseMap.containsKey(parser.getCourseNo())){
                    Vector Pi = PMatrix.get(parser.getStudentID());
                    int courseNo = courseMap.get(parser.getCourseNo());
                    Vector Qj = QMatrix.viewRow(courseNo);
                    int Iij = studentsMap.get(parser.getStudentID()).contains(courseNo) ? 1 : 0;

                    PiAndQj piAndQj = stochasticGradient(Pi,Qj,Iij,parser.getGrade());
                    sumErrors += Math.pow(piAndQj.getError(),2);
                    //System.err.println(sumErrors);

                    PMatrix.put(parser.getStudentID(),piAndQj.getPi());
                    QMatrix.assignRow(courseNo,piAndQj.getQj());
                }
            }
            System.out.printf("Total error:%f,in iters:%d",sumErrors,iters);
            reader.close();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(target));
        for(String studentID : PMatrix.keySet()){
            StringBuilder record = new StringBuilder();
            record.append(studentID).append(",");
            for(int i = 0; i < PMatrix.get(studentID).size(); i++)
                record.append(PMatrix.get(studentID).get(i)).append(",");
            writer.write(record.substring(0,record.length() - 1));
            writer.newLine();
        }
        writer.close();
        return target;
    }

    public PiAndQj stochasticGradient(Vector pi,Vector qj, int Iij, double scores){

        double error = scores - pi.dot(qj);
        if(Double.isNaN(error))
            System.err.println("Debug!");
        if(Math.abs(error) > 1e+6) {
            if(error > 0)
                error = 1e+6;
            else
                error = -1e+6;
        }

        //update pi
        Vector updatePi = qj.times(Iij * error).minus(pi.times(lamba));
        updatePi = pi.plus(updatePi.times(alpha));

        //update qj
        Vector updateQj = pi.times(Iij * error).minus(qj.times(lamba));
        updateQj = qj.plus(updateQj.times(alpha));
        return new PiAndQj(updatePi,updateQj,error);
    }

    public static void main(String[] args) throws IOException {

        String origialGrades = "D:\\GraduationThesis\\studentallgrade0.csv";
        String tempDir = "D:\\GraduationThesis\\gradeTemp";
        int minNums = 30;
        Set<Integer> termSet = new HashSet<>();
        for(int term = 1; term <= 6; term++)
            termSet.add(term);
        String year = "2010";
        int K = 12;

        GradeNMF gradeNMF = new GradeNMF(origialGrades,tempDir,minNums,termSet,year,K);
        //注意这些值的设定。太大了，会导致不收敛.
        gradeNMF.setAlpha(0.0001);
        gradeNMF.setLamba(1e-6);
        File result = gradeNMF.NMF();
    }
}