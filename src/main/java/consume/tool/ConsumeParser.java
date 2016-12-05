package consume.tool;

/**
 * Created by sghipr on 2016/12/4.
 */
public class ConsumeParser {

    private String cardNo;

    private String studentID;

    private String transName;

    private String deviceName;

    private String devphyid;

    private String transDate;

    private String transTime;

    private double amount;

    private double balance;

    private long rn;

    public boolean parser(String record){

        String[] args = record.split(",", -1);
        if(args.length < 10 || args[2].equals("校园卡充值"))
            return false;

        cardNo = args[0];
        studentID = args[1];
        transName = args[2];
        deviceName = args[3];
        devphyid = args[4];
        transDate = args[5];
        transTime = args[6];
        try{
            amount = Double.parseDouble(args[7]);
            balance = Double.parseDouble(args[8]);
            rn = Long.parseLong(args[9]);

        }catch (NumberFormatException exception){
            System.err.println(record + " can not format!");
            return false;
        }
        return true;
    }

    public String getCardNo(){
        return cardNo;
    }

    public String getStudentID(){
        return studentID;
    }

    public String getTransName(){
        return transName;
    }

    public String getDeviceName(){
        return deviceName;
    }

    public String getDevphyid(){
        return devphyid;
    }

    public String getTransDate(){
        return transDate;
    }

    public String getTransTime(){
        return transTime;
    }

    public double getAmount(){
        return amount;
    }

    public double getBalance(){
        return balance;
    }

}
