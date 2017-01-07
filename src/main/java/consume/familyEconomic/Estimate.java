package consume.familyEconomic;

import consume.tool.ConsumeRecord;
import consume.tool.Tool;

import java.io.*;
import java.util.*;

/**
 * Created by sghipr on 2017/1/6.
 */
public class Estimate {

    /**
     * 在各个学期的消费总额与消费天数.
     */
    class ConsumeDayAndAmount{

        private String studentID;

        private HashMap<Integer,Integer> daysMap;

        private HashMap<Integer, Double> amountMap;

        private String beforeDate;

        private List<Integer> termList;

        private BufferedWriter writer;

        public ConsumeDayAndAmount(String studentID, String beforeDate, BufferedWriter writer){
            this.studentID = studentID;
            daysMap = new HashMap<>();
            amountMap = new HashMap<>();
            this.beforeDate = beforeDate;
            this.writer = writer;

            termList = new ArrayList<>();
            for(int term = 1; term <= 8; term++)
                termList.add(term);

        }

        public boolean update(String studentID,int term, String date, double amount){

            if(!studentID.equals(this.studentID))
                return false;

            if(amountMap.containsKey(term))
                amount += amountMap.get(term);
            amountMap.put(term,amount);

            if(!date.split(" ")[0].equals(beforeDate.split(" ")[0])){
                int days = 1;
                if(daysMap.containsKey(term))
                    days += daysMap.get(term);
                daysMap.put(term,days);
                beforeDate = date;
            }
            return true;
        }

        public boolean contains(String studentID){
            return this.studentID.equals(studentID);
        }

        public int getDay(String studentID,int term){
            return this.studentID.equals(studentID) && daysMap.containsKey(term) ? daysMap.get(term) : 0;
        }

        public double getAmount(String studentID, int term){
            return this.studentID.equals(studentID) && amountMap.containsKey(term) ? amountMap.get(term) : 0;
        }

        public void write() throws IOException {

            StringBuilder result = new StringBuilder();
            result.append(studentID).append(",");
            for(int term : termList){
                int day = getDay(studentID,term);
                result.append(day).append(",");
            }

            for(int term : termList){
                double amount = getAmount(studentID,term);
                result.append(amount).append(",");
            }

            //每天的平均消费
            for(int term : termList){
                int day = getDay(studentID,term);
                double amount = getAmount(studentID,term);
                double average = day == 0 ? 0 : amount/day;
                result.append(average).append(",");
            }

            writer.write(result.substring(0,result.length() - 1));
            writer.newLine();

        }
    }

    /**
     * 某类消费类型在各个学期的消费总额.
     */
    class ConsumeTypeAmount{

        private String studentID;

        private Set<String> typeSet;

        private HashMap<Integer, Double> amountMap;

        private List<Integer> termList ;

        private BufferedWriter writer;

        private String type;

        public ConsumeTypeAmount(String studentID, String type, BufferedWriter writer,Set<String> set){

            this.studentID = studentID;
            this.type = type;
            amountMap = new HashMap<>();
            termList = new ArrayList<>();
            for(int term = 1; term <= 8; term++)
                termList.add(term);
            this.writer = writer;
            this.typeSet = set;
        }

        public boolean update(String studentID,String type, int term, double amount){

            if(!studentID.equals(this.studentID)) {
                return false;
            }

            if(!typeSet.contains(type))
                return true;

            if(amountMap.containsKey(term))
                amount += amountMap.get(term);

            amountMap.put(term,amount);
            return true;
        }

        public double getAmount(int term){
            return amountMap.containsKey(term) ? amountMap.get(term) : 0;
        }

        public String toString(ConsumeDayAndAmount consumeDayAndAmount){

            StringBuilder result = new StringBuilder();
            result.append(studentID).append(",");
            result.append(type).append(",");

            for(int term : termList){
                double sumAmount = consumeDayAndAmount.getAmount(studentID,term);
                double amount = getAmount(term);
                double rate = sumAmount == 0.0 ? 0.0 : amount/sumAmount;
                result.append(rate).append(",");
            }
            return result.substring(0,result.length() - 1).toString();
        }

        public void write(ConsumeDayAndAmount consumeDayAndAmount) throws IOException {
            writer.write(toString(consumeDayAndAmount));
            writer.newLine();
        }
    }

    private String cleanConsume;

    private String dayAndSumAmount;

    private String messSumAmount;

    private String supermarketAmount;

    private Estimate(String cleanConsume, String dayAndSumAmount, String messSumAmount, String supermarketAmount){
        this.cleanConsume = cleanConsume;
        this.dayAndSumAmount = dayAndSumAmount;
        this.messSumAmount = messSumAmount;
        this.supermarketAmount = supermarketAmount;
    }

    public void estimate() throws IOException {

        ConsumeRecord parser = new ConsumeRecord();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(cleanConsume)));

        BufferedWriter dayAndSumWriter = new BufferedWriter(new FileWriter(dayAndSumAmount));

        BufferedWriter messSumWrite = new BufferedWriter(new FileWriter(messSumAmount));

        BufferedWriter supermarketWriter = new BufferedWriter(new FileWriter(supermarketAmount));

        ConsumeDayAndAmount consumeDayAndAmount = null;

        ConsumeTypeAmount messAmount = null;

        ConsumeTypeAmount supermarketAmount = null;

        String str = null;

        Set<String> messSet = new HashSet<>();
        messSet.add("mess");

        Set<String> supermarketSet = new HashSet<>();
        supermarketSet.add("supermarket");
        supermarketSet.add("snack");

        while ((str = reader.readLine()) != null){

            if(parser.parser(str)){

                if(consumeDayAndAmount == null){
                    consumeDayAndAmount = new ConsumeDayAndAmount(parser.getStudentID(),parser.getTime(),dayAndSumWriter);
                    messAmount = new ConsumeTypeAmount(parser.getStudentID(),"mess",messSumWrite,messSet);
                    supermarketAmount = new ConsumeTypeAmount(parser.getStudentID(),"supermarket",supermarketWriter,supermarketSet);
                }

                if(!consumeDayAndAmount.contains(parser.getStudentID())){
                    consumeDayAndAmount.write();
                    messAmount.write(consumeDayAndAmount);
                    supermarketAmount.write(consumeDayAndAmount);

                    consumeDayAndAmount = new ConsumeDayAndAmount(parser.getStudentID(),parser.getTime(),dayAndSumWriter);
                    messAmount = new ConsumeTypeAmount(parser.getStudentID(),"mess",messSumWrite,messSet);
                    supermarketAmount = new ConsumeTypeAmount(parser.getStudentID(),"supermarket",supermarketWriter,supermarketSet);
                }

                consumeDayAndAmount.update(parser.getStudentID(),parser.getTerm(),parser.getTime(),parser.getAmount());
                messAmount.update(parser.getStudentID(),parser.getType(),parser.getTerm(),parser.getAmount());
                supermarketAmount.update(parser.getStudentID(),parser.getType(),parser.getTerm(),parser.getAmount());
            }
        }

        consumeDayAndAmount.write();
        messAmount.write(consumeDayAndAmount);
        supermarketAmount.write(consumeDayAndAmount);

        dayAndSumWriter.close();
        messSumWrite.close();
        supermarketWriter.close();
        reader.close();
    }

    public static void main(String[] args) throws IOException {

        String cleanConsume = "D:\\GraduationThesis\\consume_clean_step1_sorted_removeDeuplicate.csv";
        String dayAndAmount = "D:/GraduationThesis/consumeDayAndAmount.csv";
        String messAmount = "D:/GraduationThesis/consumeMess.csv";
        String supermarket = "D:/GraduationThesis/consumeSuperMarket.csv";
        Estimate estimate = new Estimate(cleanConsume,dayAndAmount,messAmount,supermarket);
        estimate.estimate();
        Tool.addWork(dayAndAmount);
        Tool.addWork(messAmount);
        Tool.addWork(supermarket);
    }
}
