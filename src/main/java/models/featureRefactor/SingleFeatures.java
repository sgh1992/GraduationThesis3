package models.featureRefactor;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sghipr on 2017/2/11.
 * 抽取单一的的特征来验证不同因素对学生毕业去向预测模型的影响.
 *
 */
public class SingleFeatures {

    private String featureCombines;

    private String target;

    private Set<String> featureTypes;

    public SingleFeatures(String featureCombines, String target,Set<String> featureTypes){
        this.featureCombines = featureCombines;
        this.target = target;
        this.featureTypes = featureTypes;
    }

    public File abstractSingleFeatures(boolean isRegularity) throws IOException {
        return abstractSingleFeatures(featureCombines,target,featureTypes,isRegularity);
    }

    public File abstractSingleFeatures(String featureCombines, String target, Set<String> featureTypes, boolean isRegularity) throws IOException {

        File result = new File(target);

        BufferedWriter writer = new BufferedWriter(new FileWriter(result));

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(featureCombines)));

        String str = null;
        Set<Integer> indexSet = new HashSet<>();
        indexSet.add(0);
        String[] titles = reader.readLine().trim().split(",", -1);

        for(int i = 1; i < titles.length - 1; ++i) {
            String name = titles[i].split("_", -1)[0];
            if (isRegularity) {
                if (!featureTypes.contains(name))
                    indexSet.add(i);
            }
            else
                if(featureTypes.contains(name))
                    indexSet.add(i);
        }
        indexSet.add(titles.length - 1);

        StringBuilder titleBuilder = new StringBuilder();
        for(int i = 0; i < titles.length; ++i)
            if(indexSet.contains(i))
                titleBuilder.append(titles[i]).append(",");

        writer.write(titleBuilder.substring(0,titleBuilder.length() - 1));
        writer.newLine();


        while ((str = reader.readLine()) != null){
            String[] args = str.trim().split(",", -1);
            StringBuilder record = new StringBuilder();
            for(int i = 0; i < args.length; ++i)
                if(indexSet.contains(i))
                    record.append(args[i]).append(",");

            writer.write(record.substring(0,record.length() - 1));
            writer.newLine();
        }

        reader.close();
        writer.close();

        return result;
    }

    public static void main(String[] args) throws IOException {

        String featureCombines = "D:\\GraduationThesis\\combineFeatures1_2_3_4_5_6_addWork.csv";
        String target = "D:\\GraduationThesis\\regularityModel.csv";
        Set<String> set = new HashSet<>();
        set.add("messRate");
        set.add("supermarketRate");
        set.add("dayAndAmount");
        set.add("librarybook");
        set.add("skill");
        boolean isRegularity = true;


        SingleFeatures singleFeatures = new SingleFeatures(featureCombines,target,set);
        File result = singleFeatures.abstractSingleFeatures(isRegularity);
    }

}
