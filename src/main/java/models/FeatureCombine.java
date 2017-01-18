package models;

import java.util.Set;

/**
 * Created by sghipr on 2017/1/18.
 * 将所有的特征组合在一起.
 * 注意需要按学期来组合，最终拼凑成一个完整的文件.
 */
public class FeatureCombine {

    /**
     * 有关学生成绩的特征文件
     */
    private String grades;

    /**
     * 有关学生图书借阅的特征文件.
     */
    private String interest;

    /**
     * 有关学生家庭经济情况的特征文件.
     */
    private String familyEconomic;

    /**
     * 学生的行为规律性文件.
     */
    private String regularity;

    /**
     * 所需要的学期.
     */
    private Set<Integer> termSet;

    private String combines;

    public FeatureCombine(String grades, String interest, String familyEconomic, String regularity, Set<Integer> termSet, String combines){
        this.grades = grades;
        this.interest = interest;
        this.familyEconomic = familyEconomic;
        this.regularity = regularity;
        this.termSet = termSet;
        this.combines = combines;
    }






}
