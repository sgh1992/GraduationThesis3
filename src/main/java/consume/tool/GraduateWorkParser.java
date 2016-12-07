package consume.tool;

/**
 * Created by sghipr on 2016/12/7.
 */
public class GraduateWorkParser {

    private String year;

    private String studentID;

    private String nation;

    private String gender;

    private String political;

    private String birthday;

    private String major;

    private String work;

    private String company;

    private String workplace;

    private String sector;

    private String orgin;

    private String college;

    public GraduateWorkParser(){}

    public boolean parser(String record){
        String[] args = record.split(",", -1);
        if(args.length < 13)
            return false;

        year = args[0];
        studentID = args[1];
        nation = args[2];
        gender = args[3];
        political = args[4];
        birthday = args[5];
        major = args[6];
        work = args[7];
        company = args[8];
        workplace = args[9];
        sector = args[10];
        orgin = args[11];
        college = args[12];

        return true;
    }

    public String getYear(){
        return year;
    }

    public String getStudentID(){
        return studentID;
    }

    public String getNation(){
        return nation;
    }

    public String getGender(){
        return gender;
    }

    public String getPolitical(){
        return political;
    }

    public String getBirthday(){
        return birthday;
    }

    public String getMajor(){
        return major;
    }

    public String getWork(){
        return work;
    }

    public String getCompany(){
        return company;
    }

    public String getWorkplace(){
        return workplace;
    }

    public String getSector(){
        return orgin;
    }

    public String getCollege(){
        return college;
    }

    public String toString(){
        return year + "," + studentID + "," + nation + "," + gender + "," + political + "," + birthday + "," + major + "," + work + "," + company + "," + workplace + "," + orgin + "," + college;
    }


}
