package consume.librarybook;

import consume.tool.SortBase;

/**
 * Created by sghipr on 2017/1/5.
 */
public class BookBorrowRecordRefactor implements SortBase{

    private String studentID;

    private String itemNum;

    private String date;

    private String type;

    private String year;

    private int term;

    public BookBorrowRecordRefactor(){}

    public BookBorrowRecordRefactor(BookBorrowRecordRefactor o){
        this.studentID = o.studentID;
        this.itemNum = o.itemNum;
        this.date = o.date;
        this.type = o.type;
        this.year = o.year;
        this.term = o.term;
    }

    @Override
    public boolean parser(String records) {

        String[] args = records.split(",", -1);
        if(args.length < 6)
            return false;
        studentID = args[0];
        itemNum = args[1];
        date = args[2];
        type = args[3];
        year = args[4];
        term = Integer.parseInt(args[5]);
        if(term > 8 || term <= 0)
            return false;
        return true;
    }

    @Override
    public SortBase create(SortBase o) {
        BookBorrowRecordRefactor other = (BookBorrowRecordRefactor)o;
        return new BookBorrowRecordRefactor(other);
    }

    @Override
    public int getTerm() {
        return term;
    }

    @Override
    public String getStudentID() {
        return studentID;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getTime() {
        return date;
    }

    @Override
    public int compareTo(SortBase o) {

        BookBorrowRecordRefactor other = (BookBorrowRecordRefactor)o;

        return studentID.compareTo(other.studentID) == 0 ? date.compareTo(other.date) : studentID.compareTo(other.studentID);
    }

    public String toString(){
        return studentID + "," + itemNum + "," + date + "," + type + "," + year + "," + term;
    }
}
