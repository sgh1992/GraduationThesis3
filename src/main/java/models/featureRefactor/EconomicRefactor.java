package models.featureRefactor;

import java.io.*;

/**
 * Created by sghipr on 2017/2/9.
 * 学生的家庭经济条件进行重构.
 */
public class EconomicRefactor {

    private String economic;

    private static String REFACTOR = "Refactor";

    private static String DAYANDAMOUNT = "dayAndAmount";

    private static String Rate = "Rate";

    public EconomicRefactor(){
    }

    /**
     * 衡量学生家庭经济状况的指标.
     * @param economic
     * @return
     * @throws IOException
     */
    public File refactor(String economic, boolean isRate) throws IOException {

        File root = new File(economic);
        File result = new File(root.getParent(),root.getName().substring(0, root.getName().indexOf(".")) + "_" + REFACTOR + ".csv");

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(root)));

        BufferedWriter writer = new BufferedWriter(new FileWriter(result));

        String str = null;

        while ((str = reader.readLine()) != null){

            String[] args = str.split(",", -1);
            String studentID = args[0];
            if(!isRate){ //针对消费天数与消费总额的情况.
                int index = 1;
                for(int i = 0; i < 8; ++i){
                    StringBuilder record = new StringBuilder();
                    record.append(studentID).append(",");
                    record.append(i + 1).append(",");
                    record.append(DAYANDAMOUNT).append(",");

                    String days = args[index + i]; //在校内的总消费天数.
                    String sumAmount = args[index + i + 8]; //在校内的总消费额.
                    String average = args[index + i + 8 + 8]; //在校内的平均消费额.

                    record.append(days).append(",");
                    record.append(sumAmount).append(",");
                    record.append(average);

                    writer.write(record.toString());
                    writer.newLine();
                }
            }
            else {
                String type = args[1] + Rate;
                int index = 2;
                for(int term = 0; term < 8; ++term){
                    StringBuilder record = new StringBuilder();
                    record.append(studentID).append(",");
                    record.append(term + 1).append(",");
                    record.append(type).append(",");
                    record.append(args[index + term]);

                    writer.write(record.toString());
                    writer.newLine();
                }
            }
        }

        writer.close();
        reader.close();
        return result;
    }

    public static void main(String[] args) throws IOException {

        String dayAndAmount = "D:\\GraduationThesis\\consumeDayAndAmount.csv"; //记录学生在校总消费天数与总消费金额的文件.

        String messRate = "D:\\GraduationThesis\\consumeMess.csv";

        String superMarketRate = "D:\\GraduationThesis\\consumeSuperMarket.csv";

        EconomicRefactor economicRefactor = new EconomicRefactor();

        File dayAndAmountRefactor = economicRefactor.refactor(dayAndAmount,false);

        File messRefactor = economicRefactor.refactor(messRate,true);

        File superMarketRefactor = economicRefactor.refactor(superMarketRate,true);

    }





}
