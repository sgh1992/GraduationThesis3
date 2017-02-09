package models;

import consume.tool.Tool;

import java.io.*;
import java.util.*;

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
    private String librarybooks;

    /**
     * 有关学生家庭经济情况的特征文件.
     */
    private String dayAndAmount;

    private String messRate;

    private String supermarket;

    /**
     * 学生的行为规律性文件.
     */
    private String consumeRegularity;

    /**
     * 所需要的学期.
     */
    private Set<Integer> termSet;

    private String combines;

    public FeatureCombine(String grades, String librarybooks, String dayAndAmount, String messRate, String supermarketRate,  String consumeRegularity, String combines){
        this.grades = grades;
        this.librarybooks = librarybooks;
        this.dayAndAmount = dayAndAmount;
        this.messRate = messRate;
        this.supermarket = supermarketRate;
        this.consumeRegularity = consumeRegularity;
        this.combines = combines;
    }

    public void setTerms(Set<Integer> termSet){
        this.termSet = termSet;
    }

    public File combine() throws IOException {
        return combine(combines,grades,librarybooks,consumeRegularity,dayAndAmount,messRate,supermarket);
    }


    public File combine(String result,String grades, String librarybooks, String consumeRegularity, String dayAndAmount, String messRate, String supermarketRate) throws IOException {

        TreeMap<String,Integer> featuresNum = countFeaturesNum(dayAndAmount,messRate,supermarketRate,librarybooks,consumeRegularity);
        HashMap<String,String> featureRecords = featureRecord(dayAndAmount,messRate,supermarketRate,librarybooks,consumeRegularity);

        File resultFile = new File(result);
        BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile));

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(grades)));

        String str = null;

        StringBuilder title = new StringBuilder();
        title.append("studentID").append(",");
        for(int k = 0; k < 12; ++k)
            title.append("skill" + k).append(",");

        for(int term : termSet)
            for(String type : featuresNum.keySet()) {
                int count = featuresNum.get(type);
                for(int i = 0; i < count; ++i) //这类特征下有多少维.
                    title.append(type + "_" + term + "_" + i).append(",");
            }

        System.err.println("featureNums: " + title.toString().split(",", -1).length);
        writer.write(title.substring(0,title.length() - 1));
        writer.newLine();

        while ((str = reader.readLine()) != null){
            String[] args = str.split(",", -1);
            String studentID = args[0];
            StringBuilder record = new StringBuilder();
            record.append(studentID).append(",");
            for(int i = 1; i < args.length; ++i)
                record.append(args[i]).append(",");

            for(int term : termSet)
                for(String type : featuresNum.keySet()){
                    String key = studentID + "_" + term + "_" + type;
                    if(featureRecords.containsKey(key))
                        record.append(featureRecords.get(key)).append(",");
                    else{
                        int count = featuresNum.get(type);
                        for(int i = 0; i < count; ++i)
                            record.append(0.0).append(",");
                    }
                }
            System.err.println(record.toString().split(",", -1).length);
            writer.write(record.substring(0,record.length() - 1));
            writer.newLine();
        }

        reader.close();
        writer.close();
        return resultFile;
    }

    public TreeMap<String,Integer> countFeaturesNum(String dayAndAmount, String messRate, String supermarketRate,String librarybooks, String consumeRegularity) throws IOException {

        List<BufferedReader> readerList = new ArrayList<>();
        readerList.add(new BufferedReader(new InputStreamReader(new FileInputStream(dayAndAmount))));
        readerList.add(new BufferedReader(new InputStreamReader(new FileInputStream(messRate))));
        readerList.add(new BufferedReader(new InputStreamReader(new FileInputStream(supermarketRate))));
        readerList.add(new BufferedReader(new InputStreamReader(new FileInputStream(librarybooks))));
        readerList.add(new BufferedReader(new InputStreamReader(new FileInputStream(consumeRegularity))));

        TreeMap<String,Integer> featuresNum = new TreeMap<>();

        for(BufferedReader reader : readerList){

            String str = null;
            while ((str = reader.readLine()) != null){
                String[] args = str.split(",", -1);
                String studentID = args[0];
                String term = args[1];
                String type = args[2];
                int count = args.length - 3;
                if(count <= 0){
                    System.err.println(str);
                    continue;
                }

                String key = type;
                if(!featuresNum.containsKey(key))
                    featuresNum.put(key,count);
            }
            reader.close();
        }

        return featuresNum;
    }


    public HashMap<String,String> featureRecord(String dayAndAmount, String messRate, String supermarketRate,String librarybooks, String consumeRegularity) throws IOException {


        List<BufferedReader> readerList = new ArrayList<>();
        readerList.add(new BufferedReader(new InputStreamReader(new FileInputStream(dayAndAmount))));
        readerList.add(new BufferedReader(new InputStreamReader(new FileInputStream(messRate))));
        readerList.add(new BufferedReader(new InputStreamReader(new FileInputStream(supermarketRate))));
        readerList.add(new BufferedReader(new InputStreamReader(new FileInputStream(librarybooks))));
        readerList.add(new BufferedReader(new InputStreamReader(new FileInputStream(consumeRegularity))));

        HashMap<String,String> featureRecords = new HashMap<>();

        for(BufferedReader reader : readerList){

            String str = null;
            while ((str = reader.readLine()) != null){
                String[] args = str.split(",", -1);
                String studentID = args[0];
                String term = args[1];
                String type = args[2];

                StringBuilder features = new StringBuilder();
                for(int i = 3; i < args.length; ++i)
                    features.append(args[i]).append(",");

                String key = studentID + "_" + term + "_" + type;
                featureRecords.put(key,features.substring(0,features.length() - 1));
            }
            reader.close();
        }

        return featureRecords;
    }


    /**
     * 测试.
     * @param args
     */
    public static void main(String[] args) throws IOException {

        String grades = "D:\\GraduationThesis\\studentSkill.csv";

        String librarybooks = "D:\\GraduationThesis\\bookBorrowAnalysize.csv";

        String dayAndAmount = "D:\\GraduationThesis\\consumeDayAndAmount_Refactor.csv";

        String messRate = "D:\\GraduationThesis\\consumeMess_Refactor.csv";

        String supermarketRate = "D:\\GraduationThesis\\consumeSuperMarket_Refactor.csv";

        String consumeRegularity = "D:\\GraduationThesis\\consumeRegularity_refactor.csv";

        String combines = "D:\\GraduationThesis\\combineFeatures.csv";

        Set<Integer> termSet = new HashSet<>();
        termSet.add(1);
        termSet.add(2);
        termSet.add(3);

        FeatureCombine featureCombine = new FeatureCombine(grades, librarybooks, dayAndAmount, messRate, supermarketRate, consumeRegularity, combines);

        featureCombine.setTerms(termSet);
        File result = featureCombine.combine();
        System.err.println(result.getPath());
        File addWork = Tool.addWork(result.getPath());
        System.err.println(addWork.getName());

    }






}
