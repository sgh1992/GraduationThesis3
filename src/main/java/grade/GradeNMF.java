package grade;

import java.util.Set;

/**
 * Created by sghipr on 10/01/17.
 * 成绩矩阵进行非負矩阵分解.
 * 并利用梯度下降法亚求解参数.
 */
public class GradeNMF {

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

    public GradeNMF(String origialGrades, String tempDir, int minNums, Set<Integer> termSet, String year){

        this.origialGrades = origialGrades;
        this.tempDir = tempDir;
        this.minNums = minNums;
        this.termSet = termSet;
        this.year = year;
    }

}