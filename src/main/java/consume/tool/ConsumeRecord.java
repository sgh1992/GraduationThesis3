package consume.tool;

import java.net.StandardSocketOptions;

/**
 * Created by sghipr on 2016/12/4.
 */
public class ConsumeRecord implements  Comparable<ConsumeRecord>{

    private String studentID;

    private String type;

    private String place;

    private String cardNo;

    private String time;

    private double amount;

    private double balance;

    private int term;

    private String year;

    public ConsumeRecord(){}

    public ConsumeRecord(String studentID, String type, String place, String cardNo, String time,
                         double amount, double balance, int term, String year){
        this.studentID = studentID;
        this.type = type;
        this.place = place;
        this.cardNo = cardNo;
        this.time = time;
        this.amount = amount;
        this.balance = balance;
        this.term = term;
        this.year = year;
    }

    public String getStudentID(){
        return studentID;
    }

    public String getType(){
        return type;
    }

    public String getPlace(){
        return place;
    }

    public String getCardNo(){
        return cardNo;
    }

    public String getTime(){
        return time;
    }

    public double getAmount(){
        return amount;
    }

    public double getBalance(){
        return balance;
    }

    public int getTerm(){
        return term;
    }

    public String getYear(){
        return year;
    }

    public boolean parser(String record){
        String[] args = record.split(",", -1);
        if(args.length < 9)
            return false;
        studentID = args[0];
        type = args[1];
        place = args[2];
        cardNo = args[3];
        time = args[4];
        try {
            amount = Double.parseDouble(args[5]);
            balance = Double.parseDouble(args[6]);
            term = Integer.parseInt(args[7]);
        }catch (Exception e){
            System.err.println(record + " can not format to ConsumeRecord!");
            return false;
        }
        year = args[8];
        return true;
    }

    public int hashCode(){
        return studentID.hashCode()*17 + type.hashCode() + place.hashCode() + cardNo.hashCode()*13 + time.hashCode()
                + (int)amount  + (int)balance + term + year.hashCode();
    }

    @Override
    public int compareTo(ConsumeRecord o) {
        return studentID.equals(o.studentID) ?
                time.compareTo(o.time) : studentID.compareTo(o.studentID);
    }

    public String toString(){
       return studentID + "," + type + "," + place + "," + cardNo + "," + time + ","
               + amount + "," + balance + "," + term + "," + year;
    }
}
