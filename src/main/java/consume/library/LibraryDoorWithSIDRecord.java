package consume.library;

import consume.tool.SortBase;

/**
 * Created by sghipr on 31/12/16.
 */
public class LibraryDoorWithSIDRecord implements SortBase{

    private String studentID;

    private String name;

    private String status;

    private String passTime;

    private String college;

    private String year;

    private int term;

    public LibraryDoorWithSIDRecord(){}

    public LibraryDoorWithSIDRecord(LibraryDoorWithSIDRecord refactorRecord){

        this.studentID = refactorRecord.studentID;
        this.name = refactorRecord.name;
        this.status = refactorRecord.status;
        this.passTime = refactorRecord.passTime;
        this.college = refactorRecord.college;
        this.year = refactorRecord.year;
        this.term = refactorRecord.term;
    }

    @Override
    public boolean parser(String records) {

        String[] args = records.split(",", -1);
        if(args.length < 7)
            return false;

        studentID = args[0];

        name = args[1];

        status = args[2];

        passTime = args[3];

        college = args[4];

        year = args[5];


        try {
            term = Integer.parseInt(args[6]);
            if(term > 8 || term <= 0)
                return false;
        }catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public SortBase create(SortBase o) {

        LibraryDoorWithSIDRecord other = (LibraryDoorWithSIDRecord)o;

        return new LibraryDoorWithSIDRecord(other);
    }

    public String getStudentID(){
        return studentID;
    }

    @Override
    public String getType() {
        return "librarydoor";
    }

    public String getName(){
        return name;
    }

    public String getStatus(){
        return status;
    }

    public String getTime(){
        return passTime;
    }

    public String getCollege(){
        return college;
    }

    public String getYear(){
        return year;
    }

    public int getTerm(){
        return term;
    }


    @Override
    public int compareTo(SortBase other) {

        LibraryDoorWithSIDRecord o = (LibraryDoorWithSIDRecord)other;
        return studentID.compareTo(o.studentID) == 0 ? passTime.compareTo(o.passTime):studentID.compareTo(o.studentID);
    }

    public String toString(){
        return studentID + "," + name + "," + status + "," + passTime + "," + college + "," + year + "," + term;
    }

}
