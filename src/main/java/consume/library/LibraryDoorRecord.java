package consume.library;

/**
 * Created by sghipr on 2016/12/29.
 */
public class LibraryDoorRecord {

    private String ID;

    private String cardid;

    private String userName;

    private String college;

    private String status;

    private String rank;

    private String passway;

    private String passtime;

    private String reportTime;

    private String studentID;

    public boolean parser(String record){

        String[] args = record.trim().split(",", -1);
        if(args.length < 8)
            return false;

        ID = args[0];
        cardid = args[1].replace("\"","");
        userName = args[2].replace("\"","");
        college = args[3].replace("\"","");
        rank = args[4].replace("\"","");
        status = args[5].replace("\"","");
        passway = args[6].replace("\"","");
        passtime = args[7].replace("\"","");
        reportTime = args[8].replace("\"","");
        studentID = args[9].replace("\"","");
        return true;
    }

    public String getStudentID(){
        return studentID;
    }

    public String getCardid(){
        return cardid;
    }

    public String getUserName(){
        return userName;
    }

    public String getCollege(){
        return college;
    }

    public String getRank(){
        return rank;
    }

    public String getPassway(){
        return passway;
    }

    public String getPasstime(){
        return passtime;
    }

    public String toString(){
        return studentID + "," + cardid + "," + userName + "," + college + "," + rank + "," + passway + "," + passtime + "," + ID;
    }
}
