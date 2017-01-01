package consume.tool;

/**
 * Created by sghipr on 2016/12/4.
 */
public class ConsumeRecord implements SortBase{

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

    public ConsumeRecord(ConsumeRecord o){
        this.studentID = o.studentID;
        this.type = o.type;
        this.place = o.place;
        this.cardNo = o.cardNo;
        this.time = o.time;
        this.amount = o.amount;
        this.balance = o.balance;
        this.term = o.term;
        this.year = o.year;
    }

    public void update(ConsumeRecord o){

        this.studentID = o.studentID;
        this.type = o.type;
        this.place = o.place;
        this.cardNo = o.cardNo;
        this.time = o.time;
        this.amount = o.amount;
        this.balance = o.balance;
        this.term = o.term;
        this.year = o.year;
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
        if(term > 8)
            return false;
        return true;
    }

    @Override
    public SortBase create(SortBase o) {

        ConsumeRecord other = (ConsumeRecord)o;
        return new ConsumeRecord(other);
    }

    public int hashCode(){
        return studentID.hashCode()*17 + type.hashCode() + place.hashCode() + cardNo.hashCode()*13 + time.hashCode()
                + (int)amount  + (int)balance + term + year.hashCode();
    }

    public String toString(){
       return studentID + "," + type + "," + place + "," + cardNo + "," + time + ","
               + amount + "," + balance + "," + term + "," + year;
    }

    @Override
    public int compareTo(SortBase others) {
        ConsumeRecord o = (ConsumeRecord)others;
        return studentID.equals(o.studentID) ?
                time.compareTo(o.time) : studentID.compareTo(o.studentID);
    }
}
